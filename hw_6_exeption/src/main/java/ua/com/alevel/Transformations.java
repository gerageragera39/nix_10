package ua.com.alevel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformations {

    //  dd/mm/yy - 01/12/21
    public static final Pattern TIME_FIRST =
            Pattern.compile("^\\d{2}/\\d{2}/\\d{2} - \\d{2}/\\d{2}/\\d{2} $", Pattern.CASE_INSENSITIVE);

    //  m/d/yyyy - 3/4/2021
    public static final Pattern TIME_SECOND =
            Pattern.compile("^\\d{1}/\\d{1}/\\d{4} - \\d{1}/\\d{1}/\\d{4} $", Pattern.CASE_INSENSITIVE);

    //  mmm-d-yy - Март 4 21
    public static final Pattern TIME_THIRD_1 =
            Pattern.compile("^\\d{3}-\\d{1}-\\d{2} - $", Pattern.CASE_INSENSITIVE);
    public static final Pattern TIME_THIRD_2 =
            Pattern.compile("^ \\d{1} \\d{2} $", Pattern.CASE_INSENSITIVE);

//    public static final Pattern TIME_THIRD =
//            Pattern.compile("^\\d{3}-\\d{1}-\\d{2} - [Март]|[Апрель] \\d{1} \\d{2}$", Pattern.CASE_INSENSITIVE);


    //   12/23/34 - 12/23/34
    public String transfer(String time) {
        time += " ";
        ArrayList<Integer> index = new ArrayList<>();
        int flag = 0;
        if (regex12(time)) {
            System.out.println(time);
        } else if (regex3(time)&&StringMonth(time)) {
            System.out.println(time);
        }


        if (flag != 1) {
            for (int i = 0; i < time.length(); i++) {
                if (String.valueOf(time.charAt(i)).equals("-")) {
                    index.add(i);
                }
            }
        }

        return time;
    }

    public boolean StringMonth(String time){
        String[] months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        String month = " ";
        int firstStep = 1;
        for (int i = 0; i < time.length(); i++) {
            if(Character.isLetter(time.charAt(i))){
                if(firstStep == 1){
                    month = String.valueOf(time.charAt(i));
                    firstStep = 0;
                }else{
                    month += String.valueOf(time.charAt(i));
                }
            }
        }
        for (String m : months) {
            if(month.equals(m)){
                return true;
            }
        }
        return false;
    }

    public boolean regex3(String time){
        //  111-2-33 - Март 4 21
        int firstIndex = 0;
        String startReg = String.valueOf(time.charAt(0));
        String finishReg = " ";
        int firstStep = 1;
        for (int i = 0; i < time.length(); i++) {
            if(Character.isLetter(time.charAt(i))){
                firstIndex = i;
                break;
            }
        }
        for (int i = 1; i < firstIndex; i++) {
            startReg += String.valueOf(time.charAt(i));
        }
        for (int i = firstIndex + 1; i < time.length(); i++) {
            if(!Character.isLetter(time.charAt(i))){
                if(firstStep == 1){
                    finishReg = String.valueOf(time.charAt(i));
                    firstStep = 0;
                }else{
                    finishReg += String.valueOf(time.charAt(i));
                }
            }
        }

        Matcher matcher3 = TIME_THIRD_1.matcher(startReg);
        boolean timeRegex3 = matcher3.find();
        Matcher matcher4 = TIME_THIRD_2.matcher(finishReg);
        boolean timeRegex4 = matcher4.find();
        boolean allReg = timeRegex3&&timeRegex4;
        return allReg;
    }

    public boolean regex12(String time){
        //  11/22/33 - 01/12/21
        Matcher matcher1 = TIME_FIRST.matcher(time);
        boolean timeRegex1 = matcher1.find();
        Matcher matcher2 = TIME_SECOND.matcher(time);
        boolean timeRegex2 = matcher2.find();

        String d = " ";
        int indexOfSlash = 0;
        int firstStep = 1;
        ArrayList<String> data = new ArrayList<>();
        int indexOfDash = 0;
        ArrayList<Integer> index = new ArrayList<>();
        int flag = 0;
        if (timeRegex1 || timeRegex2) {
            for (int i = 0; i < time.length(); i++) {
                if (String.valueOf(time.charAt(i)).equals("/") || String.valueOf(time.charAt(i)).equals(" ")) {
                    for (int j = indexOfSlash; j < i; j++) {
                        if (firstStep == 1) {
                            d = String.valueOf(time.charAt(j));
                            firstStep = 0;
                        } else {
                            d += String.valueOf(time.charAt(j));
                        }
                    }
                    indexOfSlash = i + 1;
                    firstStep = 1;
                    data.add(d);
                    index.add(i);
                    flag = 1;
                }
                if (String.valueOf(time.charAt(i)).equals("-")) {
                    indexOfDash = i;
                }
            }
            System.out.println(data);
        }
        boolean reg12 = timeRegex2 || timeRegex1;
        return reg12;
    }
}
