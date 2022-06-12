package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuctionServiceTest {

    private AuctionService auctionService;
    private AuctionRepository auctionRepository;
    private OfferRepository offerRepository;
    private AuctionMapper auctionMapper;

    @BeforeEach
    public void beforeEach() {
        auctionRepository = mock(AuctionRepository.class);
        auctionMapper = mock(AuctionMapper.class);
        offerRepository = mock(OfferRepository.class);
        auctionService = new AuctionService(auctionRepository, offerRepository, auctionMapper);
    }

    @Test
    void findAllAuctions() {
        // Given
        Auction auction1 = new Auction();
        Auction auction2 = new Auction();
        when(auctionRepository.findAll()).thenReturn(List.of(auction1, auction2));

        // When
        List<Auction> auctionList = auctionService.findAllAuctions();

        // Then
        assertEquals(2, auctionList.size());
        assertTrue(auctionList.contains(auction1));
        assertTrue(auctionList.contains(auction2));
    }

    @Test
    void findAuction() {
        // Given
        Auction auction1 = new Auction();
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction1));

        // When
        Auction auction = auctionService.findAuction(1L);

        // Then
        assertEquals(auction, auction1);
    }

    @Test
    void findAuction_notFound() {
        // Given
        when(auctionRepository.findById(1L)).thenReturn(Optional.empty());

        // When Then
        assertThrows(NoSuchElementException.class, () -> {
            auctionService.findAuction(1L);
        });
    }

    @Test
    void endAuction() {
        // Given
        Auction auction = new Auction();
        auction.setId(1L);
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setAuction(auction);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));

        // When
        Auction endedAuction = auctionService.endAuction(1L, 1L);

        // Then
        assertEquals(endedAuction.getId(), 1L);
        assertFalse(endedAuction.isOngoing());
        assertEquals(endedAuction.getWinningOffer(), offer);
    }

    @Test
    void findAllOffersForAuction() {
        // Given
        Auction auction = new Auction();
        auction.setId(1L);
        Offer offer1 = new Offer();
        offer1.setId(1L);
        offer1.setAuction(auction);
        Offer offer2 = new Offer();
        offer2.setId(2L);
        offer2.setAuction(auction);
        when(offerRepository.findByAuctionId(1L)).thenReturn(List.of(offer1, offer2));

        // When
        List<Offer> foundOffers = auctionService.findAllOffersForAuction(1L);

        // Then
        assertEquals(foundOffers.size(),2);
        assertTrue(foundOffers.contains(offer1));
        assertTrue(foundOffers.contains(offer2));
    }

    @Test
    void findOffer() {
        // Given
        Auction auction = new Auction();
        Offer offer = new Offer();
        offer.setAuction(auction);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        // When
        Offer foundOffer = auctionService.findOffer(1L);

        // Then
        assertEquals(foundOffer, offer);
    }
}