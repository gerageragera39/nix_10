package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import ua.com.alevel.persistense.crud.CrudRepositoryHelper;
import ua.com.alevel.persistense.datatable.DataTableRequest;
import ua.com.alevel.persistense.datatable.DataTableResponse;
import ua.com.alevel.persistense.entity.Countries;
import ua.com.alevel.persistense.repository.CountriesRepository;
import ua.com.alevel.service.CountriesService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CrudRepositoryHelper<Countries, CountriesRepository> crudRepositoryHelper;
    private final CountriesRepository countriesRepository;

    public CountriesServiceImpl(CrudRepositoryHelper<Countries, CountriesRepository> crudRepositoryHelper, CountriesRepository countriesRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.countriesRepository = countriesRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void create(Countries country) {
        crudRepositoryHelper.create(countriesRepository, country);
    }

    @Override
    public void update(Countries entity) {
        crudRepositoryHelper.update(countriesRepository, entity);
    }

    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(countriesRepository, id);
    }

    @Override
    public Optional<Countries> findById(Long id) {
        return crudRepositoryHelper.findById(countriesRepository, id);
    }

    @Override
    @Transactional()
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(countriesRepository, request);
    }

    @Override
    public List<String> findAllCountriesNames() {
        return null;
    }

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        return null;
    }

    @Override
    public Countries findByName(String countryName) {
        return null;
    }

//    @Override
////    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void help() {
//        System.out.println("DepartmentServiceImpl.help");
//        crudRepositoryHelper.help();
//        crudRepositoryHelper.help();
//        crudRepositoryHelper.help();
//        crudRepositoryHelper.help();
//    }
}
