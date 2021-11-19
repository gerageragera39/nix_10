package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.CountriesDao;
import ua.com.alevel.entity.Countries;

import java.util.List;

public class CountiesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountiesService.class);
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public void create(Countries country) {
        LOGGER_INFO.info("start creating country");
        if (existByCountryName(country.getNameOfCountry())) {
            CountriesDao.create(country);
            LOGGER_INFO.info("finish creating country : " + country.getNameOfCountry());
        } else {
            System.out.println();
            System.out.println("COUNTRY DOES NOT EXIST BY THIS NAME");
            LOGGER_WARN.warn("country does not exist by nameOfCountry (creating): " + country.getNameOfCountry());
        }
    }

    public void update(Countries country, String previousName) {
        LOGGER_INFO.info("start updating country : ");
        if (existByCountryName(country.getNameOfCountry())) {
            CountriesDao.update(country, previousName);
            LOGGER_INFO.info("finish updating country : " + country.getNameOfCountry());
        } else {
            System.out.println();
            System.out.println("COUNTRY DOES NOT EXIST BY THIS NAME");
            LOGGER_WARN.warn("country does not exist by nameOfCountry (updating): " + country.getNameOfCountry());
        }
    }

    public void delete(int IOS) {
        LOGGER_INFO.info("start deleting country");
        CountriesDao.delete(IOS);
        LOGGER_INFO.info("finish deleting country : ");
    }

    public Countries findByISO(int IOS) {
        return CountriesDao.findByISO(IOS);
    }

    public List<Countries> findAllCounties() {
        return CountriesDao.findAllCounties();
    }

    public int numOfAllCountries() {
        return CountriesDao.numOfAllCountries();
    }

    public boolean existByCountryName(String name) {
        return CountriesDao.existByCountryName(name);
    }
}
