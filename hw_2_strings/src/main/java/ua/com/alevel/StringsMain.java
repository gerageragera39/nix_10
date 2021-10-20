package ua.com.alevel;

import java.util.Scanner;

public class StringsMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int numOfWord = scanner.nextInt();
        text = StringHelperUtil.reverseAll(text, numOfWord);
    }
}
