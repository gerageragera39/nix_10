package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;

import java.util.Map;

public interface TransactionService extends BaseService<Transaction> {

    DataTableResponse<Transaction> findAll(Class entityClass, Long entityId, DataTableRequest dataTableRequest);

    Map<Long, String> findByAccountByTransactionId(Long id);

    Map<Long, String> findUserByTransactionId(Long id);
}
