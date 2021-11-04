package ua.com.alevel.service;

import ua.com.alevel.dao.CountriesDao;
import ua.com.alevel.db.DBCountries;
import ua.com.alevel.entity.Countries;

public class CountiesService {

    public void create(Countries country) {
        if(existByCountryName(country.getNameOfCountry())){
            CountriesDao.create(country);
        } else{
            System.out.println();
            System.out.println("COUNTRY DOES NOT EXIST BY THIS NAME");
        }
    }

    public void update(Countries country, String previousName) {
        if(existByCountryName(country.getNameOfCountry())){
            CountriesDao.update(country, previousName);
        } else{
            System.out.println();
            System.out.println("COUNTRY DOES NOT EXIST BY THIS NAME");
        }
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

    public boolean existByCountryName(String name){
        return CountriesDao.existByCountryName(name);
    }
}
