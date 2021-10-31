package ua.com.alevel.db;

import ua.com.alevel.entity.Population;

public class DBPopulation {

    private Population[] people;
    private static DBPopulation instance;

    private DBPopulation() {
        people = new Population[0];
    }

    public static DBPopulation getInstance() {
        if (instance == null) {
            instance = new DBPopulation();
        }
        return instance;
    }
}
