package ua.com.alevel.service.open.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.service.open.PLPService;

import java.util.List;
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
    public DataTableResponse findAll(DataTableRequest dataTableRequest) {

        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, dataTableRequest);
        return dataTableResponse;

//        int page = dataTableRequest.getPage() - 1;
//        int size = dataTableRequest.getSize();
//        String sortBy = dataTableRequest.getSort();
//        String orderBy = dataTableRequest.getOrder();
//        Sort sort = orderBy.equals("desc")
//                ? Sort.by(sortBy).descending()
//                : Sort.by(sortBy).ascending();
//        PageRequest pageRequest = PageRequest.of(page, size, sort);
//        List<Clothes> items = clothesRepository.findAllByVisibleTrue(pageRequest);
//
//        DataTableResponse<Clothes> dataTableResponse = new DataTableResponse<>();
//        dataTableResponse.setItems(items);
//        dataTableResponse.setOrder(orderBy);
//        dataTableResponse.setSort(sortBy);
//        dataTableResponse.setCurrentPage(dataTableRequest.getPage());
//        dataTableResponse.setPageSize(dataTableRequest.getSize());
//        dataTableResponse.setItemsSize(clothesRepository.countAllByVisibleTrue());
//        return dataTableResponse;
    }

    @Override
    public Clothes findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id).get();
    }
}
