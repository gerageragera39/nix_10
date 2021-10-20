package ua.com.alevel;

import java.util.Scanner;

public class StringsMain {

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
        System.out.print("Enter the text you want to reverse : ");
        String text = scan().nextLine();
        String[] words = text.split(" ");
        System.out.print("Enter word number (from 1 to " + words.length + "): ");
        int numOfWord = scan().nextInt();
        System.out.println(StringHelperUtil.reverseAll(text, numOfWord));
    }

    private static void task2() {
        System.out.print("Enter the text you want to reverse : ");
        String text = scan().nextLine();
        System.out.print("Enter part of the text : ");
        String part = scan().nextLine();
        System.out.println(StringHelperUtil.reversePartOfSentence(text, part));
    }

    private static void task3() {
        System.out.print("Enter the text you want to reverse : ");
        String text = scan().nextLine();
        int endOfFirstIndex = text.length()-2;
        int endOfText = text.length()-1;
        System.out.print("Enter the number of the first letter with which to start reversing (form 0 to " + endOfFirstIndex + ") : ");
        int firstIndex = scan().nextInt();
        int nextIndex = firstIndex + 1;
        System.out.print("Enter the number of the first letter with which to end the reversal (from " + nextIndex + " to " + endOfText + ") : ");
        int lastIndex = scan().nextInt();
        System.out.println(StringHelperUtil.reveverseByInterval(text,firstIndex,lastIndex));
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

    public static Scanner scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }
}

