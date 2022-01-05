package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;

import java.util.Map;

public interface TransactionDao extends BaseDao<Transaction> {

    DataTableResponse<Transaction> findAll(Class entityClass, Long entityId, DataTableRequest dataTableRequest);

    Map<Long, String> findByAccountByTransactionId(Long id);

    Map<Long, String> findUserByTransactionId(Long id);
}
