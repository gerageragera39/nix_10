package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.TransactionService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Map;

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
        return transactionDao.findById(id);
    }

    @Override
    public DataTableResponse<Transaction> findAll(DataTableRequest request) {
//        return transactionDao.findAll(request);
        DataTableResponse<Transaction> dataTableResponse = transactionDao.findAll(request);
        long count = transactionDao.countVisible();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Transaction> findAll(Class entityClass, Long entityId, DataTableRequest dataTableRequest) {
//        return transactionDao.findAll(entityClass, entityId, dataTableRequest);
        DataTableResponse<Transaction> dataTableResponse = transactionDao.findAll(dataTableRequest);
        long count = transactionDao.countVisible();
        WebResponseUtil.initDataTableResponse(dataTableRequest, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Long, String> findByAccountByTransactionId(Long id) {
        return transactionDao.findByAccountByTransactionId(id);
    }

    @Override
    public Map<Long, String> findUserByTransactionId(Long id) {
        return transactionDao.findUserByTransactionId(id);
    }
}
