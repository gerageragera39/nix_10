package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request) {
        return null;
    }

//    @Override
//    public List<Population> findAll(int page, int size, String sort, String order) {
//        return null;
//    }
}
