package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.ReaderCSV;
import ua.com.alevel.WriterCSV;
import ua.com.alevel.controller.PopulationController;
import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DBPopulation {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

//    public static final String path = "C:\\Users\\admin\\IdeaProjects\\nix_10\\hw_7_ionio\\population.csv";
    public static String path;
//    public static WriterCSV writer = new WriterCSV(path);

//    private static Population[] people;
    private static DBPopulation instance;

//    public DBPopulation() {
//        people = new Population[0];
//    }

    public static DBPopulation getInstance() {
        if (instance == null) {
            instance = new DBPopulation();
        }
        return instance;
    }

    public void create(Population person) {
        person.setPassportID(generatePassportId());
//        people = Arrays.copyOf(people, people.length + 1);
//        people[people.length - 1] = person;
        populationWriter(path,person);
    }

    public void update(Population updatePerson) {
        Population current = findByPassportId(updatePerson.getPassportID());
        current.setFirstName(updatePerson.getFirstName());
        current.setLastName(updatePerson.getLastName());
        current.setAge(updatePerson.getAge());
        current.setCountryOfResidence(updatePerson.getCountryOfResidence());
        List<Population> populationList = readPopulationCSV();
        WriterCSV writerCSV = new WriterCSV(path);
        writerCSV.clearCSV(Population.class);
        for (int i = 0; i < populationList.size(); i++) {
            if(!String.valueOf(populationList.get(i).getPassportID()).equals(current.getPassportID())){
                writerCSV.writeCVS(getFiends(populationList.get(i)));
            }else{
                writerCSV.writeCVS(getFiends(current));
            }
        }
    }

    public void delete(String id) {
        WriterCSV writerCSV = new WriterCSV(path);
        findByPassportId(id);
        List<Population> populationList = readPopulationCSV();
        writerCSV.clearCSV(Population.class);
        for (int i = 0; i < populationList.size(); i++) {
            if(!String.valueOf(populationList.get(i).getPassportID()).equals(id)){
                writerCSV.writeCVS(getFiends(populationList.get(i)));
            }
        }
    }

    public Population findByPassportId(String id) {
        List<Population> peopleList = readPopulationCSV();
        for (int i = 0; i < peopleList.size(); i++) {
            if (id.equals(String.valueOf(peopleList.get(i).getPassportID()))) {
                return peopleList.get(i);
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

    public List<Population> findAllPersons() {
        return readPopulationCSV();
    }

    public String generatePassportId() {
        String id = UUID.randomUUID().toString();
        List<Population> persons = findAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            if (id.equals(String.valueOf(persons.get(i).getPassportID()))) {
                generatePassportId();
            }
        }
        return id;
    }

    public int numOfAllPersons() {
        return readPopulationCSV().size();
    }

    public boolean existByCountry(String nameOfCountry) {
        List<Countries> countries = DBCountries.getInstance().findAllCounties(path);
        for (int i = 0; i < countries.size(); i++) {
            if (nameOfCountry.equals(String.valueOf(countries.get(i).getNameOfCountry()))) {
                return true;
            }
        }
        return false;
    }

//    public static Population[] getPeople() {
//        return people;
//    }

    public void populationWriter(String path, Population person){
        List<String> list = getFiends(person);
        WriterCSV writerCSV1 = new WriterCSV(path);
        writerCSV1.writeCVS(list);
    }

    public List<Population> readPopulationCSV(){
        String[][] personFields = null;
        ReaderCSV readerCSV = new ReaderCSV(path);
        List<String> list = readerCSV.readCSV();
        Field[] fields = Population.class.getDeclaredFields();
        personFields = new String[list.size()][fields.length];
        int fieldIndex = 0;
        int indexOfQuotes = 1;
        boolean firstStep = true;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.get(i).length(); j++) {
                if(list.get(i).charAt(j) == '"'){
                    for (int k = indexOfQuotes; k < j; k++) {
                        if(firstStep){
                            personFields[i][fieldIndex] = String.valueOf(list.get(i).charAt(k));
                            firstStep = false;
                            indexOfQuotes++;
                        }else{
                            personFields[i][fieldIndex] += String.valueOf(list.get(i).charAt(k));
                            indexOfQuotes++;
                        }
                    }
                    fieldIndex++;
                    firstStep = true;
                    j+=3;
                    indexOfQuotes+=3;
                }
            }
            fieldIndex = 0;
            indexOfQuotes = 1;
        }

        List<Population> peopleList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            Population person = new Population();
            person.setPassportID(personFields[i][0]);
            person.setFirstName(personFields[i][1]);
            person.setLastName(personFields[i][2]);
            person.setCountryOfResidence(personFields[i][3]);
            person.setAge(Integer.parseInt(personFields[i][4]));
            person.setSex(personFields[i][5]);
            peopleList.add(person);
        }
        return peopleList;
    }

    public List<String> getFiends(Population person){
        List<String> list = new ArrayList<>();
        list.add(person.getPassportID());
        list.add(person.getFirstName());
        list.add(person.getLastName());
        list.add(person.getCountryOfResidence());
        list.add(String.valueOf(person.getAge()));
        list.add(person.getSex());
        return list;
    }

    public static void setPath(String pth) {
        DBPopulation.path = pth;
    }
}
