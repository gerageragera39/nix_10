package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;

public interface TransactionDao extends BaseDao<Transaction>{

    DataTableResponse<Transaction> findAll(Long accountId, DataTableRequest dataTableRequest);
}
