package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Clothes;

import java.util.List;

public interface ClothesService {

    List<Clothes> findAll();

    void doNotNew(List<Clothes> clothes);
}
