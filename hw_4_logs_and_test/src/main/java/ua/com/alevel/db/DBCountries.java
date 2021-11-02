package ua.com.alevel.db;

import ua.com.alevel.controller.CountriesController;
import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;

import java.util.Arrays;

public class DBCountries {

    private Countries[] countries;
    private static DBCountries instance;

    public DBCountries() {
        countries = new Countries[0];
    }

    public static DBCountries getInstance() {
        if (instance == null) {
            instance = new DBCountries();
        }
        return instance;
    }

    public void create(Countries newCountry) {
        newCountry.setISO(generateISO());
        countries = Arrays.copyOf(countries, countries.length + 1);
        countries[countries.length - 1] = newCountry;
    }

    public void update(Countries updateCountry, String previousName) {
        Countries current = findByISO(updateCountry.getISO());
        for (int i = 0; i < DBPopulation.getInstance().getPeople().length; i++) {
            if (DBPopulation.getInstance().getPeople()[i].getCountryOfResidence().equals(previousName)) {
                DBPopulation.getInstance().getPeople()[i].setCountryOfResidence(updateCountry.getNameOfCountry());
            }
        }
        current.setNameOfCountry(updateCountry.getNameOfCountry());
    }

    public void delete(int IOS) {
        Countries countryToDelete = findByISO(IOS);

        for (int i = 0; i < DBPopulation.getInstance().getPeople().length; i++) {
            if (DBPopulation.getInstance().getPeople()[i].getCountryOfResidence().equals(countryToDelete.getNameOfCountry())) {
                DBPopulation.getInstance().delete(DBPopulation.getInstance().getPeople()[i].getPassportID());
            }
        }
        int index = -1;
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].getISO() == countryToDelete.getISO()) {
                countries[i] = null;
                index = i;
            }
        }
        Countries arrayWithDeletedUser[] = new Countries[countries.length - 1];
        for (int i = 0; i < index; i++) {
            arrayWithDeletedUser[i] = countries[i];
        }
        for (int i = index; i < arrayWithDeletedUser.length; i++) {
            arrayWithDeletedUser[i] = countries[i + 1];
        }
        countries = Arrays.copyOf(arrayWithDeletedUser, countries.length - 1);
    }

    public Countries findByISO(int IOS) {

        for (int j = 0; j < countries.length; j++) {
            if (IOS == countries[j].getISO()) {
                return countries[j];
            }
        }

        System.out.println();
        System.out.println("COUNTRY NOT FOUND");
        CountriesController controller = new CountriesController();
        controller.run();
        return countries[0];
    }

    public Countries[] findAllCounties() {
        return countries;
    }

    private int generateISO() {

        int id = (int) (Math.random() * 999) + 1;
        for (int i = 0; i < countries.length; i++) {
            if (id == countries[i].getISO()) {
                generateISO();
            }
        }

        return id;
    }

    public int numOfAllCountries() {
        return countries.length;
    }

    public Countries[] getCountries() {
        return countries;
    }

    public static Population[] getPeople() {
        return Countries.getPeople();
    }
}

