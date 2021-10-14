package ua.com.alevel;

import java.util.*;

public class NumberOfLetterRepetitions {
    public void quantitySearch(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the string : ");
        String str = scan.nextLine();
        String letter;
        int qnt[] = new int[str.length()];
        HashMap<String,Integer > map = new HashMap<String,Integer>();
        for (int i = 0; i < str.length(); i++) {
            if(Character.isLetter(str.charAt(i))){
                letter = String.valueOf(str.charAt(i));
                for (int j = 0; j < str.length(); j++) {
                    if (letter.equals(String.valueOf(str.charAt(j)))) {
                        qnt[j]++;
                    }
                }
                map.put(letter, qnt[i]);
            }
        }
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
