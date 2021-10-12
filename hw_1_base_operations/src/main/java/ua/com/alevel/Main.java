package ua.com.alevel;

public class Main {

    public static void main(String[] args) {
        Task1 t1 = new Task1();
        t1.srchSum();
        System.out.println("\n");
        Task2 t2 = new Task2();
        t2.quantSrch();
        System.out.println("\n");
        Task3 t3 = new Task3();
        t3.endTime();
    }
}



//package ua.com.alevel;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Select you event");
//        String position;
//        while ((position = reader.readLine()) != null) {
//            logic(position, reader);
//            position = reader.readLine();
//            switch (position) {
//                case "0" : {
//                    System.exit(0);
//                }
//                case "1" : {
//                    logic(position, reader);
//                }
//            }
//        }
//        reader.close();
//    }
//
//    private static void logic(String position, BufferedReader bufferedReader) {
//        switch (position) {
//            case "1" : task1(); break;
//            case "2" : task2(); break;
//            case "3" : task3(); break;
//        }
//        System.out.println("Your variant: if you want exit, please input 0, else, repeat logic");
//    }
//
//    private static void task1() {
//        Task1 t1 = new Task1();
//        t1.task();
//    }
//
//    private static void task2() {
//        Task2 task2 = new Task2();
//        task2.task();
//    }
//
//    private static void task3() {
//        Task3 task3 = new Task3();
//        task3.task();
//    }
//}