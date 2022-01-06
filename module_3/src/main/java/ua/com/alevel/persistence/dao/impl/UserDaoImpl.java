package ua.com.alevel.persistence.dao.impl;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.UserDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User entity, String tempField) {
        entityManager.persist(entity);
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        List<Account> accountList = findById(id).getAccounts().stream().toList();
        for (Account account : accountList) {
            account.setVisible(false);
        }
        findById(id).setVisible(false);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(u.id) from User u where u.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        List<User> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if (request.getSort().equals("accountCount")) {
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select u from User u where u.visible = true order by u.accounts.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select u from User u where u.visible = true order by u.accounts.size asc")
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
            otherParamMap.put(items.get(i).getId(), countNumOfAccounts(items.get(i).getId()));
        }

        DataTableResponse<User> response = new DataTableResponse<>();
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
        Query query = entityManager.createQuery("select count(u) from User u where u.visible = true");
        return (long) query.getSingleResult();
    }

    public int countNumOfAccounts(Long id) {
        List<Account> accounts = findById(id).getAccounts().stream().toList();
        int countVisible = 0;
        for (Account account : accounts) {
            if (account.getVisible()) {
                countVisible++;
            }
        }
        return countVisible;
    }

    @Override
    public Map<Long, String> findAccountsByUserId(Long id) {
        List<Account> accountList = findById(id).getAccounts().stream().toList();
        List<Account> visibleAccounts = new ArrayList<>();
        for (Account account : accountList) {
            if (account.getVisible() == true) {
                visibleAccounts.add(account);
            }
        }
        Map<Long, String> accounts = new HashMap<>();
        for (int i = 0; i < visibleAccounts.size(); i++) {
            accounts.put(visibleAccounts.get(i).getId(), visibleAccounts.get(i).getCardNumber());
        }
        return accounts;
    }

    @Override
    public boolean existByPassportId(String passportID) {
        Query query = entityManager.createQuery("select count(u.id) from User u where u.passportID = :passportID")
                .setParameter("passportID", passportID);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public boolean existByEmail(String email) {
        Query query = entityManager.createQuery("select count(u.id) from User u where u.email = :email")
                .setParameter("email", email);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public void writeOut(Long id) {
        User user = findById(id);
        List<Account> accounts = user.getAccounts().stream().toList();
        List<Transaction> transactions = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> byAccount = account.getTransactions().stream().toList();
            transactions.addAll(byAccount);
        }
        List<String[]> writeOutList = new ArrayList<>();
        String[] strings = new String[5];
        strings[0] = "#";
        strings[1] = "user_email";
        strings[2] = "account_number";
        strings[3] = "transaction_amount";
        strings[4] = "category_name";
        writeOutList.add(strings);
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("write_out.csv"))) {
            for (int i = 0; i < transactions.size(); i++) {
                String[] transactionsString = new String[5];
                transactionsString[0] = String.valueOf(i);
                transactionsString[1] = transactions.get(i).getAccount().getUser().getEmail();
                transactionsString[2] = transactions.get(i).getAccount().getCardNumber();
                if (transactions.get(i).getCategory().getFinances()) {
                    transactionsString[3] = "+ " + transactions.get(i).getAmount().toString();
                } else {
                    transactionsString[3] = "- " + transactions.get(i).getAmount().toString();
                }
                transactionsString[4] = transactions.get(i).getCategory().getName();
                writeOutList.add(transactionsString);
            }
            csvWriter.writeAll(writeOutList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
