package ua.com.alevel.service;

import ua.com.alevel.dao.PopulationDao;
import ua.com.alevel.entity.Population;

public class PopulationService {

    private final PopulationDao populationDao = new PopulationDao();

    public void create(Population person) {
        if (existByCountry(person.getCountryOfResidence())) {
            populationDao.create(person);
        } else {
            System.out.println();
            System.out.println("THERE ARE NO COUNTRY IN WHICH A PERSON LIVES");
        }
    }

    public void update(Population person) {
        if (existByCountry(person.getCountryOfResidence())) {
            PopulationDao.update(person);
        } else {
            System.out.println();
            System.out.println("THERE ARE NO COUNTRY IN WHICH A PERSON COULD LIVE");
        }

        PopulationDao.update(person);
    }

    public void delete(String id) {
        PopulationDao.delete(id);
    }

    public static Population findByPassportId(String id) {
        return PopulationDao.findByPassportId(id);
    }

    public static Population[] findAllPersons() {
        return PopulationDao.findAllPersons();
    }

    public static int numOfAllPersons() {
        return PopulationDao.numOfAllPersons();
    }

    public boolean existByCountry(String nameOfCountry) {
        return PopulationDao.existByCountry(nameOfCountry);
    }
}
