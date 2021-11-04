package ua.com.alevel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RelationController {

    public static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position = "0";
        runNavigation();
        try {
            while (position != null) {
                System.out.print("select your option : ");
                position = reader.readLine();
                if (!position.equals("0")) {
                    crud(position, reader);
                }
                if (position.equals("0")) {
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void runNavigation() {
        System.out.println();
        System.out.println("if you want to work with countries, please enter 1");
        System.out.println("if you want to work with people, please enter 2");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    private static void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                CountriesController();
                break;
            case "2":
                PopulationController();
                break;
        }
        runNavigation();
    }

    private static void CountriesController() {
        CountriesController.run();
    }

    private static void PopulationController() {
        PopulationController.run();
    }
}
