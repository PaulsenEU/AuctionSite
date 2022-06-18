package com.javabiz.auctionsite.service.usermanagement;

import com.javabiz.auctionsite.service.model.Auction;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController implements UserDetailsService {

    private final UserRepository userRepository;

    @GetMapping()
    public String getAll(Model model) {
        List<UserModel> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "users/getAll";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
