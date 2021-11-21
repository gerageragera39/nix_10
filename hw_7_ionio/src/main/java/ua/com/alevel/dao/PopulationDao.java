package ua.com.alevel.dao;

import ua.com.alevel.db.DBPopulation;
import ua.com.alevel.entity.Population;

import java.util.List;

public class PopulationDao {

    public void create(Population person, String path) {
        DBPopulation.setPath(path);
        DBPopulation.getInstance().create(person);
    }

    public static void update(Population person, String path) {
        DBPopulation.setPath(path);
        DBPopulation.getInstance().update(person);
    }

    public static void delete(String id, String path) {
        DBPopulation.setPath(path);
        DBPopulation.getInstance().delete(id);
    }

    public static Population findByPassportId(String id, String path) {
        DBPopulation.setPath(path);
        return DBPopulation.getInstance().findByPassportId(id);
    }

    public static List<Population> findAllPersons(String path) {
        DBPopulation.setPath(path);
        return DBPopulation.getInstance().findAllPersons();
    }

    public static int numOfAllPersons(String path) {
        DBPopulation.setPath(path);
        return DBPopulation.getInstance().numOfAllPersons();
    }

    public static boolean existByCountry(String nameOfCountry, String path) {
        DBPopulation.setPath(path);
        return DBPopulation.getInstance().existByCountry(nameOfCountry);
    }
}
