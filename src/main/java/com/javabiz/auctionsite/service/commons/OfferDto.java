package com.javabiz.auctionsite.service.commons;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class OfferDto {
    private Long id;
    private Auction auction;
    private UserModel offering;
    private double price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public OfferDto(Auction auction, UserModel offering, double price) {
        this.auction = auction;
        this.offering = offering;
        this.price = price;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public OfferDto() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
