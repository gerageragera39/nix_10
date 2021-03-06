package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.service.PopulationService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;
import java.util.Map;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final PopulationDao populationDao;

    public PopulationServiceImpl(PopulationDao populationDao) {
        this.populationDao = populationDao;
    }

    @Override
    public void create(Population entity) {
        if (!populationDao.existByPassportId(entity.getPassportID()) && ageCheck(entity.getAge())) {
            populationDao.create(entity);
        }
    }

    @Override
    public void update(Population entity) {
        if(ageCheck(entity.getAge())) {
            populationDao.update(entity);
        }
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
        long count = populationDao.countVisible();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Long, String> findCountriesByPersonId(Long id) {
        return populationDao.findCountriesByPersonId(id);
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        return populationDao.findNamesByPersonId(id);
    }

    @Override
    public List<String> findAllCountriesNames() {
        return populationDao.findAllCountriesNames();
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        if(populationDao.existByPassportId(personPassportId)){
            populationDao.addRelation(countryName, personPassportId);
        }
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        populationDao.removeRelation(countryName, personPassportId);
    }

    @Override
    public DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest) {
        DataTableResponse<Population> dataTableResponse = populationDao.findAllNotVisible(dataTableRequest);
        long count = populationDao.countNotVisible();
        WebResponseUtil.initDataTableResponse(dataTableRequest, dataTableResponse, count);
        return dataTableResponse;
    }

    public boolean ageCheck(int age) {
        if ((age > 0) && (age < 122)) {
            return true;
        }
        return false;
    }
}
