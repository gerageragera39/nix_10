package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.TokenFacade;
import ua.com.alevel.persistence.entity.Token;
import ua.com.alevel.service.TokenService;

@Service
public class TokenFacadeImpl implements TokenFacade {

    private final TokenService tokenService;

    public TokenFacadeImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void updateToken() {
        tokenService.updateToken();
    }

    @Override
    public Token findNewToken() {
        return tokenService.findNewToken();
    }
}
