package ua.com.alevel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderCSV {

    public String path;

    public ReaderCSV(String path) {
        this.path = path;
    }

    public List<String> readCSV(){
        List<String> csvList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (bufferedReader.ready()){
                csvList.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }
}
