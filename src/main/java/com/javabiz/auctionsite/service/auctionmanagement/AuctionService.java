package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.Offer;
import com.javabiz.auctionsite.service.commons.AuctionDto;
import com.javabiz.auctionsite.service.commons.OfferDto;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService {
    private static final Logger log = LogManager.getLogger(AuctionService.class);
    private final AuctionRepository auctionRepository;
    private final OfferRepository offerRepository;
    private final AuctionMapper auctionMapper;

    public List<Auction> findAllAuctions(){
        return auctionRepository.findAll();
    }

    public Auction findAuction(Long id){
        return auctionRepository.findById(id).orElseThrow();
    }

    public void createAuction(AuctionDto auctionDto){
        Auction auction = auctionMapper.fromDto(auctionDto);
        auctionRepository.save(auction);
        log.info("Auction with id {} successfully created.", auction.getId());
    }

    public void editAuction(Long auctionId, AuctionDto auctionDto){
        Auction auction = this.findAuction(auctionId);
        auction.setTitle(auctionDto.getTitle());
        auction.setContent(auctionDto.getContent());
        auctionRepository.save(auction);
        log.info("Auction with id {} successfully updated.", auction.getId());
    }

    // This ends auction with auctionId by accepting offer with offerId
    public Auction endAuction(Long auctionId, Long offerId){
        Offer offer = this.findOffer(offerId);
        Auction auction = this.findAuction(auctionId);
        auction.setOngoing(false);
        auction.setWinningOffer(offer);
        auctionRepository.save(auction);
        log.info("Auction with id {} ended.", auction.getId());
        return auctionRepository.findById(auctionId).orElseThrow();
    }

    public void deleteAuction(Long id){
        Auction auction = this.findAuction(id);
        auctionRepository.delete(auction);
        log.info("Auction with id {} successfully deleted.", id);
    }

    public List<Offer> findAllOffersForAuction(Long auctionId){
        return offerRepository.findByAuctionId(auctionId);
    }

    public Offer findOffer(Long id){
        return offerRepository.findById(id).orElseThrow();
    }

    public void createOffer(OfferDto offerDto){
        Offer offer = auctionMapper.fromDto(offerDto);
        offerRepository.save(offer);
    }
}
