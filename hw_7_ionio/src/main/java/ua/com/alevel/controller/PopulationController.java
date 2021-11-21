package ua.com.alevel.controller;

import ua.com.alevel.entity.Population;
import ua.com.alevel.service.PopulationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PopulationController {

    private static final PopulationService populationService = new PopulationService();

    public static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position = "0";
        runNavigation();
        try {
            while (position != null) {
                System.out.print("select your option for population : ");
                position = reader.readLine();
                if (!position.equals("0")) {
                    crud(position, reader);
                }
                if (position.equals("0")) {
                    RelationController.run();
                }
            }
        } catch (IOException e) {
            System.out.println("Wrong input");
            run();
        }
    }

    private static void runNavigation() {
        System.out.println();
        System.out.println("if you want create person, please enter 1");
        System.out.println("if you want update person, please enter 2");
        System.out.println("if you want delete person, please enter 3");
        System.out.println("if you want findByPassportId person, please enter 4");
        System.out.println("if you want findAll persons, please enter 5");
        System.out.println("if you want numberOfAll persons, please enter 6");
        System.out.println("if you want exit from people, please enter 0");
        System.out.println();
    }

    private static void crud(String position, BufferedReader reader) {
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
                findById(reader);
                break;
            case "5":
                findAllPersons(reader);
                break;
            case "6":
                numOfAllPersons();
                break;
        }
        runNavigation();
    }

    private static void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter person's first name : ");
            String firstName = reader.readLine();
            System.out.print("Please, enter person's last name : ");
            String lastName = reader.readLine();
            System.out.print("Please, enter person's age (from 0 to 117) : ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            while ((age < 0) || (age > 117)) {
                System.out.print("Wrong age, please, enter person's age! (from 0 to 117) : ");
                ageString = reader.readLine();
                age = Integer.parseInt(ageString);
            }
            System.out.print("Please, enter person's sex (M or F) : ");
            String sex = reader.readLine();
            while ((!sex.equals("M")) && (!sex.equals("F"))) {
                System.out.print("Wrong sex, please, enter person's sex! (M or F) : ");
                sex = reader.readLine();
            }
            System.out.print("Please, enter person's country of residence : ");
            String countryOfResidence = reader.readLine();
            Population person = new Population();
            person.setAge(age);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setSex(sex);
            person.setCountryOfResidence(countryOfResidence);
            populationService.create(person);
        } catch (IOException e) {
            System.out.println("Wrong input");
            create(reader);
        }
    }

    private static void update(BufferedReader reader) {
        try {
            System.out.print("Please, enter passport id : ");
            String id = reader.readLine();
            System.out.print("Please, enter person's new first name : ");
            String firstName = reader.readLine();
            System.out.print("Please, enter person's new last name : ");
            String lastName = reader.readLine();
            System.out.print("Please, enter person's new age (from 0 to 117) : ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            while ((age < 0) || (age > 117)) {
                System.out.print("Wrong age, please, enter person's age! (from 0 to 117) : ");
                ageString = reader.readLine();
                age = Integer.parseInt(ageString);
            }
            System.out.print("Please, enter person's new country of residence : ");
            String CountryOfResidence = reader.readLine();
            Population person = new Population();
            person.setPassportID(id);
            person.setAge(age);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setCountryOfResidence(CountryOfResidence);
            populationService.update(person);
        } catch (IOException e) {
            System.out.println("Wrong input");
            update(reader);
        }
    }

    private static void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter passport id : ");
            String id = reader.readLine();
            populationService.delete(id);
            System.out.println("User was deleted");
        } catch (IOException e) {
            System.out.println("Wrong input");
            delete(reader);
        }
    }

    private static void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter passport id : ");
            String id = reader.readLine();
            Population person = populationService.findByPassportId(id);
            System.out.println(person.toString());
        } catch (IOException e) {
            System.out.println("Wrong input");
            findById(reader);
        }
    }

    private static void findAllPersons(BufferedReader reader) {
        List<Population> people = populationService.findAllPersons();
        if (people != null && people.size() != 0) {
            for (Population person : people) {
                System.out.println(person.toString());
            }
        } else {
            System.out.println("THERE ARE NO PEOPLE");
        }
    }

    public static void numOfAllPersons() {
        System.out.println("Number Of All Persons = " + populationService.numOfAllPersons());
    }
}
