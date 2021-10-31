package ua.com.alevel.db;

import ua.com.alevel.entity.Countries;

public class DBCountries {

    private Countries[][] countries;
    private static DBCountries instance;

    private DBCountries() {
        countries = new Countries[0][0];
    }

    public static DBCountries getInstance() {
        if (instance == null) {
            instance = new DBCountries();
        }
        return instance;
    }


}
