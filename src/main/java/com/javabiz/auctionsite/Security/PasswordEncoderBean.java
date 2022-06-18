package com.javabiz.auctionsite.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderBean {
    @Bean
    @Primary
    public BCryptPasswordEncoder getEncoder(){ return new BCryptPasswordEncoder(); }
}
