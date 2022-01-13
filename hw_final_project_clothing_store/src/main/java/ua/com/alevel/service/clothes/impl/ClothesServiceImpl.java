package ua.com.alevel.service.clothes.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.service.clothes.ClothesService;

import java.util.Optional;

@Service
public class ClothesServiceImpl implements ClothesService {

    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;
    private final ClothesRepository clothesRepository;

    public ClothesServiceImpl(CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper, ClothesRepository clothesRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.clothesRepository = clothesRepository;
    }

    @Override
    public void create(Clothes entity) {
        crudRepositoryHelper.create(clothesRepository, entity);
    }

    @Override
    public void update(Clothes entity) {
        crudRepositoryHelper.update(clothesRepository, entity);
    }

    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(clothesRepository, id);
    }

    @Override
    public Optional<Clothes> findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id);
    }

    @Override
    public DataTableResponse<Clothes> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(clothesRepository, request);
    }
}
