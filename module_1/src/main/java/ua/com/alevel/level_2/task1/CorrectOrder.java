package ua.com.alevel.level_2.task1;

import java.util.ArrayList;
import java.util.Scanner;

public class CorrectOrder {

    public void checkForCorrectOrder() {
        ArrayList<Character> brackets = new ArrayList<Character>();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the string : ");
        String str = scan.nextLine();
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == '(') ||
                    (str.charAt(i) == '{') ||
                    (str.charAt(i) == '[') ||
                    (str.charAt(i) == ')') ||
                    (str.charAt(i) == '}') ||
                    (str.charAt(i) == ']')) {
                brackets.add(str.charAt(i));
            }
        }
        int i = 0;
        while (brackets.size() != 0) {
            if (i == brackets.size() - 1) {
                System.out.println("Input string is NOT valid");
                break;
            }
            if (brackets.get(i) == reverse(brackets.get(i + 1))) {
                brackets.remove(i);
                brackets.remove(i++);
                i = 0;
            } else {
                i++;
            }
        }
        if (brackets.size() == 0) {
            System.out.println("Input string is valid");
        }
    }

    public Character reverse(char brack) {
        switch (brack) {
            case ')':
                brack = '(';
                break;
            case '}':
                brack = '{';
                break;
            case ']':
                brack = '[';
                break;
            default:
                brack = ' ';
        }
        return brack;
    }
}