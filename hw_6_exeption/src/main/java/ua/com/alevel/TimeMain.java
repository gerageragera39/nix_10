package ua.com.alevel;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TimeMain {

    public static final Operations operations = new Operations();

    public static void main(String[] args) {
        int[] startData = runStartData();
        String position = "-1";
        Scanner scanner = new Scanner(System.in);
        while (position != null) {
            switch (position) {
                case "1":
                    System.out.println("Enter the date you want to compare with");
                    int[] differenceData = runStartData();
                    operations.difference(startData, differenceData);
                    break;
                case "2":
                    operations.add(startData);
                    break;
                case "3":
                    operations.minus(startData);
                    break;
                case "4":
                    Scanner scan = new Scanner(System.in);
                    ArrayList<int[]> dataList = new ArrayList<>();
                    dataList.add(startData);
                    String addOne = "1";
                    while (!addOne.equals("0")) {
                        dataList.add(runStartData());
                        System.out.println();
                        System.out.println("if you want to add another date insert any number other than 0 : ");
                        addOne = scan.nextLine();
                    }
                    dataList = operations.increaseDecrease(dataList);
                    int[][] dataArray = new int[dataList.size()][7];
                    for (int i = 0; i < dataList.size(); i++) {
                        dataArray[i] = dataList.get(i);
                    }
                    for (int i = 0; i < dataList.size(); i++) {
                        System.out.print("{");
                        printData(parseString(dataArray[i]), true);
                        System.out.print("}");
                        if (i != dataList.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                    break;
                case "5":
                    printData(parseString(startData), false);
                    System.out.println();
                    break;
                case "0":
                    System.exit(0);
            }
            System.out.println();
            choice();
            System.out.println();
            System.out.print("Enter your option : ");
            position = scanner.nextLine();
        }
    }

    public static int[] runStartData() {
        syntaxExample();
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
        } catch (InputMismatchException e) {
            System.out.println();
            System.out.println("wrong input");
            runStartData();
        }
        return intData;
    }

    public static void choice() {
        System.out.println("Difference -> 1");
        System.out.println("Add -> 2");
        System.out.println("Minus -> 3");
        System.out.println("Sort -> 4");
        System.out.println("Print data -> 5");
        System.out.println("To exit -> 0");
    }

    public static void syntaxExample() {
        System.out.println("?????????????? :");
        System.out.println("12/5/47 00:24 -> 1");
        System.out.println("12-5-47 00:24:00:001 -> 1");
        System.out.println("???????? 4 21 ::2 -> 2");
        System.out.println("???????????? 29 1932 -> 2");
        System.out.println("9 ???????????? 789 15:23 -> 3");
        System.out.println("16 ???????????? 2021 -> 3");
        System.out.println();
    }

    public static void printData(String[] data, boolean isList) {
        String method = "4";
        if (!isList) {
            System.out.println("Choose print method");
            System.out.println("dd/mm/yy 00:00:00:000 -> 1");
            System.out.println("dd-mm-yy 00:00:00:000 -> 2");
            System.out.println("mmm-d-yy 00:00:00:000 -> 3");
            System.out.println("dd-mmm-yyyy 00:00:00:000 -> 4");
            System.out.print("Enter your choice : ");
            Scanner scanner = new Scanner(System.in);
            method = scanner.nextLine();
            System.out.println();
        }
        switch (method) {
            case "1":
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        System.out.print(data[i] + "/");
                    } else {
                        System.out.print(data[i]);
                    }
                }
                break;
            case "2":
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        System.out.print(data[i] + "-");
                    } else {
                        System.out.print(data[i]);
                    }
                }
                break;
            case "3":
                System.out.print(switchMonth(data[1]) + "-");
                System.out.print(data[0] + "-");
                System.out.print(data[2]);
                break;
            case "4":
                System.out.print(data[0] + "-");
                System.out.print(switchMonth(data[1]) + "-");
                System.out.print(data[2]);
                break;
        }
        System.out.print(" ");
        for (int i = 3; i < 7; i++) {
            if (i != 6) {
                System.out.print(data[i] + ":");
            } else {
                System.out.print(data[i]);
            }
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

    public static String switchMonth(String month) {
        switch (month) {
            case "01":
                return "????????????";
            case "02":
                return "??????????????";
            case "03":
                return "????????";
            case "04":
                return "????????????";
            case "05":
                return "??????";
            case "06":
                return "????????";
            case "07":
                return "????????";
            case "08":
                return "????????????";
            case "09":
                return "????????????????";
            case "10":
                return "??????????????";
            case "11":
                return "????????????";
            case "12":
                return "??????????????";
        }
        return null;
    }
}
