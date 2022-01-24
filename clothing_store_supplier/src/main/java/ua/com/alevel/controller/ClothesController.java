package ua.com.alevel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.TokenFacade;
import ua.com.alevel.persistence.entity.Clothes;
import ua.com.alevel.facade.ClothesFacade;
import ua.com.alevel.persistence.entity.Token;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothesController {

    private final ClothesFacade clothesFacade;
    private final TokenFacade tokenFacade;

    public ClothesController(ClothesFacade clothesFacade, TokenFacade tokenFacade) {
        this.clothesFacade = clothesFacade;
        this.tokenFacade = tokenFacade;
    }

    @GetMapping
    public List<Clothes> findAll() {
        List<Clothes> clothes = clothesFacade.findAll();
        clothesFacade.doNotNew(clothes);
        return clothes;
    }

    @GetMapping("/new")
    public Token findNewToken() {
        tokenFacade.updateToken();
        return tokenFacade.findNewToken();
    }
}
