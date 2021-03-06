package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByAuctionId(Long id);
}
