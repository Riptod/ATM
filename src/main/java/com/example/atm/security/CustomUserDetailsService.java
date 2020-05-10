package com.example.atm.security;

import com.example.atm.entity.Role;
import com.example.atm.entity.User;
import com.example.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findUserByName(name);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(name);
            builder.password(user.getPassword());
            builder.roles(user.getRoles().stream().map(Role::getRoleName).toArray(String[]::new));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}