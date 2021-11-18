package ua.com.alevel;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class WriterCSV {

    public String path;

    public WriterCSV(String path) {
        this.path = path;
    }

    public void writeCVS(List<String> list){
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.write('\n');
            for (int i = 0; i < list.size(); i++) {
                fileWriter.write('"');
                fileWriter.write(list.get(i));
                fileWriter.write('"');
                if(i!=list.size()-1){
                    fileWriter.write(',');
                }
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
