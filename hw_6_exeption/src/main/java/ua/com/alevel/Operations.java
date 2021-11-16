package ua.com.alevel;

import java.util.ArrayList;
import java.util.Scanner;

public class Operations {

    public int[] add(int[] startData) {
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
        switch (option) {
            case "1":
                System.out.println("Enter the number of years you want to add : ");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[2] += time;
                startData = refactor(startData);
                break;
            case "2":
                System.out.println("Enter the number of months you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[1] += time;
                startData = refactor(startData);
                break;
            case "3":
                System.out.println("Enter the number of days you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[0] += time;
                startData = refactor(startData);
                break;
            case "4":
                System.out.println("Enter the number of hours you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[3] += time;
                startData = refactor(startData);
                break;
            case "5":
                System.out.println("Enter the number of minutes you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[4] += time;
                startData = refactor(startData);
                break;
            case "6":
                System.out.println("Enter the number of seconds you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[5] += time;
                startData = refactor(startData);
                for (int i = 0; i < startData.length; i++) {
                    System.out.print(startData[i] + " ");
                }
                break;
            case "7":
                System.out.println("Enter the number of milliseconds you want to add");
                System.out.println("Total years must be <= 9999 !");
                time = scanner2.nextInt();
                startData[6] += time;
                startData = refactor(startData);
                break;
            default:
                System.out.println("Wrong index");
                add(startData);
        }
        return new int[0];
    }

    public String[] parseString(int[] data) {
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

        if (data[1] > 12) {
            factor = data[1] / 12;
            data[1] -= factor * 12;
            data[2] += factor;
        }

        if (data[2] > 9999) {
            System.out.println("total years > 9999");
            System.out.println("Year will be zero");
            data[2] = 0;
            return startData;
        }
        return data;
    }

    public int numOfDays(int numOfMonth, int year) {
        int[] days31 = {1, 3, 5, 7, 8, 10, 12};
        int[] days30 = {4, 6, 9, 11};
        int daysOfFebruary;
        if (((year % 4 != 0) || (year % 100 == 0)) && (year % 400 != 0)) {
            daysOfFebruary = 28;
        } else {
            daysOfFebruary = 29;
        }
        for (int day : days31) {
            if (numOfMonth == day) {
                return 31;
            }
        }
        for (int day : days30) {
            if (numOfMonth == day) {
                return 30;
            }
        }
        return daysOfFebruary;
    }

    public void difference(int[] firstData, int[] secondData) {
        System.out.println("difference in years -> 1");
        System.out.println("difference in months -> 2");
        System.out.println("difference in days -> 3");
        System.out.println("difference in hours -> 4");
        System.out.println("difference in minutes -> 5");
        System.out.println("difference in seconds -> 6");
        System.out.println("difference in milliseconds -> 7");
        System.out.println("Select difference option :");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                int years = firstData[2] - secondData[2];
                System.out.println("years - " + Math.abs(years));
                break;
            case "2":
                long months = findMonths(firstData) - findMonths(secondData);
                System.out.println("months - " + Math.abs(months));
                break;
            case "3":
                long days = findDays(firstData) - findDays(secondData);
                System.out.println("days - " + Math.abs(days));
                break;
            case "4":
                long hours = findHours(firstData) - findHours(secondData);
                System.out.println("hours - " + Math.abs(hours));
                break;
            case "5":
                long minutes = findMinutes(firstData) - findMonths(secondData);
                System.out.println("minutes - " + Math.abs(minutes));
                break;
            case "6":
                long seconds = findSeconds(firstData) - findSeconds(secondData);
                System.out.println("seconds - " + Math.abs(seconds));
                break;
            case "7":
                long milliseconds = findMilliSeconds(firstData) - findMilliSeconds(secondData);
                System.out.println("milliseconds - " + Math.abs(milliseconds));
                break;
        }
    }

    public long findMonths(int[] data) {
        return data[2] * 12 + data[1] - 1;
    }

    public long findDays(int[] data) {
        int numberOfDays = 0;
        int numOfYear = 0;
        long finish = findMonths(data);
        for (int i = 0; i < finish; i++) {
            numberOfDays += numOfDays(i, numOfYear);
            if (i % 12 == 0) {
                numOfYear++;
            }
            if (i == 12) {
                i = 0;
                finish -= 12;
            }
        }
        return numberOfDays;
    }

    public long findHours(int[] data) {
        return findDays(data) * 24;
    }

    public long findMinutes(int[] data) {
        return findHours(data) * 60;
    }

    public long findSeconds(int[] data) {
        return findMinutes(data) * 60;
    }

    public long findMilliSeconds(int[] data) {
        return findSeconds(data) * 1000;
    }

    public int[] minus(int[] startData) {
        System.out.println("minus years -> 1");
        System.out.println("minus months -> 2");
        System.out.println("minus days -> 3");
        System.out.println("minus hours -> 4");
        System.out.println("minus minutes -> 5");
        System.out.println("minus seconds -> 6");
        System.out.println("minus milliseconds -> 7");
        System.out.println("Select minus option :");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        int time = 0;
        switch (option) {
            case "1":
                System.out.println("Enter the number of years you want to minus : ");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[2] -= time;
                startData = minusRefactor(startData);
                break;
            case "2":
                System.out.println("Enter the number of months you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[1] -= time;
                startData = minusRefactor(startData);
                break;
            case "3":
                System.out.println("Enter the number of days you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[0] -= time;
                startData = minusRefactor(startData);
                break;
            case "4":
                System.out.println("Enter the number of hours you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[3] -= time;
                startData = minusRefactor(startData);
                break;
            case "5":
                System.out.println("Enter the number of minutes you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[4] -= time;
                startData = minusRefactor(startData);
                break;
            case "6":
                System.out.println("Enter the number of seconds you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[5] -= time;
                startData = minusRefactor(startData);
                break;
            case "7":
                System.out.println("Enter the number of milliseconds you want to minus");
                System.out.println("Total years must be >= 0 !");
                time = scanner2.nextInt();
                startData[6] -= time;
                startData = minusRefactor(startData);
                break;
            default:
                System.out.println("Wrong index");
                add(startData);
        }
        return new int[0];
    }

    public int[] minusRefactor(int[] data) {
        int[] startData = data;
        int factor = 0;
        if (data[6] < 0) {
            factor = data[6] / 1000;
            factor++;
            data[6] += factor * 1000;
            data[5] -= factor;
        }

        if (data[5] < 0) {
            factor = data[5] / 60;
            factor++;
            data[5] += factor * 60;
            data[4] -= factor;
        }

        if (data[4] < 0) {
            factor = data[4] / 60;
            factor++;
            data[4] += factor * 60;
            data[3] -= factor;
        }

        if (data[3] < 0) {
            factor = data[3] / 24;
            factor++;
            data[3] += factor * 24;
            data[0] -= factor;
        }

        while (data[0] < 0) {
            data[0] += numOfDays(data[1], data[2]);
            data[1]--;
            if (data[1] < 0) {
                data[1] += 12;
                data[2]--;
            }
            if (data[2] < 0) {
                System.out.println("total years < 0");
                System.out.println("Year will be zero");
                data[2] = 0;
                return startData;
            }
        }

        if (data[1] < 0) {
            factor = data[1] / 12;
            factor++;
            data[1] += factor * 12;
            data[2] -= factor;
        }

        if (data[2] < 0) {
            System.out.println("total years > 9999");
            System.out.println("Year will be zero");
            data[2] = 0;
            return startData;
        }
        return data;
    }

    public ArrayList<int[]> increaseDecrease(ArrayList<int[]> dataList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Decrease sort -> 1");
        System.out.println("Increase sort -> 2");
        System.out.print("Enter your choice : ");
        System.out.println();
        String incDec = scanner.nextLine();
        ArrayList<Long> dataArray = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            dataArray.add(findMilliSeconds(dataList.get(i)));
        }
        ArrayList<Long> sortedList;
        int index = 0;
        switch (incDec) {
            case "1":
                sortedList = new ArrayList<>();
                long max = dataArray.get(0);

                while (dataArray.size() != 0) {
                    for (int i = 0; i < dataArray.size(); i++) {
                        if (max <= dataArray.get(i)) {
                            max = dataArray.get(i);
                            index = i;
                        }
                    }
                    sortedList.add(max);
                    dataArray.remove(index);
                    if (dataArray.size() != 0) {
                        max = getMin(dataArray);
                    }
                }
                dataArray = sortedList;

                ArrayList<int[]> dataDec = new ArrayList<>();
                for (int i = 0; i < dataArray.size(); i++) {
                    for (int j = 0; j < dataList.size(); j++) {
                        if (dataArray.get(i) == findMilliSeconds(dataList.get(j))) {
                            dataDec.add(dataList.get(j));
                            break;
                        }
                    }
                }
                return dataDec;
            case "2":
                sortedList = new ArrayList<>();
                long min = getMax(dataArray);
                while (dataArray.size() != 0) {
                    for (int i = 0; i < dataArray.size(); i++) {
                        if (min >= dataArray.get(i)) {
                            min = dataArray.get(i);
                            index = i;
                        }
                    }
                    sortedList.add(min);
                    dataArray.remove(index);
                    if (dataArray.size() != 0) {
                        min = getMax(dataArray);
                    }
                }
                dataArray = sortedList;
                ArrayList<int[]> dataInc = new ArrayList<>();
                for (int i = 0; i < dataArray.size(); i++) {
                    for (int j = 0; j < dataList.size(); j++) {
                        if (dataArray.get(i) == findMilliSeconds(dataList.get(j))) {
                            dataInc.add(dataList.get(j));
                            break;
                        }
                    }
                }
                return dataInc;
            default:
                System.out.println("Wrong enter");
                increaseDecrease(dataList);
        }
        return null;
    }

    public long getMin(ArrayList<Long> dataArray) {
        long min = dataArray.get(0);
        for (int i = 0; i < dataArray.size(); i++) {
            if (min > dataArray.get(i)) {
                min = dataArray.get(i);
            }
        }
        return min;
    }

    public long getMax(ArrayList<Long> dataArray) {
        long max = dataArray.get(0);
        for (int i = 0; i < dataArray.size(); i++) {
            if (max < dataArray.get(i)) {
                max = dataArray.get(i);
            }
        }
        return max;
    }
}
