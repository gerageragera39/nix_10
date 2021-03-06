package ua.com.alevel.service;

import org.junit.jupiter.api.*;
import ua.com.alevel.WriterCSV;
import ua.com.alevel.entity.Countries;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountriesServiceTest {

    private static final CountiesService countiesService = new CountiesService();
    public static final String NAME_OF_COUNTRY = "testNameOfCountry";
    private static final String NAME_OF_COUNTRY_UPDATE = "testNameOfCountry_update";
    private static final int DEFAULT_SIZE = 20;
    private static int createdISO;
    public static final String path = "..\\hw_7_ionio\\src\\test\\resources\\test_countries.csv";
    public static final WriterCSV writerCSV = new WriterCSV(path);

    @BeforeAll
    public static void setUp() {
        countiesService.setPath(path);
        writerCSV.clearCSV();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Countries country = generateCountry(NAME_OF_COUNTRY + i);
            countiesService.create(country);
        }
        Assertions.assertEquals(countiesService.findAllCounties().size(), DEFAULT_SIZE);
    }

    @Test
    @Order(1)
    public void shouldByCreateCountryWhenCountryNameIsNotDuplicate() {
        countiesService.setPath(path);
        Countries country = generateRandomCountry();
        countiesService.create(country);
        createdISO = country.getISO();
        List<Countries> countries = countiesService.findAllCounties();
        Assertions.assertEquals(countries.size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(2)
    public void shouldByCreateCountryWhenCountryNameIsDuplicate() {
        countiesService.setPath(path);
        Countries country = generateRandomCountry();
        countiesService.create(country);
        List<Countries> countriesList = countiesService.findAllCounties();
        Assertions.assertEquals(countriesList.size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(3)
    public void shouldBeUpdateCountryByISO() {
        countiesService.setPath(path);
        Countries country = generateRandomCountry();
        country.setNameOfCountry(NAME_OF_COUNTRY_UPDATE);
        country.setISO(createdISO);
        countiesService.update(country, NAME_OF_COUNTRY);
        country = countiesService.findByISO(country.getISO());
        Assertions.assertEquals(NAME_OF_COUNTRY_UPDATE, country.getNameOfCountry());
    }

    @Test
    @Order(4)
    public void shouldBeDeleteCountryByISO() {
        countiesService.setPath(path);
        Countries country = generateRandomCountry();
        countiesService.delete(createdISO);
        Assertions.assertEquals(countiesService.findAllCounties().size(), DEFAULT_SIZE);
    }

    public static Countries generateRandomCountry() {
        Countries country = new Countries();
        country.setNameOfCountry(NAME_OF_COUNTRY);
        return country;
    }

    public static Countries generateCountry(String name) {
        Countries country = new Countries();
        country.setNameOfCountry(name);
        return country;
    }
}
