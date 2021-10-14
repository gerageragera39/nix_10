package ua.com.alevel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String position = "1";
        String taskNum;
        while (position != null) {
            switch (position) {
                case "0": {
                    System.exit(0);
                }
                case "1": {
                    System.out.print("Enter the number of task : ");
                    taskNum = scanner.nextLine();
                    logic(scanner, taskNum);
                }
            }

            System.out.println("\nIf you want to continue in THIS PROJECT enter '1', if not then '0'");
            position = scanner.nextLine();
        }
    }

    private static void logic(Scanner scanner, String taskNum) {
        switch (taskNum) {
            case "1":
                task1();
                index(scanner, taskNum);
                break;
            case "2":
                task2();
                index(scanner, taskNum);
                break;
            case "3":
                task3();
                index(scanner, taskNum);
                break;
        }
    }

    private static void task1() {
       SumOfDigits sum = new SumOfDigits();
       sum.searchSum();
    }

    private static void task2() {
        NumberOfLetterRepetitions number = new NumberOfLetterRepetitions();
        number.quantitySearch();
    }

    private static void task3() {
        EndOfLesson time = new EndOfLesson();
        time.endTime();
    }

    public static void index(Scanner scanner, String taskNum) {
        System.out.println("\nIf you want to continue in THIS TASK enter '1', if not then '0'");
        String position = scanner.nextLine();
        switch (position) {
            case "0": {
                break;
            }
            case "1": {
                logic(scanner, taskNum);
            }
        }
    }
}