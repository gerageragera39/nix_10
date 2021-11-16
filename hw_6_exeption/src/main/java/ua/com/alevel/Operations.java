package ua.com.alevel;

import java.util.Scanner;

public class Operations {

    Transformer transformer = new Transformer();

    public int[] add(int[] startData){
        System.out.println("add years -> 1");
        System.out.println("add months -> 2");
        System.out.println("add days -> 3");
        System.out.println("add hours -> 4");
        System.out.println("add minutes -> 5");
        System.out.println("add seconds -> 6");
        System.out.println("add milliseconds -> 7");
        System.out.println("Select add option :");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        int time = 0;
        switch (option){
            case "1":
                System.out.println("Enter the number of years you want to add : ");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[2]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "2":
                System.out.println("Enter the number of months you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[1]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "3":
                System.out.println("Enter the number of days you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[0]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "4":
                System.out.println("Enter the number of hours you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[3]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "5":
                System.out.println("Enter the number of minutes you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[4]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "6":
                System.out.println("Enter the number of seconds you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[5]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "7":
                System.out.println("Enter the number of milliseconds you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[6]+=time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            default:
                System.out.println("Wrong index");
                add(startData);
        }
        return new int[0];
    }

    public String[] parseString(int[] data){
        String[] stringData = new String[7];
        for (int i = 0; i < data.length; i++) {
            stringData[i] = String.valueOf(data[i]);
        }
        return stringData;
    }

    public int[] refactor(int[] data) {
        int[] startData = data;
        int factor;
        if (data[6] > 999) {
            factor = data[6] / 1000;
            data[6] -= factor * 1000;
            data[5] += factor;
        }

        if (data[5] > 59) {
            factor = data[5] / 60;
            data[5] -= factor * 60;
            data[4] += factor;
        }

        if (data[4] > 59) {
            factor = data[4] / 60;
            data[4] -= factor * 60;
            data[3] += factor;
        }

        if (data[3] > 23) {
            factor = data[3] / 24;
            data[3] -= factor * 24;
            data[0] += factor;
        }

        while (data[0] > numOfDays(data[1], data[2])) {
            data[0] -= numOfDays(data[1], data[2]);
            data[1]++;
            if (data[1] > 12) {
                data[1] -= 12;
                data[2]++;
            }
            if (data[2] > 9999) {
                System.out.println("total years > 9999");
                System.out.println("Year will be zero");
                data[2] = 0;
                return startData;
            }
        }

        if(data[1] > 12){
            factor = data[1] / 12;
            data[1] -= factor * 12;
            data[2] += factor;
        }

        if(data[2] > 9999){
            System.out.println("total years > 9999");
            System.out.println("Year will be zero");
            data[2] = 0;
            return startData;
        }
        return data;
    }

    public int numOfDays(int numOfMonth, int year){
        int[] days31 = {1,3,5,7,8,10,12};
        int[] days30 = {4,6,9,11};
        int daysOfFebruary;
        if(((year % 4 != 0) || (year % 100 == 0)) && (year % 400 != 0)){
            daysOfFebruary = 28;
        }else{
            daysOfFebruary = 29;
        }
        for (int day:days31) {
            if(numOfMonth == day){
                return 31;
            }
        }
        for (int day:days30) {
            if(numOfMonth == day){
                return 30;
            }
        }

        return daysOfFebruary;
    }
}
