package ua.com.alevel;

import java.util.*;

public class Task2 {
    public void task(){

        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String letter;
        int qnt[] = new int[str.length()];
        HashMap<String,Integer > map = new HashMap<String,Integer>();
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))){
                letter = String.valueOf(str.charAt(i));
                for (int j = 0; j < str.length(); j++) {
                    if (letter.equals(String.valueOf(str.charAt(j)))) {
                        qnt[j]++;
                    }
                }
                map.put(letter, qnt[i]);
            }
        }
        //System.out.println(map);
//        Set set = map.entrySet();
//
//        // Получаем итератор
//        Iterator i = set.iterator();
//
//        // Отображение элементов
//        int arr[] = new int[map.size()];
//        for (int j = 0; j < map.size(); j++) {
//
//            System.out.println();
//        }
//            Map.Entry me = (Map.Entry)i.next();
//            System.out.print(me.getKey() + ": ");
//            System.out.println(me.getValue());

       map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
//        TreeMap<Integer,String> sortMap = new TreeMap<Integer,String>(Collections.reverseOrder());
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            sortMap.put(entry.getValue(),entry.getKey());
//        }
//        for (Map.Entry<String,Integer> entry : map.entrySet()) {
//            System.out.println(entry.getValue() + " - " + entry.getKey());
//        }
    }
}
