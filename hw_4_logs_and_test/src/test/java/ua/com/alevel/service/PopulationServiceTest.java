package ua.com.alevel.service;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PopulationServiceTest {

    private static final PopulationService populationService = new PopulationService();
    private static final CountiesService countiesService = new CountiesService();

    private static final String FIRST_NAME = "name";
    private static final String FIRST_NAME_UPDATE = "name_update";
    private static final String LAST_NAME = "lastName";
    private static final String LAST_NAME_UPDATE = "lastName_update";
    private static final String COUNTRY_OF_RESIDENCE = "Ukraine";
    private static final String COUNTRY_OF_RESIDENCE_UPDATE = "Ukraine_update";
    private static final String SEX = "M";
    private static final int AGE = 20;
    private static final int AGE_UPDATE = 25;
    private static final int DEFAULT_SIZE = 20;
    private static String createdID;

    @BeforeAll
    public static void setUp() {
        Countries country = CountriesServiceTest.generateRandomCountry();
        countiesService.create(country);
        Countries[] countries = countiesService.findAllCounties();

        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Population person = generatePerson(FIRST_NAME + i, LAST_NAME + i, CountriesServiceTest.NAME_OF_COUNTRY, i, SEX);
            populationService.create(person);
        }
        Assertions.assertEquals(populationService.findAllPersons().length, DEFAULT_SIZE);
    }

    @Test
    @Order(1)
    public void shouldBeCreatePersonWhenCountryCreated() {
        Countries country = CountriesServiceTest.generateRandomCountry();
        countiesService.create(country);
        Countries[] countries = countiesService.findAllCounties();

        Population person = generateRandomPerson();
        populationService.create(person);
        createdID = person.getPassportID();
        Population[] people = populationService.findAllPersons();
        Assertions.assertEquals(people.length, DEFAULT_SIZE + 1);
    }

    @Test
    @Order(2)
    public void shouldBeCreatePersonWhenCountryNotCreated() {
        Population person = generatePerson(FIRST_NAME, LAST_NAME, COUNTRY_OF_RESIDENCE, AGE, SEX);
        populationService.create(person);
        Population[] people = populationService.findAllPersons();
        Assertions.assertEquals(people.length, DEFAULT_SIZE + 1);
    }

    @Test
    @Order(3)
    public void shouldBeUpdatePersonByPassportId() {
        Population person = generateRandomPerson();
        person.setFirstName(FIRST_NAME_UPDATE);
        person.setLastName(LAST_NAME_UPDATE);
        person.setCountryOfResidence(CountriesServiceTest.NAME_OF_COUNTRY);
        person.setAge(AGE_UPDATE);
        person.setPassportID(createdID);
        populationService.update(person);

        person = populationService.findByPassportId(person.getPassportID());
        Assertions.assertEquals(AGE_UPDATE, person.getAge());
        Assertions.assertEquals(FIRST_NAME_UPDATE, person.getFirstName());
        Assertions.assertEquals(LAST_NAME_UPDATE, person.getLastName());
    }

    @Test
    @Order(4)
    public void shouldBeDeletePersonByPassportId() {
        Population person = generateRandomPerson();
        populationService.delete(createdID);
        Assertions.assertEquals(populationService.findAllPersons().length, DEFAULT_SIZE);
    }

    private static Population generateRandomPerson() {
        Population person = new Population();
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setCountryOfResidence(CountriesServiceTest.NAME_OF_COUNTRY);
        person.setSex(SEX);
        person.setAge(AGE);
        return person;
    }

    private static Population generatePerson(String firstName, String lastName, String countryOfResidence, int age, String sex) {
        Population person = new Population();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setCountryOfResidence(countryOfResidence);
        person.setSex(sex);
        person.setAge(age);
        return person;
    }
}
