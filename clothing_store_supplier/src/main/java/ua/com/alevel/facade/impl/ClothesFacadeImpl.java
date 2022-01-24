package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.Clothes;
import ua.com.alevel.facade.ClothesFacade;
import ua.com.alevel.service.ClothesService;

import java.util.List;

@Service
public class ClothesFacadeImpl implements ClothesFacade {

    private final ClothesService clothesService;

    public ClothesFacadeImpl(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @Override
    public List<Clothes> findAll() {
        return clothesService.findAll();
    }

    @Override
    public void doNotNew(List<Clothes> clothes) {
        clothesService.doNotNew(clothes);
    }
}
