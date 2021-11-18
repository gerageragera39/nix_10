package ua.com.alevel;

import ua.com.alevel.entity.Population;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IONIOMain {

    public static void main(String[] args) {
        String path = "C:\\Users\\admin\\IdeaProjects\\nix_10\\hw_7_ionio\\population.csv";
        parsePopulationCSV(path);
        Population person = new Population();
        person.setFirstName("german");
        person.setLastName("dig");
        person.setAge(18);
        person.setCountryOfResidence("Uk");
        person.setPassportID("9");
        person.setSex("M");
        populationWriter(path, person);
    }

    public static String[][] parsePopulationCSV(String path){
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

        for (int i = 0; i < peopleList.size(); i++) {
            System.out.println(peopleList.get(i).toString());
        }

        return personFields;
    }

    public static String[][] parseCountryCSV(String path){
        String[][] countryFields = null;
        ReaderCSV readerCSV = new ReaderCSV(path);
        List<String> list = readerCSV.readCSV();
        list.forEach(System.out::println);
        Field[] fields = Population.class.getDeclaredFields();
        List<String> strFields = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            strFields.add(fields[i].getName());
            System.out.println(strFields.get(i));
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

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < fields.length; j++) {
                System.out.print(countryFields[i][j] + ",");
            }
            System.out.println();
        }
        return countryFields;
    }

    public static void populationWriter(String path, Population person){
        List<String> list = new ArrayList<>();
        list.add(person.getPassportID());
        list.add(person.getFirstName());
        list.add(person.getLastName());
        list.add(person.getCountryOfResidence());
        list.add(String.valueOf(person.getAge()));
        list.add(person.getSex());
        WriterCSV writer = new WriterCSV(path);
        writer.writeCVS(list);
    }
}
