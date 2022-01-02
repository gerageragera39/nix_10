package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.AccountService;

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
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        return accountDao.findAll(request);
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
