package ua.com.alevel;

import ua.com.alevel.firstTask.DataRefact;
import ua.com.alevel.secondTask.UniqueName;
import ua.com.alevel.thirdTask.PathCost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecondModelMain {

    public static void main(String[] args) {
        try {
            System.out.print("Enter task number : ");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String numOfTask = bufferedReader.readLine();
            switch (numOfTask) {
                case "1":
                    new DataRefact().runTask();
                    break;
                case "2":
                    new UniqueName().run();
                    break;
                case "3":
                    new PathCost().run();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
