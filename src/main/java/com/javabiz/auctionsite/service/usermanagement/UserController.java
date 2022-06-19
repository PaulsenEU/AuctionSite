package com.javabiz.auctionsite.service.usermanagement;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.Role;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping()
    public String getAll(Model model) {
        List<UserModel> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "users/getAll";
    }

    @GetMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Long id,
                       @RequestParam(name="role") String newRole) {
        UserModel userModel = userRepository.getById(id);
        userModel.setRole(Role.valueOf(newRole));
        userRepository.save(userModel);

        List<UserModel> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "users/getAll";
    }
}
