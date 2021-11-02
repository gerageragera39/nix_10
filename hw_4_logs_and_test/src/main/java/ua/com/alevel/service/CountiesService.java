package ua.com.alevel.service;

import ua.com.alevel.dao.CountriesDao;
import ua.com.alevel.db.DBCountries;
import ua.com.alevel.entity.Countries;

public class CountiesService {

    public void create(Countries country) {
        CountriesDao.create(country);
    }

    public void update(Countries country, String previousName) {
        CountriesDao.update(country, previousName);
    }

    public void delete(int IOS) {
        CountriesDao.delete(IOS);
    }

    public Countries findByISO(int IOS) {
        return CountriesDao.findByISO(IOS);
    }

    public Countries[] findAllCounties() {
        return CountriesDao.findAllCounties();
    }

    public int numOfAllCountries() {
        return CountriesDao.numOfAllCountries();
    }

}
