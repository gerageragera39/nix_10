package ua.com.alevel;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeMain {

    public static final Operations operations = new Operations();

    public static void main(String[] args) {
        System.out.println("16 Ноябрь 2021");
        syntaxExample();
        int[] startData = runStartData();
        String position = "-1";
        Scanner scanner = new Scanner(System.in);
        while (position != null){
            switch (position){
                case "1":
                    operations.add(startData);
                case "5":
                    printData(parseString(startData));
                case "0":
                    System.exit(0);
            }
            System.out.print("Enter your option : ");
            position = scanner.nextLine();
        }
    }

    public static int[] runStartData(){
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
            runStartData();
        }
        return intData;
    }

    public static void syntaxExample(){
        System.out.println("12/5/47 00:24:00:000 -- 1");
        System.out.println("12-5-47 00:24:00:000 -- 1");
        System.out.println("Март 4 21 -- 2");
        System.out.println("09 Апрель 789 15:23 -- 3");
    }

    public static void printData(String[] data){
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
    }

    public static String[] parseString(int[] toParseData) {
        String[] data = new String[7];
        for (int i = 0; i < data.length; i++) {
            data[i] = String.valueOf(toParseData[i]);
        }
        int[] twoCharIndices = {0, 1, 3, 4, 5};
        for (int k : twoCharIndices) {
            if (data[k].length() == 1) {
                data[k] = "0" + data[k];
            }
        }
        switch (data[2].length()) {
            case 1:
                data[2] = "000" + data[2];
                break;
            case 2:
                data[2] = "00" + data[2];
                break;
            case 3:
                data[2] = "0" + data[2];
                break;
        }

        switch (data[6].length()) {
            case 1:
                data[6] = "00" + data[6];
                break;
            case 2:
                data[6] = "0" + data[6];
                break;
        }
        return data;
    }
}
