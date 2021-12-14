package ua.com.alevel.persistense.crud;

import ua.com.alevel.persistense.datatable.DataTableRequest;
import ua.com.alevel.persistense.datatable.DataTableResponse;
import ua.com.alevel.persistense.entity.BaseEntity;
import ua.com.alevel.persistense.repository.BaseRepository;

import java.util.Optional;

public interface CrudRepositoryHelper<E extends BaseEntity, R extends BaseRepository<E>> {

    void help();
    void create(R repository, E entity);
    void update(R repository, E entity);
    void delete(R repository, Long id);
    Optional<E> findById(R repository, Long id);
    DataTableResponse<E> findAll(R repository, DataTableRequest dataTableRequest);
}
