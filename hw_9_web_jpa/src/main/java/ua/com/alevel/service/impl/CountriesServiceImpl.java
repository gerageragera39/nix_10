package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.service.CountriesService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.List;
import java.util.Map;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountriesDao countriesDao;

    public CountriesServiceImpl(CountriesDao countriesDao) {
        this.countriesDao = countriesDao;
    }

    @Override
    public void create(Countries entity) {
        if (!countriesDao.existByISOAndCountyName(entity.getNameOfCountry(), entity.getISO())) {
            countriesDao.create(entity);
        }
    }

    @Override
    public void update(Countries entity) {
        if (!countriesDao.existByISOAndCountyName(entity.getNameOfCountry(), entity.getISO())) {
            countriesDao.update(entity);
        }
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
        DataTableResponse<Countries> dataTableResponse = countriesDao.findAll(request);
        long count = countriesDao.countVisible();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public List<String> findAllCountriesNames() {
        return countriesDao.findAllCountriesNames();
    }

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        return countriesDao.findPeopleByCountryId(id);
    }

    @Override
    public Countries findByName(String countryName) {
        return countriesDao.findByName(countryName);
    }
}
