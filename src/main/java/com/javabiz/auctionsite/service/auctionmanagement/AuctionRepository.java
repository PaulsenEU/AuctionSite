package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
