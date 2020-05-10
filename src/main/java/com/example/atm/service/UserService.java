package com.example.atm.service;

import com.example.atm.entity.User;

public interface UserService {
    User findUserByName(String name);

    User save(User user);
}
