package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.CategoryDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.persistence.entity.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Category entity, String tempField) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public DataTableResponse<Category> findAll(DataTableRequest request) {
        List<Category> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> from = criteriaQuery.from(Category.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if (request.getSort().equals("transactionCount")) {
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select c from Category c where c.visible = true order by c.transactions.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select c from Category c where c.visible = true order by c.transactions.size asc")
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

        DataTableResponse<Category> response = new DataTableResponse<>();
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
        Query query = entityManager.createQuery("select count(c) from Category c where c.visible = true");
        return (long) query.getSingleResult();
    }

    @Override
    public List<String> getAllCategoryNames() {
        Query query = entityManager.createQuery("select c.name from Category c");
        return query.getResultList();
    }

    @Override
    public Category findByName(String categoryName) {
        Query query = entityManager.createQuery("select c from Category c where c.name = :name")
                .setParameter("name", categoryName);
        return (Category) query.getSingleResult();
    }

    public int countNumOfTransactions(Long id) {
        List<Transaction> transactionList = findById(id).getTransactions().stream().toList();
        int count = 0;
        for (Transaction transaction : transactionList) {
            if (transaction.getVisible()) {
                count++;
            }
        }
        return count;
    }
}
