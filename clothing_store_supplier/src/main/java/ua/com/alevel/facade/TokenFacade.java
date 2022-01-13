package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.Token;

public interface TokenFacade {

    void updateToken();

    Token findNewToken();
}
