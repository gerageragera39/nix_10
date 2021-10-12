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
                    logic(taskNum);
                }
            }
            System.out.println("If you want to continue working, enter '1', if not, then '0'");
            position = scanner.nextLine();
        }

    }

    private static void logic(String taskNum) {
        switch (taskNum) {
            case "1":
                task1();
                break;
            case "2":
                task2();
                break;
            case "3":
                task3();
                break;
        }
    }

    private static void task1() {
        Task1 t1 = new Task1();
        t1.srchSum();
    }

    private static void task2() {
        Task2 task2 = new Task2();
        task2.quantSrch();
    }

    private static void task3() {
        Task3 task3 = new Task3();
        task3.endTime();
    }
}

//        Task1 t1 = new Task1();
//        t1.srchSum();
//        System.out.println("\n");
//        Task2 t2 = new Task2();
//        t2.quantSrch();
//        System.out.println("\n");
//        Task3 t3 = new Task3();
//        t3.endTime();





