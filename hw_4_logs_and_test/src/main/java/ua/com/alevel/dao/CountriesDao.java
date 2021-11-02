package ua.com.alevel.dao;

import ua.com.alevel.db.DBCountries;
import ua.com.alevel.entity.Countries;

public class CountriesDao {
    public static void create(Countries user) {
        DBCountries.getInstance().create(user);
    }

    public static void update(Countries country, String previousName) {
        DBCountries.getInstance().update(country, previousName);
    }

    public static void delete(int IOS) {
        DBCountries.getInstance().delete(IOS);
    }

    public static Countries findByISO(int IOS) {
        return DBCountries.getInstance().findByISO(IOS);
    }

    public static Countries[] findAllCounties() {
        return DBCountries.getInstance().findAllCounties();
    }

    public static int numOfAllCountries() {
        return DBCountries.getInstance().numOfAllCountries();
    }
}
