package com.example.atm.service.Impl;

import com.example.atm.entity.User;
import com.example.atm.repository.UserRepository;
import com.example.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
