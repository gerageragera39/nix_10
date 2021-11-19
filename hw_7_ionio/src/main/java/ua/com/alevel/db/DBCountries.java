package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.ReaderCSV;
import ua.com.alevel.WriterCSV;
import ua.com.alevel.controller.CountriesController;
import ua.com.alevel.entity.Countries;
import ua.com.alevel.entity.Population;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBCountries {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    public static final String path = "C:\\Users\\admin\\IdeaProjects\\nix_10\\hw_7_ionio\\countries.csv";
    public static final WriterCSV writer = new WriterCSV(path);

//    private Countries[] countries;
    private static DBCountries instance;

//    public DBCountries() {
//        countries = new Countries[0];
//    }

    public static DBCountries getInstance() {
        if (instance == null) {
            instance = new DBCountries();
        }
        return instance;
    }

    public void create(Countries newCountry) {
        newCountry.setISO(generateISO());
//        countries = Arrays.copyOf(countries, countries.length + 1);
//        countries[countries.length - 1] = newCountry;
        countriesWriter(path, newCountry);
    }

    public void update(Countries updateCountry, String previousName) {
//        Countries current = findByISO(updateCountry.getISO());
//        for (int i = 0; i < DBPopulation.getInstance().getPeople().length; i++) {
//            if (DBPopulation.getInstance().getPeople()[i].getCountryOfResidence().equals(previousName)) {
//                DBPopulation.getInstance().getPeople()[i].setCountryOfResidence(updateCountry.getNameOfCountry());
//            }
//        }
//        current.setNameOfCountry(updateCountry.getNameOfCountry());

        Countries current = findByISO(updateCountry.getISO()) ;
        current.setNameOfCountry(updateCountry.getNameOfCountry());
        List<Countries> countriesList = readCountryCSV(path);
        writer.clearCSV(Countries.class);
        for (int i = 0; i < countriesList.size(); i++) {
            if(countriesList.get(i).getISO() != current.getISO()){
                writer.writeCVS(getFiends(countriesList.get(i)));
            }else{
                writer.writeCVS(getFiends(current));
            }
        }
    }

    public void delete(int ISO) {
        findByISO(ISO);
        List<Countries> countriesList = readCountryCSV(path);
        writer.clearCSV(Countries.class);
        for (int i = 0; i < countriesList.size(); i++) {
            if(countriesList.get(i).getISO() != ISO){
                writer.writeCVS(getFiends(countriesList.get(i)));
            }
        }
    }

    public Countries findByISO(int ISO) {
        List<Countries> countriesList = readCountryCSV(path);
        for (int i = 0; i < countriesList.size(); i++) {
            if (ISO == countriesList.get(i).getISO()) {
                return countriesList.get(i);
            }
        }
        System.out.println();
        System.out.println("COUNTRY NOT FOUND");
        LOGGER_WARN.warn("county not found by ISO : " + ISO);
        LOGGER_INFO.info("county not found by ISO : " + ISO);
        CountriesController controller = new CountriesController();
        controller.run();
        return null;
    }

    public List<Countries> findAllCounties() {
        return readCountryCSV(path);
    }

    private int generateISO() {
        int ISO = (int) (Math.random() * 999) + 1;
        List<Countries> countries = findAllCounties();
        for (int i = 0; i < countries.size(); i++) {
            if (ISO == countries.get(i).getISO()) {
                generateISO();
            }
        }
        return ISO;
    }

    public int numOfAllCountries() {
        return readCountryCSV(path).size();
    }

    public boolean existByCountryName(String name) {
        List<Countries> countries = findAllCounties();
        for (int i = 0; i < countries.size(); i++) {
            if (name.equals(countries.get(i).getNameOfCountry())) {
                return false;
            }
        }
        return true;
    }

    public static void countriesWriter(String path, Countries country) {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(country.getISO()));
        list.add(country.getNameOfCountry());
        WriterCSV writer = new WriterCSV(path);
        writer.writeCVS(list);
    }

    public static List<Countries> readCountryCSV(String path){
        String[][] countryFields = null;
        ReaderCSV readerCSV = new ReaderCSV(path);
        List<String> list = readerCSV.readCSV();
        Field[] fields = Countries.class.getDeclaredFields();
        List<String> strFields = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            strFields.add(fields[i].getName());
        }
        countryFields = new String[list.size()][fields.length];
        int fieldIndex = 0;
        int indexOfQuotes = 1;
        boolean firstStep = true;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.get(i).length(); j++) {
                if(list.get(i).charAt(j) == '"'){
                    for (int k = indexOfQuotes; k < j; k++) {
                        if(firstStep){
                            countryFields[i][fieldIndex] = String.valueOf(list.get(i).charAt(k));
                            firstStep = false;
                            indexOfQuotes++;
                        }else{
                            countryFields[i][fieldIndex] += String.valueOf(list.get(i).charAt(k));
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

        List<Countries> countriesList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            Countries country = new Countries();
            country.setISO(Integer.parseInt(countryFields[i][0]));
            country.setNameOfCountry(countryFields[i][1]);
            countriesList.add(country);
        }
        return countriesList;
    }

    public List<String> getFiends(Countries country){
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(country.getISO()));
        list.add(country.getNameOfCountry());
        return list;
    }
}

