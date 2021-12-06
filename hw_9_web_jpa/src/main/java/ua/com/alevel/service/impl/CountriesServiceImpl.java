package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.service.CountriesService;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountriesDao countriesDao;

    public CountriesServiceImpl(CountriesDao countriesDao) {
        this.countriesDao = countriesDao;
    }

    @Override
    public void create(Countries entity) {
        countriesDao.create(entity);
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
        return findById(id);
    }

    @Override
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
        return null;
    }
}
