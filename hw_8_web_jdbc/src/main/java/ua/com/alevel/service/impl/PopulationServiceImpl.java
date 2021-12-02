package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.service.PopulationService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Map;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final PopulationDao populationDao;

    public PopulationServiceImpl(PopulationDao populationDao) {
        this.populationDao = populationDao;
    }

    @Override
    public void create(Population entity) {
        if (!populationDao.existById(entity.getPassportID())) {
            populationDao.create(entity);
        }
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
        DataTableResponse<Population> dataTableResponse = populationDao.findAll(request);
        long count = populationDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Long, String> findByCountryId(Long id) {
        return populationDao.findByCountryId(id);
    }

    @Override
    public void createRelation(String countryName, String personPassportId) {
        populationDao.createRelation(countryName, personPassportId);
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        populationDao.addRelation(countryName, personPassportId);
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        populationDao.removeRelation(countryName, personPassportId);
    }

    @Override
    public DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest) {
        DataTableResponse<Population> dataTableResponse = populationDao.findAllNotVisible(dataTableRequest);
        long count = populationDao.notVisibleCount();
        WebResponseUtil.initDataTableResponse(dataTableRequest, dataTableResponse, count);
        return dataTableResponse;
    }
}
