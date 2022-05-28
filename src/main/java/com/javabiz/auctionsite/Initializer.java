package com.javabiz.auctionsite;

import com.github.javafaker.Faker;
import com.javabiz.auctionsite.service.auctionmanagement.AuctionRepository;
import com.javabiz.auctionsite.service.auctionmanagement.OfferRepository;
import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.Offer;
import com.javabiz.auctionsite.service.model.Role;
import com.javabiz.auctionsite.service.model.UserModel;
import com.javabiz.auctionsite.service.usermanagement.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Initializer implements CommandLineRunner {
    private final Faker faker = new Faker();

    private final AuctionRepository auctionRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserModel userModel1 = addUser("Anna", "Nowak", "anowak", "haslo123", 18, Role.USER);
        UserModel userModel2 = addUser("Ludiwik", "Kret", "kret", "haslo123", 25, Role.USER);
        UserModel userModel3 = addUser("Michał", "Kowalski", "kowal", "haslo123", 30, Role.PREMIUM_USER);
        UserModel userModel4 = addUser("Patrycja", "Komar", "pati", "haslo123", 19, Role.USER);

        Auction auction1 = addAuction(userModel1, "Buty białe rozmiar 39", "Mało noszone, wygodne.");
        Auction auction2 = addAuction(userModel2, "Hamak", "Dwuosobowy, solidny, wygodny.");
        Auction auction3 = addAuction(userModel1, "Buty czarne rozmiar 39", "Lekko zniszczone.");
        Auction auction4 = addAuction(userModel4, "Telewizor LG", "Model XC, polecam.");
        Auction auction5 = addAuction(userModel1, "Buty brązowe rozmiar 39", "Ani razu nie założone.");

        Offer offer1 = addOffer(auction5, userModel3, 1000.00);
        Offer offer2 = addOffer(auction5, userModel2, 1200.00);
        Offer offer3 = addOffer(auction5, userModel4, 700.00);
        Offer offer4 = addOffer(auction2, userModel3, 100.00);
        Offer offer5 = addOffer(auction3, userModel3, 99.00);
        Offer offer6 = addOffer(auction2, userModel1, 25.00);
    }

    public Auction addAuction(UserModel owner, String title, String content){
        Auction auction = new Auction(owner, title, content);
        auctionRepository.save(auction);
        return auction;
    }

    public UserModel addUser(String name, String surname, String username, String password, int age, Role role){
        UserModel userModel = new UserModel(name, surname, username, username+"@gmail.com", password, age, role);
        userRepository.save(userModel);
        return userModel;
    }

    public Offer addOffer(Auction auction, UserModel offering, double price){
        Offer offer = new Offer(auction, offering, price);
        offerRepository.save(offer);
        return offer;
    }
}
