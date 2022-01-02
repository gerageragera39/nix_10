package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;

public interface TransactionService extends BaseService<Transaction> {

    DataTableResponse<Transaction> findAll(Long accountId, DataTableRequest dataTableRequest);
}
