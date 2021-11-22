package ua.com.alevel.SecondTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniqueName {

    public static final String pathInput = ".\\src\\main\\resources\\secondTaskFiles\\second_task_input.txt";

    public void run(){
        try {
            int indexOfDataEnd = 0;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathInput));
            List<String> uniqueNames = new ArrayList<>();
            List<String> removedNames = new ArrayList<>();
            while (bufferedReader.ready()) {
                String str = bufferedReader.readLine();
                for (int i = 0; i < str.length(); i++) {
                    if (Character.isLetter(str.charAt(i))) {
                        String name = String.valueOf(str.charAt(i));
                        for (int j = i + 1; j < str.length(); j++) {
                            if (Character.isLetter(str.charAt(j))) {
                                name += str.charAt(j);
                                indexOfDataEnd++;
                            } else {
                                break;
                            }
                        }
                        i += indexOfDataEnd;

                        boolean isUnique = true;
                        for (int j = 0; j < uniqueNames.size(); j++) {
                            if(uniqueNames.get(j).equals(name)){
                                isUnique = false;
                                uniqueNames.remove(j);
                                removedNames.add(name);
                            }
                        }

                        for (int k = 0; k < removedNames.size(); k++) {
                            if(removedNames.get(k).equals(name)){
                                isUnique = false;
                            }
                        }

                        if(isUnique){
                            uniqueNames.add(name);
                        }
                    }
                    indexOfDataEnd = 0;
                }
            }
            if(uniqueNames.size()!=0){
                System.out.println("First unique name - " + uniqueNames.get(0));
            }else{
                System.out.println("There are no unique names");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
