package ua.com.alevel.service.open.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.brands.BrandRepository;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.service.open.PLPService;
import ua.com.alevel.util.WebUtil;

import java.util.List;
import java.util.Optional;

@Service
public class PLPServiceImpl implements PLPService {

    private final ClothesRepository clothesRepository;
    private final BrandRepository brandRepository;
    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;


    public PLPServiceImpl(ClothesRepository clothesRepository, BrandRepository brandRepository, CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper) {
        this.clothesRepository = clothesRepository;
        this.brandRepository = brandRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public DataTableResponse findAll(DataTableRequest dataTableRequest) {
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortBy = dataTableRequest.getSort();
        String orderBy = dataTableRequest.getOrder();
        Sort sort = orderBy.equals("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null &&
                dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            Long brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
            String clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            List<Clothes> items = clothesRepository.findAllByBrandIdAndTitleContaining(brandId, clothesSearch, pageRequest);
            return initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
            Long brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
            List<Clothes> items = clothesRepository.findAllByBrandId(brandId, pageRequest);
            return initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            String clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            List<Clothes> items = clothesRepository.findAllByTitleContaining(clothesSearch, pageRequest);
            return initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
        }
        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, dataTableRequest);
        return dataTableResponse;
    }

    @Override
    public Clothes findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id).get();
    }

    private DataTableResponse<Clothes> initDataTableResponse(DataTableRequest dataTableRequest, List<Clothes> items, String sortBy, String orderBy) {
        DataTableResponse<Clothes> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(items);
        dataTableResponse.setOrder(orderBy);
        dataTableResponse.setSort(sortBy);
        dataTableResponse.setCurrentPage(dataTableRequest.getPage());
        dataTableResponse.setPageSize(dataTableRequest.getSize());
        dataTableResponse.setItemsSize(items.size());
        return dataTableResponse;
    }
}
