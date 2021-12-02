package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.service.CountiesService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;
import java.util.Map;

@Service
public class CountiesServiceImpl implements CountiesService {

    private final CountriesDao countriesDao;

    public CountiesServiceImpl(CountriesDao countriesDao) {
        this.countriesDao = countriesDao;
    }

    @Override
    public void create(Countries entity) {
        if(!countriesDao.existById(entity.getNameOfCountry(), entity.getISO())){
            countriesDao.create(entity);
        }
    }

    @Override
    public void update(Countries entity) {
        countriesDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        countriesDao.delete(id);
    }

    @Override
    public Countries findById(Long id) {
        return countriesDao.findById(id);
    }

    @Override
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
//        return countriesDao.findAll(request);
        DataTableResponse<Countries> dataTableResponse = countriesDao.findAll(request);
        long count = countriesDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    public List<String> findAllCountriesNames(){
        return countriesDao.findAllCountriesNames();
    }

    @Override
    public Map<Long, String> findByCountryId(Long id) {
        return countriesDao.findByCountryId(id);
    }
}
