package com.javabiz.auctionsite.service.auctionmanagement;

import com.javabiz.auctionsite.service.auctionmanagement.model.Auction;
import com.javabiz.auctionsite.service.auctionmanagement.model.Offer;
import com.javabiz.auctionsite.service.commons.AuctionDto;
import com.javabiz.auctionsite.service.commons.OfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    Auction fromDto(AuctionDto auctionDto);
    Offer fromDto(OfferDto offerDto);
}
