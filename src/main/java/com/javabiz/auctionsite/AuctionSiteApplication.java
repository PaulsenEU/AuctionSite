package com.javabiz.auctionsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AuctionSiteApplication {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        SpringApplication.run(AuctionSiteApplication.class, args);
    }

}
