package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;

import java.util.Map;

public interface AccountDao extends BaseDao<Account> {

    Map<Long, String> findTransactionsByAccountId(Long id);

    Map<Long, String> findUserByAccountId(Long id);

    void delete(String name);

    void writeOut(Long id);

    DataTableResponse<Account> findAll(Long userId, DataTableRequest dataTableRequest);
}
