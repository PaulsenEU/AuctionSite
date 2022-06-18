package com.javabiz.auctionsite.Security.Filters;

import lombok.Data;

@Data
public class UserAndPasswordAuthRequest {
    private String username;
    private String password;
}
