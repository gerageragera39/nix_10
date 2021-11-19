package ua.com.alevel.dao;

import ua.com.alevel.db.DBPopulation;
import ua.com.alevel.entity.Population;

import java.util.List;

public class PopulationDao {

    public void create(Population person) {
        DBPopulation.getInstance().create(person);
    }

    public static void update(Population person) {
        DBPopulation.getInstance().update(person);
    }

    public static void delete(String id) {
        DBPopulation.getInstance().delete(id);
    }

    public static Population findByPassportId(String id) {
        return DBPopulation.getInstance().findByPassportId(id);
    }

    public static List<Population> findAllPersons() {
        return DBPopulation.getInstance().findAllPersons();
    }

    public static int numOfAllPersons() {
        return DBPopulation.getInstance().numOfAllPersons();
    }

    public static boolean existByCountry(String nameOfCountry) {
        return DBPopulation.getInstance().existByCountry(nameOfCountry);
    }
}
