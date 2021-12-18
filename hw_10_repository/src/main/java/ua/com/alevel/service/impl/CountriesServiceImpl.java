package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.repository.CountriesRepository;
import ua.com.alevel.service.CountriesService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        if(countriesRepository.findByNameOfCountryOrISO(country.getNameOfCountry(), country.getISO()).size() == 0) {
            crudRepositoryHelper.create(countriesRepository, country);
        }
    }

    @Override
    public void update(Countries entity) {
        if(countriesRepository.findByNameOfCountryOrISO(entity.getNameOfCountry(), entity.getISO()).size() == 0) {
            crudRepositoryHelper.update(countriesRepository, entity);
        }
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
        return countriesRepository.findAllCountriesNames();
    }

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        Map<Long, String> map = new HashMap<>();
        List<Population> population = countriesRepository.findPeopleByCountryId(id);
        for (int i = 0; i < population.size(); i++) {
            map.put(population.get(i).getId(), population.get(i).getFirstName() + " " + population.get(i).getLastName());
        }
        return map;
    }
}
