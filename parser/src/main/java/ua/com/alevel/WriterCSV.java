package ua.com.alevel;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class WriterCSV {

    public String path;

    public WriterCSV(String pth) {
        setPath(pth);
    }

    public void writeCVS(List<String> fields){
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.write('\n');

            for (int i = 0; i < fields.size(); i++) {
                fileWriter.write('"');
                fileWriter.write(fields.get(i));
                fileWriter.write('"');
                if(i!=fields.size()-1){
                    fileWriter.write(',');
                }
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCSV(Class tClass){
        try {
            Field[] fields= tClass.getDeclaredFields();
            String[] strFields = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                strFields[i] = fields[i].getName();
            }
            FileWriter fileWriter1 = new FileWriter(path);
            FileWriter fileWriter2 = new FileWriter(path, true);
            boolean firstWrite = true;
            for (int i = 0; i < fields.length; i++) {
                if(firstWrite){
                    fileWriter1.write(String.valueOf('"') + strFields[0] + String.valueOf('"'));
                    fileWriter1.flush();
                    if(i!=fields.length-1){
                        fileWriter2.write(",");
                    }
                    firstWrite = false;
                }else{
                    fileWriter2.write(String.valueOf('"') + strFields[i] + String.valueOf('"'));
                    if(i!=fields.length-1){
                        fileWriter2.write(",");
                    }
                }
            }
            fileWriter2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCSV(){
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String pth) {
        this.path = pth;
    }
}
