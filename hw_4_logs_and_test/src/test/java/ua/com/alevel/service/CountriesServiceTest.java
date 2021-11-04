package ua.com.alevel.service;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Countries;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountriesServiceTest {

    private static final CountiesService countiesService = new CountiesService();
    public static final String NAME_OF_COUNTRY = "testNameOfCountry";
    private static final String NAME_OF_COUNTRY_UPDATE = "testNameOfCountry_update";
    private static final int DEFAULT_SIZE = 20;
    private static int createdISO;

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Countries country = generateCountry(NAME_OF_COUNTRY + i);
            countiesService.create(country);
        }
        Assertions.assertEquals(countiesService.findAllCounties().length, DEFAULT_SIZE);
    }

    @Test
    @Order(1)
    public void shouldByCreateCountryWhenCountryNameIsNotDuplicate() {
        Countries country = generateRandomCountry();
        countiesService.create(country);
        createdISO = country.getISO();
        Countries[] countries = countiesService.findAllCounties();
        Assertions.assertEquals(countries.length, DEFAULT_SIZE + 1);
    }

    @Test
    @Order(2)
    public void shouldByCreateCountryWhenCountryNameIsDuplicate() {
        Countries country = generateRandomCountry();
        countiesService.create(country);
        Countries[] countries = countiesService.findAllCounties();
        Assertions.assertEquals(countries.length, DEFAULT_SIZE + 1);
    }

    @Test
    @Order(3)
    public void shouldBeUpdateCountryByISO() {
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
        Countries country = generateRandomCountry();
        countiesService.delete(createdISO);
        Assertions.assertEquals(countiesService.findAllCounties().length, DEFAULT_SIZE);
    }

    public static Countries generateRandomCountry(){
        Countries country = new Countries();
        country.setNameOfCountry(NAME_OF_COUNTRY);
        return country;
    }

    public static Countries generateCountry(String name){
        Countries country = new Countries();
        country.setNameOfCountry(name);
        return country;
    }
}
