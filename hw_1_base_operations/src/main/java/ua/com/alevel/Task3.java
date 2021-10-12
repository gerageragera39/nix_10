package ua.com.alevel;

import java.util.Scanner;

public class Task3 {
    public void task(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of lesson : ");
        int lesson = scanner.nextInt();
        int time = 540;
        int lesTime = 45;
        time += lesson*lesTime + (lesson-1)*5 + (lesson-1)/2*10;
        System.out.println("End of the lesson - " + time / 60 + ":" + time % 60);
    }
}
