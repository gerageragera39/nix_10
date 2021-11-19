package ua.com.alevel.controller;

import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;
import ua.com.alevel.service.CountiesService;
import ua.com.alevel.service.PopulationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CountriesController {

    private static final CountiesService countiesService = new CountiesService();
    private static final PopulationService populationService = new PopulationService();

    public static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position = "0";
        runNavigation();
        try {
            while (position != null) {
                System.out.print("select your option for countries : ");
                position = reader.readLine();
                if (!position.equals("0")) {
                    crud(position, reader);
                }
                if (position.equals("0")) {
                    RelationController.run();
                }
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void runNavigation() {
        System.out.println();
        System.out.println("if you want create country, please enter 1");
        System.out.println("if you want update country, please enter 2");
        System.out.println("if you want delete country, please enter 3");
        System.out.println("if you want findISO country, please enter 4");
        System.out.println("if you want findAll countries, please enter 5");
        System.out.println("if you want numberOfAll countries, please enter 6");
        System.out.println("if you want findPersonsOfCountry countries, please enter 7");
        System.out.println("if you want exit from countries, please enter 0");
        System.out.println();
    }

    private static void crud(String position, BufferedReader reader) throws IOException {
        switch (position) {
            case "1":
                create(reader);
                break;
            case "2":
                update(reader);
                break;
            case "3":
                delete(reader);
                break;
            case "4":
                findByISO(reader);
                break;
            case "5":
                findAllCountries(reader);
                break;
            case "6":
                numOfAllCountries();
                break;
            case "7":
                findPersonsOfCountry(reader);
                break;
        }

        runNavigation();
    }

    private static void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter name of country : ");
            String nameOfCountry = reader.readLine();
            Countries country = new Countries();
            country.setNameOfCountry(nameOfCountry);
            countiesService.create(country);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void update(BufferedReader reader) {
        try {
            System.out.print("Please, enter country ISO : ");
            String StringISO = reader.readLine();
            int ISO = Integer.parseInt(StringISO);
            String previousName = countiesService.findByISO(ISO).getNameOfCountry();
            System.out.print("Please, enter new name of country : ");
            String nameOfCountry = reader.readLine();
            Countries newCountry = new Countries();
            newCountry.setNameOfCountry(nameOfCountry);
            newCountry.setISO(ISO);
            countiesService.update(newCountry, previousName);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter country ISO : ");
            String StringISO = reader.readLine();
            int ISO = Integer.parseInt(StringISO);
            countiesService.delete(ISO);
            System.out.println("Country was deleted");
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static Countries findByISO(BufferedReader reader) {
        try {
            System.out.print("Please, enter ISO : ");
            String StringISO = reader.readLine();
            int ISO = Integer.parseInt(StringISO);
            Countries country = countiesService.findByISO(ISO);
            System.out.println(country.toString());
            return country;
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return new Countries();
    }

    private static void findAllCountries(BufferedReader reader) {
        List<Countries> countries = countiesService.findAllCounties();
        if (countries != null && countries.size() != 0) {
            for (Countries country : countries) {
                System.out.println(country.toString());
            }
        } else {
            System.out.println("THERE ARE NO COUNTRIES");
        }
    }

    public static void numOfAllCountries() {
        System.out.println("Number Of All Countries = " + countiesService.numOfAllCountries());
    }

    public static void findPersonsOfCountry(BufferedReader reader) throws IOException {
        Countries country = findByISO(reader);
        List<Population> people = populationService.findAllPersons();
        if (people != null && people.size() != 0) {
            for (Population person : people) {
                if (person.getCountryOfResidence().equals(country.getNameOfCountry())) {
                    System.out.println(person.toString());
                }
            }
        } else {
            System.out.println("THERE ARE NO PEOPLE IN THIS COUNTRY");
        }
    }
}
