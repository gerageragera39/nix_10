package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.controller.PopulationController;
import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;

import java.util.Arrays;
import java.util.UUID;

public class DBPopulation {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    private static Population[] people;
    private static DBPopulation instance;

    public DBPopulation() {
        people = new Population[0];
    }

    public static DBPopulation getInstance() {
        if (instance == null) {
            instance = new DBPopulation();
        }
        return instance;
    }

    public void create(Population person) {
        person.setPassportID(generatePassportId());
        people = Arrays.copyOf(people, people.length + 1);
        people[people.length - 1] = person;
    }

    public void update(Population updatePerson) {
        Population current = findByPassportId(updatePerson.getPassportID());
        current.setFirstName(updatePerson.getFirstName());
        current.setLastName(updatePerson.getLastName());
        current.setAge(updatePerson.getAge());
        current.setCountryOfResidence(updatePerson.getCountryOfResidence());
    }

    public void delete(String id) {
        Population personToDelete = findByPassportId(id);
        int index = -1;
        for (int i = 0; i < people.length; i++) {
            if (people[i].getPassportID().equals(String.valueOf(personToDelete.getPassportID()))) {
                people[i] = null;
                index = i;
            }
        }
        Population arrayWithDeletedPerson[] = new Population[people.length - 1];
        for (int i = 0; i < index; i++) {
            arrayWithDeletedPerson[i] = people[i];
        }
        for (int i = index; i < arrayWithDeletedPerson.length; i++) {
            arrayWithDeletedPerson[i] = people[i + 1];
        }
        people = Arrays.copyOf(arrayWithDeletedPerson, people.length - 1);
    }

    public Population findByPassportId(String id) {
        for (int j = 0; j < people.length; j++) {
            if (id.equals(String.valueOf(people[j].getPassportID()))) {
                return people[j];
            }
        }

        System.out.println();
        System.out.println("PERSON NOT FOUND");
        LOGGER_WARN.warn("person not found by passport id : " + id);
        LOGGER_INFO.info("person not found by passport id : " + id);
        PopulationController controller = new PopulationController();
        controller.run();
        return null;
    }

    public Population[] findAllPersons() {
        return people;
    }

    public String generatePassportId() {
        String id = UUID.randomUUID().toString();
        for (int i = 0; i < people.length; i++) {
            if (id.equals(String.valueOf(people[i].getPassportID()))) {
                generatePassportId();
            }
        }
        return id;
    }

    public int numOfAllPersons() {
        return people.length;
    }

    public boolean existByCountry(String nameOfCountry) {
        Countries[] countries = DBCountries.getInstance().getCountries();
        for (int i = 0; i < countries.length; i++) {
            if (nameOfCountry.equals(String.valueOf(countries[i].getNameOfCountry()))) {
                return true;
            }
        }
        return false;
    }

    public static Population[] getPeople() {
        return people;
    }
}
