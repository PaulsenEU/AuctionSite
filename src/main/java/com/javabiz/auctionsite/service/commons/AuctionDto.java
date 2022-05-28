package com.javabiz.auctionsite.service.commons;

import com.javabiz.auctionsite.service.model.Offer;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
public class AuctionDto {
    private Long id;
    private UserModel owner;
    @Size(min=3, max=20)
    private String title;
    @Size(min=3, max=500)
    private String content;
    private boolean ongoing;
    private Offer winningOffer;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Use to create
    public AuctionDto(UserModel owner, String title, String content) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.ongoing = true;
        this.winningOffer = null;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    // Use to update
    public AuctionDto(String title, String content) {
        this.ongoing = true;
        this.winningOffer = null;
        this.title = title;
        this.content = content;
        this.updatedAt = OffsetDateTime.now();
    }

    public AuctionDto() {
        this.ongoing = true;
        this.winningOffer = null;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
