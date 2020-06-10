package com.taptalk.authentication.services;


import com.taptalk.authentication.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
