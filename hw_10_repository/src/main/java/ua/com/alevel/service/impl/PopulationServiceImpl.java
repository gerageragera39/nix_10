package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.repository.PopulationRepository;
import ua.com.alevel.service.PopulationService;

import java.util.*;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final CrudRepositoryHelper<Population, PopulationRepository> crudRepositoryHelper;
    private final PopulationRepository populationRepository;

    public PopulationServiceImpl(CrudRepositoryHelper<Population, PopulationRepository> crudRepositoryHelper, PopulationRepository employeeRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.populationRepository = employeeRepository;
    }

    @Override
    public void create(Population entity) {
        if (populationRepository.findAllByPassportID(entity.getPassportID()).size() == 0 && validAge(entity.getAge())) {
            crudRepositoryHelper.create(populationRepository, entity);
        }
    }

    @Override
    public void update(Population entity) {
        if (validAge(entity.getAge())) {
            crudRepositoryHelper.update(populationRepository, entity);
        }
    }

    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(populationRepository, id, Population.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Population> findById(Long id) {
        return crudRepositoryHelper.findById(populationRepository, id);
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request, boolean visible) {
        return crudRepositoryHelper.findAll(populationRepository, request, Population.class, visible);
    }

    @Override
    public Map<Long, String> findCountriesByPersonId(Long id) {
        Map<Long, String> map = new HashMap<>();
        List<Countries> countries = populationRepository.findCountriesByPersonId(id);
        for (int i = 0; i < countries.size(); i++) {
            map.put(countries.get(i).getId(), countries.get(i).getNameOfCountry());
        }
        return map;
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        List<Countries> list = populationRepository.findCountriesByPersonId(id);
        List<String> addedNames = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            addedNames.add(list.get(i).getNameOfCountry());
        }
        return addedNames;
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        crudRepositoryHelper.addRelation(countryName, personPassportId);
        Population person = populationRepository.findByPassportID(personPassportId);

        if (person.getCountries().size() != 0) {
            person.setVisible(true);
            update(person);
        }
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        crudRepositoryHelper.removeRelation(countryName, personPassportId);
        Population person = populationRepository.findByPassportID(personPassportId);

        if (person.getCountries().size() == 0) {
            person.setVisible(false);
            update(person);
        }
    }

    private boolean validAge(int age) {
        if ((age > 0) && (age < 122)) {
            return true;
        }
        return false;
    }
}
