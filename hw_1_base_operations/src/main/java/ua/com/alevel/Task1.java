package ua.com.alevel;

import java.util.Scanner;

public class Task1 {

    public void srchSum(){
        int sum = 0, dig = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the string ");
        String str = in.nextLine();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                dig = Integer.parseInt(String.valueOf(str.charAt(i)));
                sum += dig;
            }

        }
        System.out.println("Sum of digits = "+sum);
    }
}

