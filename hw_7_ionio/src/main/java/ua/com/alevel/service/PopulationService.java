package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.PopulationDao;
import ua.com.alevel.entity.Population;

import java.util.List;

public class PopulationService {

    private final PopulationDao populationDao = new PopulationDao();
    private static final Logger LOGGER = LoggerFactory.getLogger(PopulationService.class);
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
//    public static String path = "..\\nix_10\\hw_7_ionio\\src\\main\\resources\\population.csv";
//    public static String countryPath = "..\\nix_10\\hw_7_ionio\\src\\main\\resources\\countries.csv";
    public static String path = ".\\src\\main\\resources\\population.csv";
    public static String countryPath = ".\\src\\main\\resources\\countries.csv";

    public void create(Population person) {
        LOGGER_INFO.info("start creating person");
        if (existByCountry(person.getCountryOfResidence())) {
            populationDao.create(person, path);
            LOGGER_INFO.info("finish creating person : " +
                    "firstName='" + person.getFirstName() + '\'' +
                    ", lastName='" + person.getLastName() + '\'' +
                    ", countryOfResidence='" + person.getCountryOfResidence() + '\'' +
                    ", age=" + person.getAge() +
                    ", passportID='" + person.getPassportID() + '\'' +
                    ", sex=" + person.getSex() +
                    '}');
        } else {
            System.out.println();
            System.out.println("THERE ARE NO COUNTRY IN WHICH A PERSON LIVES");
            LOGGER_WARN.warn("person does not exist by country (creating): " + person.getCountryOfResidence());
        }
    }

    public void update(Population person) {
        LOGGER_INFO.info("start updating person");
        if (existByCountry(person.getCountryOfResidence())) {
            PopulationDao.update(person, path);
            LOGGER_INFO.info("finish updating person : " + person.getCountryOfResidence());
        } else {
            System.out.println();
            System.out.println("THERE ARE NO COUNTRY IN WHICH A PERSON COULD LIVE");
            LOGGER_WARN.warn("person does not exist by country (updating): " + person.getCountryOfResidence());
        }
        PopulationDao.update(person, path);
    }

    public void delete(String id) {
        LOGGER_INFO.info("start deleting person");
        PopulationDao.delete(id, path);
        LOGGER_INFO.info("finish deleting person : ");
    }

    public static Population findByPassportId(String id) {
        return PopulationDao.findByPassportId(id, path);
    }

    public static List<Population> findAllPersons() {
        return PopulationDao.findAllPersons(path);
    }

    public static int numOfAllPersons() {
        return PopulationDao.numOfAllPersons(path);
    }

    public boolean existByCountry(String nameOfCountry) {
        return PopulationDao.existByCountry(nameOfCountry, countryPath);
    }

    public static void setPath(String path) {
        PopulationService.path = path;
    }

    public static void setCountryPath(String countryPth) {
        PopulationService.countryPath = countryPth;
    }


//    public static void setPath(String path) {
//        CountiesService.path = path;
//    }
}
