package com.javabiz.auctionsite.service.commons;

import com.javabiz.auctionsite.service.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private Role role;
    @NotEmpty
    private int age;
}
