package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CountriesDaoImpl implements CountriesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Countries entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Countries entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Countries country where country.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(c.id) from Countries c where c.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Countries findById(Long id) {
        return null;
    }

    @Override
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
        List<Countries> countries = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();

        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        String sql = "select id, country_name, ISO, count(country_id) as personCount " +
                "from countries as country left join country_person as cp on country.id = cp.country_id " +
                "group by country.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();

        System.out.println(sql);

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                CountriesResultSet countriesResultSet = convertResultSetToSimpleCountry(resultSet);
                countries.add(countriesResultSet.getCountry());
                otherParamMap.put(countriesResultSet.getCountry().getId(), countriesResultSet.getPersonCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataTableResponse<Countries> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(countries);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;

//        page = (page - 1) * size;
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
//        Root<Employee> from = criteriaQuery.from(Employee.class);
//        if (order.equals("desc")) {
//            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(sort)));
//        } else {
//            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(sort)));
//        }
//
//        return entityManager.createQuery(criteriaQuery)
//                .setFirstResult(page)
//                .setMaxResults(size)
//                .getResultList();
    }

//    @Override
//    public List<Countries> findAll(int page, int size, String sort, String order) {
//        return null;
//    }
}
