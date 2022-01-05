package ua.com.alevel.persistence.dao.impl;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.AccountDao;
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Account entity, String tempField) {
        entity.setCardNumber(generateCardNumber());
        Query query = entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", tempField);
        User user = (User) query.getSingleResult();
        if (entity.getBalance() >= 0) {
            entityManager.persist(entity);
            user.addAccount(entity);
            entityManager.merge(user);
        } else if (user.getAccounts().size() == 0) {
            entityManager.createQuery("delete from User u where u.id = :id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
        }
    }

    @Override
    public void update(Account entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Account account = findById(id);
        account.setVisible(false);
        update(account);

        User user = findById(id).getUser();
        List<Account> accounts = user.getAccounts().stream().toList();
        for (Account acc : accounts) {
            if (!acc.getVisible()) {
                user.setVisible(false);
            } else {
                user.setVisible(true);
                break;
            }
        }
        List<Transaction> transactionList = account.getTransactions().stream().toList();
        for (Transaction transaction : transactionList) {
            transaction.setVisible(false);
        }
    }

    @Override
    public void delete(String name) {
        Account account = findByName(name);
        account.setVisible(false);
        update(account);
        User user = account.getUser();
        List<Account> accounts = user.getAccounts().stream().toList();
        for (Account acc : accounts) {
            if (!acc.getVisible()) {
                user.setVisible(false);
            } else {
                user.setVisible(true);
                break;
            }
        }
        List<Transaction> transactionList = account.getTransactions().stream().toList();
        for (Transaction transaction : transactionList) {
            transaction.setVisible(false);
        }
    }

    @Override
    public void writeOut(Long id) {
        List<Transaction> transactions = findById(id).getTransactions().stream().toList();
        List<String[]> transactionList = new ArrayList<>();
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("write_out.csv"))) {
            for (int i = 0; i < transactions.size(); i++) {
                String[] transactionsString = new String[3];
                transactionsString[0] = String.valueOf(i);
                if (transactions.get(i).getCategory().getFinances()) {
                    transactionsString[1] = "+ " + transactions.get(i).getAmount().toString();
                } else {
                    transactionsString[1] = "- " + transactions.get(i).getAmount().toString();
                }
                transactionsString[2] = transactions.get(i).getCategory().getName();
                transactionList.add(transactionsString);
            }
            csvWriter.writeAll(transactionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataTableResponse<Account> findAll(Long userId, DataTableRequest request) {
        List<Account> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if (request.getSort().equals("transactionCount")) {
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select a from Account a where a.visible = true and a.user.id = :userId order by a.transactions.size desc")
                        .setParameter("userId", userId)
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select a from Account a where a.visible = true and a.user.id = :userId order by a.transactions.size asc")
                        .setParameter("userId", userId)
                        .setFirstResult(page)
                        .setMaxResults(size);
            }
            items = query.getResultList();
        } else {
            Predicate cond = criteriaBuilder.and(criteriaBuilder.equal(from.get("visible"), true));
            if (request.getOrder().equals("desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
            }
            Predicate cond2 = criteriaBuilder.or(criteriaBuilder.equal(from.get("user"), userId));
            criteriaQuery.where(cond, cond2);
            items = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(page)
                    .setMaxResults(size)
                    .getResultList();
        }

        for (int i = 0; i < items.size(); i++) {
            otherParamMap.put(items.get(i).getId(), countNumOfTransactions(items.get(i).getId()));
        }

        DataTableResponse<Account> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setCurrentSize(request.getPageSize());
        response.setItems(items);
        response.setOtherParamMap(otherParamMap);

        return response;
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    private Account findByName(String name) {
        Query query = entityManager.createQuery("select a from Account a where a.cardNumber = :name")
                .setParameter("name", name);
        return (Account) query.getSingleResult();
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        List<Account> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if (request.getSort().equals("transactionCount")) {
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select a from Account a where a.visible = true order by a.transactions.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select a from Account a where a.visible = true order by a.transactions.size asc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            }
            items = query.getResultList();
        } else {
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
        }

        for (int i = 0; i < items.size(); i++) {
            otherParamMap.put(items.get(i).getId(), countNumOfTransactions(items.get(i).getId()));
        }

        DataTableResponse<Account> response = new DataTableResponse<>();
        response.setSort(request.getSort());
        response.setOrder(request.getOrder());
        response.setCurrentPage(request.getCurrentPage());
        response.setCurrentSize(request.getPageSize());
        response.setItems(items);
        response.setOtherParamMap(otherParamMap);

        return response;
    }

    @Override
    public long countVisible() {
        Query query = entityManager.createQuery("select count(a) from Account a where a.visible = true");
        return (long) query.getSingleResult();
    }

    public int countNumOfTransactions(Long id) {
        return findById(id).getTransactions().size();
    }

    @Override
    public Map<Long, String> findTransactionsByAccountId(Long id) {
        List<Transaction> transactionList = findById(id).getTransactions().stream().toList();
        Map<Long, String> transactions = new HashMap<>();
        for (int i = 0; i < transactionList.size(); i++) {
            transactions.put(transactionList.get(i).getId(), transactionList.get(i).getAmount().toString());
        }
        return transactions;
    }

    @Override
    public Map<Long, String> findUserByAccountId(Long id) {
        User user = findById(id).getUser();
        Map<Long, String> userMap = new HashMap<>();
        userMap.put(user.getId(), user.getEmail());
        return userMap;
    }

    private String generateCardNumber() {
        Random random = new Random();
        String cardNumber = " ";
        for (int i = 0; i < 4; i++) {
            String tempFourDigits = String.valueOf(random.nextInt(1, 9999));

            switch (tempFourDigits.length()) {
                case 1:
                    tempFourDigits = "000" + tempFourDigits;
                    break;
                case 2:
                    tempFourDigits = "00" + tempFourDigits;
                    break;
                case 3:
                    tempFourDigits = "0" + tempFourDigits;
                    break;
            }
            if (i == 0) {
                cardNumber = " " + tempFourDigits;
            } else {
                cardNumber += " " + tempFourDigits;
            }
        }

        Query query = entityManager.createQuery("select a.cardNumber from Account a");
        List<String> numbers = query.getResultList();
        for (String number : numbers) {
            if (cardNumber.equals(number)) {
                generateCardNumber();
            }
        }

        return cardNumber;
    }
}
