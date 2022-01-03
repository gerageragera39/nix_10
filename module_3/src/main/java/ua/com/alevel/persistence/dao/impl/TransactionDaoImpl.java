package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Transaction entity, String tempField) {
        Query query = entityManager.createQuery("select a from Account a where a.cardNumber = :cardNumber")
                .setParameter("cardNumber", tempField);
        Account account = (Account) query.getSingleResult();
        if (entity.getCategory().getFinances()) {
            account.setBalance(account.getBalance() + entity.getAmount());
        } else if (account.getBalance() >= entity.getAmount()) {
            account.setBalance(account.getBalance() - entity.getAmount());
        } else {
            throw new NullPointerException("Null balance");
        }
        entity.setAccount(account);
        account.addTransaction(entity);
        entityManager.persist(entity);
        entityManager.merge(account);
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Transaction findById(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public DataTableResponse<Transaction> findAll(DataTableRequest request) {
        List<Transaction> items;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> from = criteriaQuery.from(Transaction.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        Predicate cond = criteriaBuilder.and(criteriaBuilder.equal(from.get("visible"), true));
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }
        criteriaQuery.where(cond);
        items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Transaction> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setCurrentSize(request.getPageSize());
        response.setItems(items);
        return response;
    }

    @Override
    public long countVisible() {
        Query query = entityManager.createQuery("select count(t) from Transaction t where t.visible = true");
        return (long) query.getSingleResult();
    }

    @Override
    public DataTableResponse<Transaction> findAll(Class entityClass, Long entityId, DataTableRequest dataTableRequest) {
        List<Transaction> items;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> from = criteriaQuery.from(Transaction.class);

        int page = (dataTableRequest.getCurrentPage() - 1) * dataTableRequest.getPageSize();
        int size = page + dataTableRequest.getPageSize();

        Predicate cond = criteriaBuilder.and(criteriaBuilder.equal(from.get("visible"), true));
        if (dataTableRequest.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(dataTableRequest.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(dataTableRequest.getSort())));
        }
        Predicate cond2;
        if (entityClass.isAssignableFrom(Account.class)) {
            cond2 = criteriaBuilder.or(criteriaBuilder.equal(from.get("account"), entityId));
            criteriaQuery.where(cond,cond2);
        } else if (entityClass.isAssignableFrom(User.class)) {
            Query query = entityManager.createQuery("select u.accounts from User u where u.id = :id")
                    .setParameter("id", entityId);
            List<Account> accounts = query.getResultList();
            List<Long> ids = new ArrayList<>();
            for (Account account : accounts) {
                ids.add(account.getId());
            }
            criteriaQuery.where(from.get("account").in(ids));
        }
        items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Transaction> response = new DataTableResponse<>();
        response.setSort(dataTableRequest.getSort());
        response.setOrder(dataTableRequest.getOrder());
        response.setCurrentPage(dataTableRequest.getCurrentPage());
        response.setCurrentSize(dataTableRequest.getPageSize());
        response.setItems(items);
        return response;
    }

    @Override
    public Map<Long, String> findByAccountByTransactionId(Long id) {
        Account account = findById(id).getAccount();
        Map<Long, String> accountMap = new HashMap<>();
        accountMap.put(account.getId(), account.getCardNumber());
        return accountMap;
    }

    @Override
    public Map<Long, String> findUserByTransactionId(Long id) {
        User user = findById(id).getAccount().getUser();
        Map<Long, String> userMap = new HashMap<>();
        userMap.put(user.getId(), user.getEmail());
        return userMap;
    }
}
