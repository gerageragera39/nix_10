package ua.com.alevel.persistence.crud;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface CrudRepositoryHelper<E extends BaseEntity, R extends BaseRepository<E>> {

    void create(R repository, E entity);

    void update(R repository, E entity);

    void delete(R repository, Long id);

    Optional<E> findById(R repository, Long id);

    DataTableResponse<E> findAll(R repository, DataTableRequest dataTableRequest);

    void addRelation(String countryName, String personPassportId);

    void removeRelation(String countryName, String personPassportId);
}
