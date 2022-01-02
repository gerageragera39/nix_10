package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Account;

import java.util.Map;

public interface AccountService extends BaseService<Account> {

    Map<Long, String> findTransactionsByAccountId(Long id);

    Map<Long, String> findUserByAccountId(Long id);

    void delete(String name);
}
