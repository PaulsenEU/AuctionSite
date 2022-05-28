package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.commons.AuctionDto;
import com.javabiz.auctionsite.service.usermanagement.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

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
        model.addAttribute("offerList", auctionService.findAllOffersForAuction(id));
        return "offer/getAllForAuction";
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
                              RedirectAttributes redirectAttributes) {
        auctionService.createAuction(auction);
        redirectAttributes.addFlashAttribute("success", "Record has been successfully added.");
        return "redirect:/auction/";
    }

    @GetMapping("/edit/{id}")
    public String editAuction(@PathVariable Long id, Model model) {
        Auction auction = auctionService.findAuction(id);
        AuctionDto auctionDto = new AuctionDto(auction.getTitle(), auction.getContent());

        if (!model.containsAttribute("auction")) {
            model.addAttribute("auction", new AuctionDto());
        }

        model.addAttribute("auctionId", id);
        model.addAttribute("auction", auctionDto);
        return "auction/editAuction";
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
    public String deleteAuction(@PathVariable("id") Long id) {
        auctionService.deleteAuction(id);
        return "redirect:/auction";
    }
}
