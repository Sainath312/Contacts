package com.example.Contacts.services;

import com.example.Contacts.config.UserInfoUserDetails;
import com.example.Contacts.entity.UserInfo;
import com.example.Contacts.exceptions.ContactNotFound;
import com.example.Contacts.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoByEmail = repository.findByEmailID(username);
        if (userInfoByEmail.isPresent()) {
            return new UserInfoUserDetails(userInfoByEmail.get());
        }
        Optional<UserInfo> userInfoByMobile = repository.findByPhoneNumber(username);
        return userInfoByMobile.map(userInfo -> new UserInfoUserDetails(userInfo, true))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or mobile number: " + username));
    }
}
