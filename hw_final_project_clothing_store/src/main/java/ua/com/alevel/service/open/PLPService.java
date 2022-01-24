package ua.com.alevel.service.open;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PLPService<E extends BaseEntity> {

    DataTableResponse<E> findAll(DataTableRequest request);

    Clothes findById(Long id);

    Map<Long, String> findAllBrands();

    Map<Long, String> findAllColors();

    Map<Long, String> findAllTypes();

    Map<Long, String> findAllSizes();
}
