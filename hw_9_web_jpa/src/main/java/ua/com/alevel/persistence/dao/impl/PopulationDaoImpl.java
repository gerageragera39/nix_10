package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PopulationDaoImpl implements PopulationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Population entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Population entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Population person where person.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(p.id) from Population p where p.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Population findById(Long id) {
        return entityManager.find(Population.class, id);
//        Query query = entityManager.createQuery("select p.firstName from Population p where p.id = :id")
//                .setParameter("id", id);
//        return (Population) query.getSingleResult();
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request) {
        List<Population> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Population> criteriaQuery = criteriaBuilder.createQuery(Population.class);
        Root<Population> from = criteriaQuery.from(Population.class);

        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = page + request.getPageSize();

        if (request.getSort().equals("countryCount")) {
            Query query;
            if (request.getOrder().equals("desc")) {
                query = entityManager.createQuery("select p from Population p where p.visible = true order by p.countries.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select p from Population p where p.visible = true order by p.countries.size asc")
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
            otherParamMap.put(items.get(i).getId(), countNumOfCountries(items.get(i).getId()));
        }

        DataTableResponse<Population> response = new DataTableResponse<>();
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
        Query query = entityManager.createQuery("select count(p) from Population p where p.visible = true");
        return (long) query.getSingleResult();
    }

    @Override
    public int countNumOfCountries(Long id) {
        return findById(id).getCountries().size();
    }

    @Override
    public Map<Long, String> findCountriesByPersonId(Long id) {
        List<Countries> countriesList = findById(id).getCountries().stream().toList();
        Map<Long, String> countries = new HashMap<>();
        for (int i = 0; i < countriesList.size(); i++) {
            countries.put(countriesList.get(i).getId(), countriesList.get(i).getNameOfCountry());
        }
        return countries;
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        List<Countries> countriesList = findById(id).getCountries().stream().toList();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < countriesList.size(); i++) {
            names.add(countriesList.get(i).getNameOfCountry());
        }
        return names;
    }

    @Override
    public List<String> findAllCountriesNames() {
        Query query = entityManager.createQuery("select c.nameOfCountry from Countries c");
        List<String> allNames = query.getResultList();
        return allNames;
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        List<Object> list = findPersonByPassportIdAndCountryByName(countryName, personPassportId);
        Countries country = (Countries) list.get(0);
        Population person = (Population) list.get(1);

        if (person.getCountries().size() == 0) {
            person.setVisible(true);
            update(person);
        }
        country.addPerson(person);
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        List<Object> list = findPersonByPassportIdAndCountryByName(countryName, personPassportId);
        Countries country = (Countries) list.get(0);
        Population person = (Population) list.get(1);

        country.removePerson(person);
        if (person.getCountries().size() == 0) {
            person.setVisible(false);
            update(person);
        }
    }

    @Override
    public DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest) {
        List<Population> items;
        Map<Object, Object> otherParamMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Population> criteriaQuery = criteriaBuilder.createQuery(Population.class);
        Root<Population> from = criteriaQuery.from(Population.class);
        Predicate cond = criteriaBuilder.and(criteriaBuilder.equal(from.get("visible"), false));
        int page = (dataTableRequest.getCurrentPage() - 1) * dataTableRequest.getPageSize();
        int size = page + dataTableRequest.getPageSize();

        if (dataTableRequest.getSort().equals("countryCount")) {
            Query query;
            if (dataTableRequest.getOrder().equals("desc")) {
                query = entityManager.createQuery("select p from Population p where p.visible = false order by p.countries.size desc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            } else {
                query = entityManager.createQuery("select p from Population p where p.visible = false order by p.countries.size asc")
                        .setFirstResult(page)
                        .setMaxResults(size);
            }
            items = query.getResultList();
        } else {
            if (dataTableRequest.getOrder().equals("desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(from.get(dataTableRequest.getSort())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(from.get(dataTableRequest.getSort())));
            }
            criteriaQuery.where(cond);
            items = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(page)
                    .setMaxResults(size)
                    .getResultList();
        }

        for (int i = 0; i < items.size(); i++) {
            otherParamMap.put(items.get(i).getId(), countNumOfCountries(items.get(i).getId()));
        }

        DataTableResponse<Population> response = new DataTableResponse<>();
        response.setSort(dataTableRequest.getSort());
        response.setOrder(dataTableRequest.getOrder());
        response.setCurrentPage(dataTableRequest.getCurrentPage());
        response.setCurrentSize(dataTableRequest.getPageSize());
        response.setItems(items);
        response.setOtherParamMap(otherParamMap);

        return response;
    }

    @Override
    public long countNotVisible() {
        Query query = entityManager.createQuery("select count(p) from Population p where p.visible = false");
        return (long) query.getSingleResult();
    }

    @Override
    public boolean existByPassportId(String passportID) {
        Query query = entityManager.createQuery("select count(p.id) from Population p where p.passportID = :passportID")
                .setParameter("passportID", passportID);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public List<Object> findPersonByPassportIdAndCountryByName(String countryName, String personPassportId) {
        Query query1 = entityManager.createQuery("select c from Countries c where c.nameOfCountry = :countryName")
                .setParameter("countryName", countryName);
        List<Countries> countries = query1.getResultList();
        Countries country = countries.get(0);

        Query query2 = entityManager.createQuery("select p from Population p where p.passportID = :personPassportId")
                .setParameter("personPassportId", personPassportId);
        List<Population> populations = query2.getResultList();
        Population person = populations.get(0);

        List<Object> list = new ArrayList<>();
        list.add(country);
        list.add(person);
        return list;
    }
}
