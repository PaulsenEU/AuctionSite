package com.javabiz.auctionsite.service.registration;

import com.javabiz.auctionsite.service.commons.UserDto;
import com.javabiz.auctionsite.service.model.Role;
import com.javabiz.auctionsite.service.model.UserModel;
import com.javabiz.auctionsite.service.usermanagement.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private static final Logger log = LogManager.getLogger(RegistrationService.class);
    private UserRepository userRepository;

    public UserModel register(UserDto request) {
        var user = new UserModel();
        var encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setAge(0);
        user.setRole(Role.USER);
        userRepository.save(user);

        log.info("User "+user.getUsername()+" registered successfully.");
        return user;
    }
}
