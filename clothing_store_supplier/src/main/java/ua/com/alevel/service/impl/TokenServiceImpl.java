package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.Token;
import ua.com.alevel.persistence.repository.token.TokenRepository;
import ua.com.alevel.service.TokenService;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void updateToken() {
        String newContent = UUID.randomUUID().toString();
        Token token = tokenRepository.findTokenById(1L);
        token.setContent(newContent);
        tokenRepository.save(token);
    }

    @Override
    public Token findNewToken() {
        return tokenRepository.findTokenById(1L);
    }
}
