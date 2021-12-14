package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

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
        return entityManager.find(Countries.class, id);
    }

    @Override
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
        List<Countries> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Countries> criteriaQuery = criteriaBuilder.createQuery(Countries.class);
        Root<Countries> from = criteriaQuery.from(Countries.class);
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if(request.getSort().equals("peopleCount")){
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select c from Countries c where c.visible = true order by c.people.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select c from Countries c where c.visible = true order by c.people.size asc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            }
            items = query.getResultList();
        } else {
            if (request.getOrder().equals("desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
            }

            items = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(page)
                    .setMaxResults(size)
                    .getResultList();
        }

        for (int i = 0; i < items.size(); i++) {
            otherParamMap.put(items.get(i).getId(), countNumOfPeople(items.get(i).getId()));
        }

        DataTableResponse<Countries> response = new DataTableResponse<>();
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
        Query query = entityManager.createQuery("select count(c) from Countries c");
        return (long) query.getSingleResult();
    }

    @Override
    public List<String> findAllCountriesNames() {
        Query query = entityManager.createQuery("select c.nameOfCountry from Countries c");
        return query.getResultList();
    }

    @Override
    public int countNumOfPeople(Long id) {
        return findById(id).getPeople().size();
    }

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        List<Population> populationList = findById(id).getPeople().stream().toList();
        Map<Long, String> people = new HashMap<>();
        for (int i = 0; i < populationList.size(); i++) {
            people.put(populationList.get(i).getId(), populationList.get(i).getFirstName() + populationList.get(i).getLastName());
        }
        return people;
    }

    @Override
    public Countries findByName(String countryName) {
        Query query = entityManager.createQuery("select c from Countries c where c.nameOfCountry = :countryName")
                .setParameter("countryName", countryName);
        return (Countries) query.getSingleResult();
    }

    @Override
    public boolean existByISOAndCountyName(String nameOfCountry, Integer iso) {
        Query query = entityManager.createQuery("select count(c.id) from Countries c where c.ISO = :iso or c.nameOfCountry = :nameOfCountry")
                .setParameter("iso", iso)
                .setParameter("nameOfCountry", nameOfCountry);
        return (Long) query.getSingleResult() == 1;
    }
}
