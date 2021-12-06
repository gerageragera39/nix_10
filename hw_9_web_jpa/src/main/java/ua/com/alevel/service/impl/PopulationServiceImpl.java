package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.service.PopulationService;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final PopulationDao populationDao;

    public PopulationServiceImpl(PopulationDao populationDao) {
        this.populationDao = populationDao;
    }

    @Override
    public void create(Population entity) {
        populationDao.create(entity);
    }

    @Override
    public void update(Population entity) {
        populationDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        populationDao.delete(id);
    }

    @Override
    public Population findById(Long id) {
        return populationDao.findById(id);
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request) {
        return null;
    }
}
