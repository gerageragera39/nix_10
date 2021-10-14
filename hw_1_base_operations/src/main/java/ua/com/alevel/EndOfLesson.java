package ua.com.alevel;

import java.util.Scanner;

public class  EndOfLesson {
    
    private static final int ONE_HOUR = 60;

    public void endTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of lesson : ");
        int lesson = scanner.nextInt();
        int time = 9*ONE_HOUR;
        int lesssonTime = 45;
        time += lesson*lesssonTime + (lesson-1)*5 + (lesson-1)/2*10;
        int hourOfEnd = time / ONE_HOUR;
        int minutesOfEnd = time - hourOfEnd*ONE_HOUR;
        System.out.println("End of the lesson - " + hourOfEnd + ":" + minutesOfEnd);
        System.out.println("The lesson ended at " + hourOfEnd + " hours and " + minutesOfEnd + " minutes");
    }
}
