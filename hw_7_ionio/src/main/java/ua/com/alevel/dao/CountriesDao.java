package ua.com.alevel.dao;

import ua.com.alevel.db.DBCountries;
import ua.com.alevel.entity.Countries;

import java.util.List;

public class CountriesDao {

    public static void create(Countries country, String path) {
        DBCountries.setPath(path);
        DBCountries.getInstance().create(country);
    }

    public static void update(Countries country, String previousName, String path) {
        DBCountries.setPath(path);
        DBCountries.getInstance().update(country, previousName);
    }

    public static void delete(int IOS, String path) {
        DBCountries.setPath(path);
        DBCountries.getInstance().delete(IOS);
    }

    public static Countries findByISO(int IOS, String path) {
        DBCountries.setPath(path);
        return DBCountries.getInstance().findByISO(IOS);
    }

    public static List<Countries> findAllCounties(String path) {
        DBCountries.setPath(path);
        return DBCountries.getInstance().findAllCounties(path);
    }

    public static int numOfAllCountries(String path) {
        DBCountries.setPath(path);
        return DBCountries.getInstance().numOfAllCountries();
    }

    public static boolean existByCountryName(String name, String path) {
        DBCountries.setPath(path);
        return DBCountries.getInstance().existByCountryName(name);
    }
}
