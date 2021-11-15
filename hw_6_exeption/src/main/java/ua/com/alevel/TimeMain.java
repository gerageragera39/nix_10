package ua.com.alevel;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeMain {

    public static void main(String[] args) {
        int[] startData;
        String position = "-1";
        Scanner scanner = new Scanner(System.in);
        while (position != null){
            syntaxExample();
            switch (position){
                case "-1":
                    startData = run();
                    for (int i = 0; i < startData.length; i++) {
                        System.out.print(startData[i] + " ");
                    }
                case "0":
                    System.exit(0);
            }
            System.out.print("Enter your option : ");
            position = scanner.nextLine();
        }
    }

    public static int[] run(){
        int[] intData = new int[0];
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        try {
            System.out.print("Enter index : ");
            int index = scanner1.nextInt();
            System.out.print("Enter data : ");
            String time = scanner2.nextLine();
            Transformer trans = new Transformer();
            return trans.transformTime(time, index);
        }catch (InputMismatchException e){
            run();
        }
        return intData;
    }

    public static void syntaxExample(){
        System.out.println("/5/47 00:24:00:000 -- 1");
        System.out.println("Март 4 21 -- 2");
        System.out.println("09 Апрель 789 45:23 -- 3");
    }
}
