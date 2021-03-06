package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void create(Account entity, String tempField) {
        accountDao.create(entity, tempField);
    }

    @Override
    public void update(Account entity) {
        accountDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

    @Override
    public void delete(String name) {
        accountDao.delete(name);
    }

    @Override
    public void writeOut(Long id) {
        accountDao.writeOut(id);
    }

    @Override
    public DataTableResponse<Account> findAll(Long userId, DataTableRequest dataTableRequest) {
        DataTableResponse<Account> dataTableResponse = accountDao.findAll(userId, dataTableRequest);
        long count = accountDao.countVisible();
        WebResponseUtil.initDataTableResponse(dataTableRequest, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        DataTableResponse<Account> dataTableResponse = accountDao.findAll(request);
        long count = accountDao.countVisible();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Long, String> findTransactionsByAccountId(Long id) {
        return accountDao.findTransactionsByAccountId(id);
    }

    @Override
    public Map<Long, String> findUserByAccountId(Long id) {
        return accountDao.findUserByAccountId(id);
    }
}
