package ua.com.alevel;

import java.util.Scanner;

public class Controller {

    public static final Scanner scanner = new Scanner(System.in);
    public static MathSet mathSet;

    public void run(){
        String position = "1";
        int fistStep = 0;
        descriptionMathSet();
        while (position != null) {
            if(fistStep != 0){
                descriptionMathSet();
            }
            fistStep = 1;
            System.out.print("select your option : ");
            position = scanner.nextLine();
            if (!position.equals("0")) {
                MathSetLogic(position);
                descriptionMethods();
                methodLogic();
//                index();
            }
            if (position.equals("0")) {
                System.exit(0);
            }
        }
    }

    private static void MathSetLogic(String constructorNum) {
        switch (constructorNum) {
            case "1":
                mathSet = new MathSet();
                break;

            case "2":
                System.out.print("Enter the capacity : ");
                int capacity = scanner.nextInt();
                mathSet = new MathSet(capacity);
                break;

            case "3":
                System.out.print("Enter the array : ");

                MyArrayList arrayList = new MyArrayList();
                scanArray(arrayList);
                mathSet = new MathSet(arrayList.getArray());
                break;

            case "4":
                String StringOneArray;
                MyArrayList list = new MyArrayList();
                String position1 = "1";
                while (position1 != null){
                    if(position1.equals("1")){
                        System.out.print("Enter the MathSet : ");
                        scanArray(list);
                        System.out.println("Enter 1, if you want to create one more Array");
                        position1 = scanner.nextLine();
                    } else {
                        position1 = null;
                    }
                }
                mathSet = new MathSet(list.getArray());

//                System.out.println();
//                for (int i = 0; i < mathSet.toArray().length; i++) {
//                    System.out.println(mathSet.getByIndex(i));
//                }
                break;

            case "5":
                System.out.print("Enter the MathSet : ");

                MathSet ms = new MathSet();

                scanArray(ms.getList());
                mathSet = new MathSet(ms.getList().getArray());
                break;

            case "6":
                MathSet arrayMathSet = new MathSet();
                String position2 = "1";
                while (position2 != null){
                    if(position2.equals("1")){
                        System.out.print("Enter the MathSet : ");
                        scanArray(arrayMathSet.getList());
                        System.out.println("Enter 1, if you want to create one more MathSet");
                        position2 = scanner.nextLine();
                    } else{
                        position2 = null;
                    }
                }
                mathSet = new MathSet(arrayMathSet.getList().getArray());
                break;
        }
    }

    private static void descriptionMathSet(){
        System.out.println();
        System.out.println("if you want to create empty MathSet, please enter 1");
        System.out.println("if you want create empty MathSet with a certain capacity, please enter 2");
        System.out.println("if you want to create MathSet with a certain array of numbers, please enter 3");
        System.out.println("if you want to create MathSet with a certain arrays of numbers (Number[] ... numbers), please enter 4");
        System.out.println("if you want to create MathSet with a certain MathSet, please enter 5");
        System.out.println("if you want to create MathSet with a certain MathSets (MathSet[] ... ms), please enter 6");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    private static void methodLogic(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select method number");
        String methodNum = scan.nextLine();
        System.out.println("Select arguments for method");
        switch (methodNum) {
            case "1":
                System.out.println("add(Number n), enter 1");
                System.out.println("add(Number ... n), enter 2");
                String pos1 = scan.nextLine();
                adds(pos1);
                break;
            case "2":
                System.out.println("join(MathSet ms), enter 1");
                System.out.println("join(MathSet ... ms), enter 2");
                String pos2 = scan.nextLine();
                joins(pos2);
                break;
            case "3":
                System.out.println("intersection(MathSet ms), enter 1");
                System.out.println("intersection(MathSet ... ms), enter 2");
                String pos3 = scan.nextLine();
                intersections(pos3);
                break;
            case "4":
                System.out.println("sortDesc(), enter 1");
                System.out.println("sortDesc(int firstIndex, int lastIndex), enter 2");
                System.out.println("sortDesc(Number value), enter 3");
                String pos4 = scan.nextLine();
                sortDesc(pos4);
                break;
            case "5":
                System.out.println("sortAsc(), enter 1");
                System.out.println("sortAsc(int firstIndex, int lastIndex), enter 2");
                System.out.println("sortAsc(Number value), enter 3");
                String pos5 = scan.nextLine();
                sortAsc(pos5);
                break;
            case "6":
                System.out.println("get(int index), enter 1");
                System.out.println("getMax, enter 2");
                System.out.println("getMin, enter 3");
                System.out.println("getAverage, enter 4");
                System.out.println("getMedian, enter 5");
                String pos6 = scan.nextLine();
                getters(pos6);
                break;
            case "7":
                System.out.println("toArray(), enter 1");
                System.out.println("toArray(int firstIndex, int lastIndex), enter 2");
                String pos7 = scan.nextLine();
                toArrays(pos7);
                break;
            case "8":
                System.out.println("cut(int index), enter 1");
                System.out.println("cut(int firstIndex, int lastIndex), enter 2");
                System.out.println("clear(), enter 3");
                System.out.println("clear(Number[] numbers), enter 4");
                String pos8 = scan.nextLine();
                cleansing(pos8);
                break;
            case "0":
                descriptionMathSet();
                break;
        }
        index();
    }

    private static void descriptionMethods(){
        System.out.println();
        System.out.println("if you want to add, please enter 1");
        System.out.println("if you want to join, please enter 2");
        System.out.println("if you want to intersection, please enter 3");
        System.out.println("if you want to sortDesc, please enter 4");
        System.out.println("if you want to sortAsc, please enter 5");
        System.out.println("if you want to get number, please enter 6");
        System.out.println("if you want to get Array, please enter 7");
        System.out.println("if you want to cut or clear, please enter 8");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    public static void adds(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                MyArrayList arrayList = new MyArrayList();
                System.out.println("Enter the number");
                String doubleNum = scan.nextLine();
                double num = Double.parseDouble(doubleNum);
                mathSet.add(num);
                break;
            case "2":
                System.out.print("Enter the array : ");
                MyArrayList list = new MyArrayList();
                scanArray(list);
                mathSet.add(list.getArray());
                break;
        }
    }

    public static void joins(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                System.out.print("Enter the MathSet : ");
                MyArrayList list1 = new MyArrayList();
                MathSet ms = new MathSet();
                ms.add(scanArray(list1).getArray());
//                String StringArray = scan.nextLine();
//                MathSet arrayList = new MathSet();
//
//                for (int i = 0; i < StringArray.length(); i++) {
//                    if (Character.isDigit(StringArray.charAt(i))) {
//                        arrayList.add(Character.getNumericValue(StringArray.charAt(i)));
//                    }
//                }
                mathSet.join(ms);
                break;
            case "2":
                String StringOneArray;
                MathSet list2 = new MathSet();
                String position1 = "1";
                while (position1 != null){
                    if(position1.equals("1")){
                        System.out.print("Enter the MathSet : ");
                        MathSet mathSet = new MathSet();
                        mathSet.add(scanArray(list2.getList()).getArray());
//                        StringOneArray = scan.nextLine();
//
//                        for (int i = 0; i < StringOneArray.length(); i++) {
//                            if (Character.isDigit(StringOneArray.charAt(i))) {
//                                list2.add(Character.getNumericValue(StringOneArray.charAt(i)));
//                            }
//                        }
                        System.out.println("Enter 1, if you want to create one more Array");
                        position1 = scan.nextLine();
                    } else {
                        position1 = null;
                    }
                }
                mathSet.join(list2);
                break;
        }
    }

    public static void intersections(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                System.out.print("Enter the MathSet : ");
                MyArrayList list1 = new MyArrayList();
                MathSet ms = new MathSet();
                ms.add(scanArray(list1).getArray());
                mathSet.intersection(ms);
                break;
            case "2":
                MathSet list2 = new MathSet();
                String position1 = "1";
                while (position1 != null){
                    if(position1.equals("1")){
                        System.out.print("Enter the MathSet : ");
                        MathSet mathSet = new MathSet();
                        mathSet.add(scanArray(list2.getList()).getArray());
//                        StringOneArray = scan.nextLine();
//
//                        for (int i = 0; i < StringOneArray.length(); i++) {
//                            if (Character.isDigit(StringOneArray.charAt(i))) {
//                                list2.add(Character.getNumericValue(StringOneArray.charAt(i)));
//                            }
//                        }
                        System.out.println("Enter 1, if you want to create one more Array");
                        position1 = scan.nextLine();
                    } else {
                        position1 = null;
                    }
                }
                mathSet.intersection(mathSet);
                break;
        }
    }

    public static void sortDesc(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                mathSet.sortDesc();
                break;
            case "2":
                System.out.println("Enter firstIndex");
                int firstIndex = scan.nextInt();
                System.out.println("Enter lastIndex");
                int lastIndex = scan.nextInt();
                mathSet.sortDesc(firstIndex,lastIndex);
                break;
            case "3":
                System.out.println("Enter value");
                int value = scan.nextInt();
                mathSet.sortDesc(value);
                break;
        }
    }

    public static void sortAsc(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                mathSet.sortAsc();
                break;
            case "2":
                System.out.println("Enter firstIndex");
                int firstIndex = scan.nextInt();
                System.out.println("Enter lastIndex");
                int lastIndex = scan.nextInt();
                mathSet.sortAsc(firstIndex,lastIndex);
                break;
            case "3":
                System.out.println("Enter value");
                int value = scan.nextInt();
                mathSet.sortAsc(value);
                break;
        }
    }

    public static void getters(String pos){
        switch (pos){
            case "1":
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter index");
                int index = scan.nextInt();
                System.out.println(mathSet.getByIndex(index));
                break;
            case "2":
                System.out.println(mathSet.getMax());
                break;
            case "3":
                System.out.println(mathSet.getMin());
                break;
            case "4":
                System.out.println(mathSet.getAverage());
                break;
            case "5":
                System.out.println(mathSet.getMedian());
                break;
        }
    }

    public static void toArrays(String pos){
        switch (pos){
            case "1":
                for (int i = 0; i < mathSet.toArray().length; i++) {
                    System.out.print(mathSet.getByIndex(i) + ", ");
                }
                if(mathSet.toArray().length == 0){
                    System.out.println("MathSet is empty");
                }
                break;
            case "2":
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter firstIndex");
                int firstIndex = scan.nextInt();
                System.out.println("Enter lastIndex");
                int lastIndex = scan.nextInt();
                for (int i = 0; i < mathSet.toArray(firstIndex,lastIndex).length; i++) {
                    System.out.print(mathSet.toArray(firstIndex,lastIndex)[i] + ", ");
                }
                if(mathSet.toArray(firstIndex,lastIndex).length == 0){
                    System.out.println("MathSet is empty");
                }
                break;
        }
    }

    public static void cleansing(String pos){
        Scanner scan = new Scanner(System.in);
        switch (pos){
            case "1":
                System.out.println("Enter index");
                int index = scan.nextInt();
                mathSet.cut(index);
                break;
            case "2":
                System.out.println("Enter firstIndex");
                int firstIndex = scan.nextInt();
                System.out.println("Enter lastIndex");
                int lastIndex = scan.nextInt();
                mathSet.cut(firstIndex,lastIndex);
                break;
            case "3":
                mathSet.clear();
                break;
            case "4":
                System.out.print("Enter the array : ");
                String StringArray = scan.nextLine();
                MyArrayList arrayList = new MyArrayList();

                for (int i = 0; i < StringArray.length(); i++) {
                    if (Character.isDigit(StringArray.charAt(i))) {
                        arrayList.add(Character.getNumericValue(StringArray.charAt(i)));
                    }
                }
                mathSet.clear(arrayList.getArray());
                break;
        }
    }


    public static void index() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nIf you want to continue with THIS MATHSET enter '1', if not then '0'");
        String position = scan.nextLine();
        switch (position) {
            case "0": {
                break;
            }
            case "1": {
                descriptionMethods();
                methodLogic();
            }
        }
    }

    public static MyArrayList scanArray(MyArrayList arrayList){
        Scanner scan = new Scanner(System.in);
        System.out.print(" (separating items with commas(',') and at the end (;) ): ");
        String StringArray = scan.nextLine();
        String doubleNumber = " ";
        double doubleNum = 0;
        int intNum = 0;
        int index = 0, flag = 0;
        for (int j = 0; j < StringArray.length(); j++) {
            if(String.valueOf(StringArray.charAt(j)).equals(",") || String.valueOf(StringArray.charAt(j)).equals(";")){
                if(j != 0){
                    for (int k = index; k < j; k++) {
                        if(Character.isDigit(StringArray.charAt(k))){
                            intNum = intNum*10 + Character.getNumericValue(StringArray.charAt(k));
                        } else if(String.valueOf(StringArray.charAt(k)).equals(".")){
                            flag = 1;
                            for (int i = index; i < j; i++) {
                                doubleNumber += String.valueOf(StringArray.charAt(i));
                            }
                        }
                    }
                }
                if(flag == 0){
                    arrayList.add(intNum);
                } else{
                    String parseNum = String.valueOf(doubleNumber.charAt(0));
                    for (int i = 1; i < doubleNumber.length(); i++) {
                        if(Character.isDigit((doubleNumber.charAt(i))) || (doubleNumber.charAt(i)=='.')){
                            parseNum += String.valueOf(doubleNumber.charAt(i));
                        }
                    }
                    doubleNum = Double.parseDouble(parseNum);
                    arrayList.add(doubleNum);
                }
                doubleNum = 0;
                intNum = 0;
                index = j;
                doubleNumber = " ";
                flag = 0;
            }
            if(String.valueOf(StringArray.charAt(j)).equals(";")){
                break;
            }
        }
        return arrayList;
    }

//    public static MyArrayList getList(){
//        System.out.print("Enter the array : ");
//        String StringArray = scanner.nextLine();
//        MyArrayList arrayList = new MyArrayList();
//
//        for (int i = 0; i < StringArray.length(); i++) {
//            if (Character.isDigit(StringArray.charAt(i))) {
//                arrayList.add(Character.getNumericValue(StringArray.charAt(i)));
//            }
//        }
//        return arrayList;
//    }
}