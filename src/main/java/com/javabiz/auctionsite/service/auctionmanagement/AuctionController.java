package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.commons.OfferDto;
import com.javabiz.auctionsite.service.mailservice.MailService;
import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.commons.AuctionDto;
import com.javabiz.auctionsite.service.model.Offer;
import com.javabiz.auctionsite.service.usermanagement.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final MailService mailService;
    private final UserRepository userRepository;

    @GetMapping()
    public String getAll(Model model) {
        List<Auction> auctionList = auctionService.findAllAuctions();
        model.addAttribute("auctionList", auctionList);
        return "auction/getAll";
    }

    @GetMapping("/{id}")
    public String getAuction(Model model, @PathVariable Long id) {
        Auction auction = auctionService.findAuction(id);
        model.addAttribute("auction", auction);
        return "auction/getAuction";
    }

    @GetMapping("/{id}/offers")
    public String getOffersForAuction(Model model, @PathVariable Long id){
        Auction auction = auctionService.findAuction(id);
        model.addAttribute("offerList", auctionService.findAllOffersForAuction(id));
        model.addAttribute("auctionId", id);
        model.addAttribute("isOnGoing", auction.isOngoing());
        if(!auction.isOngoing() && auction.getWinningOffer() != null){
            model.addAttribute("winnerId", auction.getWinningOffer().getId());
        }

        return "offer/getAllForAuction";
    }

    @GetMapping("/{id}/offers/{offerId}")
    public String endAuctionByChoosingTheOffer(@PathVariable Long id, @PathVariable Long offerId, Authentication authentication){
        Auction auction = auctionService.findAuction(id);
        Offer offer = auctionService.findOffer(offerId);

        assert auction.getOwner()!=null;
        assert offer.getOffering()!=null;

        if(!Objects.equals(auction.getOwner().getUsername(), authentication.getName())) {
            return "redirect:/auction/";
        }
        else {
            auctionService.endAuction(id, offerId);
            mailService.sendConfirmationMailToSeller(offer);
            mailService.sendConfirmationMailToBuyer(offer);
            return "redirect:/auction/";
        }
    }

    @GetMapping("/{id}/offer")
    public String makeOffer(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("offer")) {
            model.addAttribute("offer", new OfferDto());
            model.addAttribute("auctionId", id);
        }
        return "auction/makeOffer";
    }

    @PostMapping("/makeOffer/{id}")
    public String addOffer(@Valid OfferDto offer,
                             @PathVariable Long id,
                             RedirectAttributes redirectAttributes, Authentication authentication) {
        offer.setOffering(userRepository.findByUsername(authentication.getName()));
        offer.setAuction(auctionService.findAuction(id));
        auctionService.createOffer(offer);
        redirectAttributes.addFlashAttribute("success", "Record has been successfully added.");
        return "redirect:/auction/";
    }

    @GetMapping("/add")
    public String createAuction(Model model) {
        if (!model.containsAttribute("auction")) {
            model.addAttribute("auction", new AuctionDto());
        }
        return "auction/addAuction";
    }

    @PostMapping("/add")
    public String addAuction(@Valid AuctionDto auction,
                             RedirectAttributes redirectAttributes, Authentication authentication) {
        auction.setOwner(userRepository.findByUsername(authentication.getName()));
        auctionService.createAuction(auction);
        redirectAttributes.addFlashAttribute("success", "Record has been successfully added.");
        return "redirect:/auction/";
    }

    @GetMapping("/edit/{id}")
    public String editAuction(@PathVariable Long id, Model model, Authentication authentication) {
        Auction auction = auctionService.findAuction(id);
        AuctionDto auctionDto = new AuctionDto(auction.getTitle(), auction.getContent());

        if(!Objects.equals(auction.getOwner().getUsername(), authentication.getName())){
            return "redirect:/auction/";
        }
        else {
        if (!model.containsAttribute("auction")) {
            model.addAttribute("auction", new AuctionDto());
        }

        model.addAttribute("auctionId", id);
        model.addAttribute("auction", auctionDto);
        return "auction/editAuction";
        }
    }

    @PutMapping("/{id}/update")
    public String updateAuction(@PathVariable Long id,
                                @Valid @ModelAttribute("auction") AuctionDto auctionDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.auction", bindingResult);
            redirectAttributes.addFlashAttribute("auction", auctionDto);
            return "redirect:/auction/" + id;
        }

        auctionService.editAuction(id, auctionDto);

        redirectAttributes.addFlashAttribute("success", "Record has been successfully updated.");
        return "redirect:/auction/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteAuction(@PathVariable("id") Long id, Authentication authentication) {
        Auction auction = auctionService.findAuction(id);
        if(!Objects.equals(auction.getOwner().getUsername(), authentication.getName())){
            return "redirect:/auction/";
        }
        else {
            auctionService.deleteAuction(id);
            return "redirect:/auction";
        }
    }
}
