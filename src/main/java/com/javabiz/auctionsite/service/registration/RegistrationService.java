package com.javabiz.auctionsite.service.registration;

import com.javabiz.auctionsite.service.model.UserModel;
import com.javabiz.auctionsite.service.usermanagement.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserRepository userRepository;

    public UserModel register(RegistrationRequest request) {
        var user = new UserModel();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);

        return user;
    }
}
