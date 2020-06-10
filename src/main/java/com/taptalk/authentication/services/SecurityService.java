package com.taptalk.authentication.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
