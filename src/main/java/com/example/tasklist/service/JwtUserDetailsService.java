package com.example.tasklist.service;

import com.example.tasklist.domain.model.user.User;
import com.example.tasklist.web.security.JwtEntityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService{

    private final UserService userService;

    public UserDetails loadUserByUsername(String username) {

        User user = userService.getByUsername(username);
        return JwtEntityFactory.create(user);
    }

}
