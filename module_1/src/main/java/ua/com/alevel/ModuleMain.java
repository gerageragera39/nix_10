package ua.com.alevel;

import ua.com.alevel.level3.Level3Main;
import ua.com.alevel.level_1.Level1Main;
import ua.com.alevel.level_2.Level2Main;

import java.util.Scanner;

public class ModuleMain {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String position = "1";
        String levelNum;
        while (position != null) {
            switch (position) {
                case "0": {
                    System.exit(0);
                }
                case "1": {
                    System.out.println("Enter the number of level : ");
                    levelNum = scanner.nextLine();
                    logic(levelNum);
                    break;
                }
            }
            System.out.println("\nIf you want to continue in THIS PROJECT enter '1', if not then '0'");
            position = scanner.nextLine();
        }
    }

    private static void logic(String levelNum) throws Exception {
        String taskNum;
        switch (levelNum) {
            case "1":
                System.out.println("Enter task number from 1 to 3 : ");
                taskNum = scan.nextLine();
                level1(taskNum);
                LevelIndex(levelNum);
                break;

            case "2":
                System.out.println("Enter task number form 1 to 2 : ");
                taskNum = scan.nextLine();
                level2(taskNum);
                LevelIndex(levelNum);
                break;

            case "3":
                level3();
                LevelIndex("3");
                break;
        }
    }

    public static void LevelIndex(String levelNum) throws Exception {
        System.out.println("\nIf you want to continue in THIS LEVEL enter '1', if not then '0'");
        String position = scan.nextLine();
        switch (position) {
            case "0": {
                break;
            }
            case "1": {
                logic(levelNum);
                break;
            }
        }
    }

    public static void TaskIndex(String taskNum, String levelNum) throws Exception {
        System.out.println("\nIf you want to continue in THIS TASK enter '1', if not then '0'");
        String position = scan.nextLine();
        switch (position) {
            case "0": {
                break;
            }
            case "1": {
                switch (levelNum) {
                    case "1":
                        level1(taskNum);
                        break;
                    case "2":
                        level2(taskNum);
                        break;
                    case "3":
                        level3();
                        break;
                }
                break;
            }
        }
    }

    private static void level1(String taskNum) throws Exception {
        Level1Main l1 = new Level1Main();
        switch (taskNum) {
            case "1":
                l1.runtask1();
                TaskIndex(taskNum, "1");
                break;
            case "2":
                l1.runtask2();
                TaskIndex(taskNum, "1");
                break;
            case "3":
                l1.runtask3();
                TaskIndex(taskNum, "1");
                break;
        }
    }

    private static void level2(String taskNum) throws Exception {
        Level2Main l2 = new Level2Main();
        switch (taskNum) {
            case "1":
                l2.runTask1();
                TaskIndex(taskNum, "2");
                break;

            case "2":
                l2.runTask2();
                TaskIndex(taskNum, "2");
                break;
        }
    }

    private static void level3() throws Exception {
        Level3Main l3 = new Level3Main();
        l3.runLevel3();
    }
}