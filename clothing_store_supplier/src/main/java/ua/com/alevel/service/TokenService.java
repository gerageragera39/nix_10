package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Token;

public interface TokenService {

    void updateToken();

    Token findNewToken();
}
