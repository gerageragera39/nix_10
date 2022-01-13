package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.Clothes;
import ua.com.alevel.persistence.repository.ClothesRepository;
import ua.com.alevel.service.ClothesService;

import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;

    public ClothesServiceImpl(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Override
    public List<Clothes> findAll() {
        return clothesRepository.findAllByIsNewTrue();
    }

    @Override
    public void doNotNew(List<Clothes> clothes) {
        for (Clothes thing : clothes) {
            thing.setNew(false);
            clothesRepository.save(thing);
        }
    }
}
