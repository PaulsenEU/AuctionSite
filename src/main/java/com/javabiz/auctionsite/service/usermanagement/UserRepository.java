package com.javabiz.auctionsite.service.usermanagement;

import com.javabiz.auctionsite.service.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}