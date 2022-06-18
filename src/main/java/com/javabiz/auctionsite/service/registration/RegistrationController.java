package com.javabiz.auctionsite.service.registration;

import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public UserModel register(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}

