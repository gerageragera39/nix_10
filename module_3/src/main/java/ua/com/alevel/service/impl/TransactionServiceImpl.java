package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public void create(Transaction entity, String tempField) {
        transactionDao.create(entity, tempField);
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Transaction findById(Long id) {
        return null;
    }

    @Override
    public DataTableResponse<Transaction> findAll(DataTableRequest request) {
        return transactionDao.findAll(request);
    }

    @Override
    public DataTableResponse<Transaction> findAll(Long accountId, DataTableRequest dataTableRequest) {
        return transactionDao.findAll(accountId, dataTableRequest);
    }
}
