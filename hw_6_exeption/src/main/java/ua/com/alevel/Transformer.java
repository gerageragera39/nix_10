package ua.com.alevel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformer {

    //  m/d/yyyy - 3/4/2021
//    public static final Pattern TIME_FIRST =
//            Pattern.compile("^\\d{0,2}/\\d{0,2}/\\d{0,4}$", Pattern.CASE_INSENSITIVE);

    //  mmm-d-yy - Март 4 21
    //  mmm-d-yy - Апрель 32 1932
    public static final Pattern TIME_SECOND =
            Pattern.compile("^\\D* *\\d{0,2} *\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    //  09 Апрель 789 45:23
    public static final Pattern TIME_THIRD =
            Pattern.compile("^\\d{0,2} *\\D* *\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    //  /5/47 00:24:00:000
    //  /2/ :2
    public static final Pattern TIME_FIRST =
            Pattern.compile("^\\d{0,2}/\\d{0,2}/\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    //  1256 14:59
    public static final Pattern TIME_FIFTH =
            Pattern.compile("^\\d{1,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern[] patterns = {TIME_SECOND, TIME_SECOND, TIME_THIRD, TIME_FIRST, TIME_FIFTH};

    public void transformTime(String time) {
        syntax(3, time);
    }

    public void syntax(int index, String time) {
        Matcher matcher;
        switch (index) {
            case 1:
                matcher = TIME_FIRST.matcher(time);
                break;
            case 2:
                matcher = TIME_SECOND.matcher(time);
                break;
            case 3:
                matcher = TIME_THIRD.matcher(time);
                break;
            case 4:
                matcher = TIME_FIFTH.matcher(time);
                break;
            default:
                matcher = TIME_FIRST.matcher(time);
                break;
        }
        boolean isReg = matcher.find();
        if (isReg) {
            splitTime(time, index);
        } else {
            System.out.println("V ROT");
        }
    }

    public String[] splitTime(String time, int index) {
        String[] data = {"01", "01", "0000", "00", "00", "00", "000"};
        time += " ";
        int indexOfDelimiter = 0;
        int k = 0;
        int numberOfDig = 0;
        boolean firstStep = true;
        if (index == 4) {
            index = 1;
        }
        switch (index) {
            case 1:
                for (int i = 0; i < time.length(); i++) {
                    if (String.valueOf(time.charAt(i)).equals("/") || String.valueOf(time.charAt(i)).equals(" ") || String.valueOf(time.charAt(i)).equals(":")) {
                        for (int j = indexOfDelimiter; j < i; j++) {
                            if (firstStep) {
                                data[k] = String.valueOf(time.charAt(j));
                                firstStep = false;
                            } else {
                                data[k] += String.valueOf(time.charAt(j));
                            }
                        }
                        indexOfDelimiter = i + 1;
                        k++;
                        firstStep = true;
                    }
                }
                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + " ");
                }
                System.out.println();
                break;
            case 2:
                boolean fistWrite = true;
                boolean isMonth = false;
                if (Character.isLetter(time.charAt(0))) {
                    isMonth = true;
                }
                for (int i = 0; i < time.length(); i++) {
                    if (!Character.isLetter(time.charAt(i)) && isMonth) {
                        for (int j = 0; j < i; j++) {
                            if (firstStep) {
                                data[1] = String.valueOf(time.charAt(0));
                                firstStep = false;
                            } else {
                                data[1] += String.valueOf(time.charAt(j));
                            }
                        }
                        data[1] = StringMonth(data[1]);
                        indexOfDelimiter = i + 1;
                        isMonth = false;
                    } else {
                        if (String.valueOf(time.charAt(i)).equals(" ") || String.valueOf(time.charAt(i)).equals(":")) {
                            for (int j = indexOfDelimiter; j < i; j++) {
                                if (fistWrite) {
                                    data[k] = String.valueOf(time.charAt(j));
                                    fistWrite = false;
                                } else {
                                    data[k] += String.valueOf(time.charAt(j));
                                }
                            }
                            indexOfDelimiter = i + 1;
                            if (k == 0) {
                                k += 2;
                            } else {
                                k++;
                            }
                            fistWrite = true;
                        }
                    }
                }
                for (int i = 0; i < data[0].length(); i++) {
                    numberOfDig++;
                }
                if (numberOfDig >= 4) {
                    data[2] = data[0];
                    data[0] = "01";
                }

                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + " ");
                }
                System.out.println();
                break;
            case 3:
//                for (int i = 0; i < time.length(); i++) {
//                    if (String.valueOf(time.charAt(i)).equals(" ") || String.valueOf(time.charAt(i)).equals(":")) {
//                        for (int j = indexOfDelimiter; j < i; j++) {
//                            if (firstStep) {
//                                data[k] = String.valueOf(time.charAt(j));
//                                firstStep = false;
//                            } else {
//                                data[k] += String.valueOf(time.charAt(j));
//                            }
//                        }
//                        indexOfDelimiter = i + 1;
//                        k++;
//                        firstStep = true;
//                    }
//                }
//                data[1] = StringMonth(time);
//                for (int i = 0; i < data.length; i++) {
//                    System.out.print(data[i] + " ");
//                }
//                System.out.println();
//                break;

                boolean isDay = true;
//                for (int i = 0; i < time.length(); i++) {
//                    if (!(String.valueOf(time.charAt(i)).equals(" ") || !Character.isLetter(time.charAt(i)))&&isDay) {
//                        for (int j = 0; j < time.length(); j++) {
//                            if(String.valueOf(time.charAt(j)).equals(" ")){
//                                if (firstStep) {
//                                    data[0] = String.valueOf(time.charAt(0));
//                                    firstStep = false;
//                                } else {
//                                    data[0] += String.valueOf(time.charAt(j));
//                                }
//                            }
//                        }
//                        isDay = false;
//                        break;
//                    }else if(Character.isLetter(time.charAt(i))){
//                        int indexEndOfLetters = 0;
//                        for (int j = i; j < time.length(); j++) {
//                            if(Character.isLetter(time.charAt(j))){
//                                if (firstStep) {
//                                    data[1] = String.valueOf(time.charAt(i));
//                                    firstStep = false;
//                                } else {
//                                    data[1] += String.valueOf(time.charAt(j));
//                                }
//                                indexEndOfLetters++;
//                                break;
//                            }
//                        }
//                        i = indexEndOfLetters + 1;
//                    }
//                }

                //  09 Апрель 789 45:23
                if(!Character.isLetter(time.charAt(0))){
                    for (int i = 0; i < time.length(); i++) {
                        if(String.valueOf(time.charAt(i)).equals(" ")){
                            for (int j = 0; j < i; j++) {
                                if (firstStep) {
                                    data[0] = String.valueOf(time.charAt(0));
                                    firstStep = false;
                                } else {
                                    data[0] += String.valueOf(time.charAt(j));
                                }
                            }
                            break;
                        }
                    }
                }

                    //if(){...
                    for (int i = 0; i < time.length(); i++) {
                        if (!Character.isLetter(time.charAt(i))) {
                            for (int j = 0; j < i; j++) {
                                if (firstStep) {
                                    data[1] = String.valueOf(time.charAt(0));
                                    firstStep = false;
                                } else {
                                    data[1] += String.valueOf(time.charAt(j));
                                }
                            }
                            data[1] = StringMonth(data[1]);
                            indexOfDelimiter = i + 1;
                        }
                        break;
                    }



                for (int i = 0; i < data[0].length(); i++) {
                    numberOfDig++;
                }
                if (numberOfDig >= 4) {
                    data[2] = data[0];
                    data[0] = "01";
                }

                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + " ");
                }
                System.out.println();
                break;
            case 5:
                int indexOfFirstSpace = 0;
                for (int i = 0; i < time.length(); i++) {
                    if (String.valueOf(time.charAt(i)).equals(" ")) {
                        indexOfFirstSpace = i;
                        for (int j = indexOfDelimiter; j < i; j++) {
                            if (firstStep) {
                                data[2] = String.valueOf(time.charAt(j));
                                firstStep = false;
                            } else {
                                data[2] += String.valueOf(time.charAt(j));
                            }
                        }
                        break;
                    }
                    if (!firstStep) {
                        break;
                    }
                }
                firstStep = true;
                indexOfDelimiter = indexOfFirstSpace;
                k = 3;
                for (int i = indexOfFirstSpace + 1; i < time.length(); i++) {
                    if (String.valueOf(time.charAt(i)).equals(":") || String.valueOf(time.charAt(i)).equals(" ")) {
                        for (int j = indexOfDelimiter + 1; j < i; j++) {
                            if (firstStep) {
                                data[k] = String.valueOf(time.charAt(j));
                                firstStep = false;
                            } else {
                                data[k] += String.valueOf(time.charAt(j));
                            }
                        }
                        indexOfDelimiter = i;
                        k++;
                        firstStep = true;
                    }
                }

                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + " ");
                }
                System.out.println();
                break;
        }
        return data;
    }

    public String StringMonth(String time) {
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String month = " ";
        int firstStep = 1;
        for (int i = 0; i < time.length(); i++) {
            if (Character.isLetter(time.charAt(i))) {
                if (firstStep == 1) {
                    month = String.valueOf(time.charAt(i));
                    firstStep = 0;
                } else {
                    month += String.valueOf(time.charAt(i));
                }
            }
        }

        switch (month) {
            case "Январь":
                return "01";
            case "Февраль":
                return "02";
            case "Март":
                return "03";
            case "Апрель":
                return "04";
            case "Май":
                return "05";
            case "Июнь":
                return "06";
            case "Июль":
                return "07";
            case "Август":
                return "08";
            case "Сентябрь":
                return "09";
            case "Октябрь":
                return "10";
            case "Ноябрь":
                return "11";
            case "Декабрь":
                return "12";
            default:
                return "00";
        }
    }
}
