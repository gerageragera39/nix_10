package ua.com.alevel.firstTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstTask {

    public static final String pathInput = ".\\src\\main\\resources\\first_task\\first_task_input.txt";
    public static final String pathOutput = ".\\src\\main\\resources\\first_task\\first_task_output.txt";
    public static final Pattern REG_FIRST =
            Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern REG_SECOND =
            Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern REG_THIRD =
            Pattern.compile("^\\d{1,2}-\\d{1,2}-\\d{4}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern REG_FORTH =
            Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$", Pattern.CASE_INSENSITIVE);

    public void runTask() {
        try {
            int indexOfDataEnd = 0;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathInput));
            FileWriter fileWriter = new FileWriter(pathOutput);
            fileWriter.write("");
            fileWriter.flush();
            while (bufferedReader.ready()) {
                String str = bufferedReader.readLine();
                for (int i = 0; i < str.length(); i++) {
                    if (Character.isDigit(str.charAt(i))) {
                        String data = String.valueOf(str.charAt(i));
                        for (int j = i + 1; j < str.length(); j++) {
                            if ((Character.isDigit(str.charAt(j))) || (str.charAt(j) == '/') || (str.charAt(j) == '-')) {
                                data += str.charAt(j);
                                indexOfDataEnd++;
                            } else {
                                break;
                            }
                        }
                        i += indexOfDataEnd;
                        if (isValid(data)) {
                            writer(data);
                        }
                    }
                    indexOfDataEnd = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(String data) {
        Matcher matcher1 = REG_FIRST.matcher(data);
        Matcher matcher2 = REG_SECOND.matcher(data);
        Matcher matcher3 = REG_THIRD.matcher(data);
        Matcher matcher4 = REG_FORTH.matcher(data);
        if (matcher1.find() || matcher2.find() || matcher3.find() || matcher4.find()) {
            return true;
        }
        return false;
    }

    public void writer(String data) {
        String[] srtData = new String[3];
        boolean firstWrite = true;
        int indexOfData = 0, indexOfDelimiter = 0;
        for (int i = 0; i < data.length(); i++) {
            if ((data.charAt(i) == '/') || (data.charAt(i) == '-') || (i == data.length() - 1)) {
                for (int j = indexOfDelimiter; j < i; j++) {
                    if (firstWrite) {
                        srtData[indexOfData] = String.valueOf(data.charAt(j));
                        firstWrite = false;
                    } else {
                        srtData[indexOfData] += String.valueOf(data.charAt(j));
                    }
                }
                if(i==data.length()-1){
                    srtData[indexOfData]+=String.valueOf(data.charAt(i));
                }
                indexOfData++;
                indexOfDelimiter = i + 1;
                firstWrite = true;
            }
        }
        String[] outputData = new String[3];

        boolean writtenMonth = false;
        for (String srtDatum : srtData) {
            if(srtDatum.length() == 4){
                outputData[0] = srtDatum;
            }else if((Integer.parseInt(srtDatum) <= 12 && Integer.parseInt(srtDatum) > 0)&&(!writtenMonth)){
                outputData[1] = srtDatum;
                writtenMonth = true;
            }else if(Integer.parseInt(srtDatum) <= 31 && Integer.parseInt(srtDatum) > 0){
                outputData[2] = srtDatum;
            }else{
                return;
            }
        }

        for (String outputDatum : outputData) {
            if(outputDatum == null){
                return;
            }
        }

        try {
            FileWriter writer = new FileWriter(pathOutput, true);
            writer.write(outputData[0]+outputData[1]+outputData[2]);
            if((Integer.parseInt(outputData[1]) <= 12)&&(Integer.parseInt(outputData[2]) <= 12)){
                writer.write(" or " + outputData[0]+outputData[2]+outputData[1]);
            }
            writer.write('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
