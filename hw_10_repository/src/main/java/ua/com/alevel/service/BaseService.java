package ua.com.alevel.service;

import ua.com.alevel.persistense.datatable.DataTableRequest;
import ua.com.alevel.persistense.datatable.DataTableResponse;
import ua.com.alevel.persistense.entity.BaseEntity;

import java.util.Optional;

public interface BaseService<ENTITY extends BaseEntity> {

    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(Long id);

    Optional<ENTITY> findById(Long id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);
}

