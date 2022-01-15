package ua.com.alevel.service.open;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface PLPService<E extends BaseEntity> {

    DataTableResponse<E> findAll(DataTableRequest request);
}
