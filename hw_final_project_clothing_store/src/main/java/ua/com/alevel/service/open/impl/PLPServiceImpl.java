package ua.com.alevel.service.open.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.service.open.PLPService;

import java.util.Optional;

@Service
public class PLPServiceImpl implements PLPService {

    private final ClothesRepository clothesRepository;
    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;


    public PLPServiceImpl(ClothesRepository clothesRepository, CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper) {
        this.clothesRepository = clothesRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public DataTableResponse findAll(DataTableRequest request) {
        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, request);
        int count = clothesRepository.countAllByVisibleTrue();
//        WebUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Clothes findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id).get();
    }
}
