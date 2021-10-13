package ua.com.alevel;

import java.util.Scanner;

public class Task3 {
    
    private static final int ONE_HOUR = 60; 
    
    public void endTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of lesson : ");
        int lesson = scanner.nextInt();
        int time = 540;
        int lesTime = 45;
        time += lesson*lesTime + (lesson-1)*5 + (lesson-1)/2*10;
        System.out.println("End of the lesson - " + time / ONE_HOUR + ":" + time % ONE_HOUR);
    }
}
