package com.javabiz.auctionsite.service.auctionmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    @NotNull
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$%^+.=<|>]).{8,20}$" , message = "{err.string.password}")
    private String password;
    @Min(1) @Max(99)
    private int age;
    @NotNull
    private Role role;

    //Only for initializer purpose
    public UserModel(String name, String surname, String username, String password, int age, Role role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.age = age;
        this.role = role;
    }
}
