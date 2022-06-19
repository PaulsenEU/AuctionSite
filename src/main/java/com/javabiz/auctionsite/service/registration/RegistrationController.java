package com.javabiz.auctionsite.service.registration;

import com.javabiz.auctionsite.service.commons.UserDto;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "login/registration";
    }

    @PostMapping
    public String register(@ModelAttribute("user") @Valid UserDto userDto) {
        registrationService.register(userDto);
        return "login/registration_success";
    }

    @GetMapping("/loginSuccessful")
    public String loggedIn(Model model, Authentication authentication) {
        return "login/loginSuccessful";
    }

    @GetMapping("/logoutSuccessful")
    public String loggedOut(Model model) {
        return "login/logoutSuccessful";
    }
}

