package ua.com.alevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformer {

    public static final Pattern TIME_SECOND =
            Pattern.compile("^\\D* *\\d{0,2} *\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern TIME_THIRD =
            Pattern.compile("^\\d{0,2} *\\D* *\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern TIME_FIRST =
            Pattern.compile("^\\d{0,2}/\\d{0,2}/\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern TIME_FIRST_2 =
            Pattern.compile("^\\d{0,2}-\\d{0,2}-\\d{0,4} *\\d{0,2}:*\\d{0,2}:*\\d{0,2}:*\\d{0,3}$", Pattern.CASE_INSENSITIVE);

    public static TimeMain timeMain = new TimeMain();

    public int[] transformTime(String time, int index) {
        return syntax(time, index);
    }

    public int[] syntax(String time, int index) {
        int[] intData = null;
        try {
            Matcher matcher = null;
            Matcher matcher2 = null;
            switch (index) {
                case 1:
                    matcher = TIME_FIRST.matcher(time);
                    matcher2 = TIME_FIRST_2.matcher(time);
                    break;
                case 2:
                    matcher = TIME_SECOND.matcher(time);
                    break;
                case 3:
                    matcher = TIME_THIRD.matcher(time);
                    matcher2 = TIME_SECOND.matcher(time);
                    break;
                default:
                    System.out.println("incorrect index");
                    return timeMain.runStartData();
            }
            boolean isReg;
            if (index == 2) {
                isReg = matcher.find();
            } else if (index == 3) {
                isReg = matcher.find() && !matcher2.find();
            } else {
                isReg = matcher.find() || matcher2.find();
            }
            if (isReg) {
                String[] data = splitTime(time, index);
                intData = parseInteger(data);
            } else {
                System.out.println();
                System.out.println("Wrong data entry");
                return timeMain.runStartData();
            }
        } catch (NullPointerException e) {
            System.out.println("incorrect index");
            return timeMain.runStartData();
        }
        return intData;
    }

    public String[] splitTime(String time, int index) {
        String[] data = {"01", "01", "0000", "00", "00", "00", "000"};
        try {
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
                        if (String.valueOf(time.charAt(i)).equals("/") || String.valueOf(time.charAt(i)).equals(" ") || String.valueOf(time.charAt(i)).equals(":") || String.valueOf(time.charAt(i)).equals("-")) {
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
                    if (numberOfDig >= 3) {
                        data[2] = data[0];
                        data[0] = "01";
                    }
                    break;
                case 3:
                    int indexOfEndDay = 0;
                    if (!Character.isLetter(time.charAt(0))) {
                        for (int i = 0; i < time.length(); i++) {
                            if (String.valueOf(time.charAt(i)).equals(" ")) {
                                for (int j = 0; j < i; j++) {
                                    if (firstStep) {
                                        data[0] = String.valueOf(time.charAt(0));
                                        firstStep = false;
                                    } else {
                                        data[0] += String.valueOf(time.charAt(j));
                                    }
                                    indexOfEndDay++;
                                }
                                break;
                            }
                        }
                    }
                    boolean monthWritten = false;
                    firstStep = true;
                    if (indexOfEndDay + 1 != time.length()) {
                        if (Character.isLetter(time.charAt(indexOfEndDay + 1))) {
                            for (int i = indexOfEndDay + 1; i < time.length(); i++) {
                                if (!Character.isLetter(time.charAt(i))) {
                                    for (int j = indexOfEndDay + 1; j < i; j++) {
                                        if (firstStep) {
                                            data[1] = String.valueOf(time.charAt(j));
                                            firstStep = false;
                                            monthWritten = true;
                                        } else {
                                            data[1] += String.valueOf(time.charAt(j));
                                        }
                                    }
                                    data[1] = StringMonth(data[1]);
                                    indexOfDelimiter = i;
                                    if (monthWritten) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    firstStep = true;
                    k = 2;
                    if (indexOfEndDay + 1 != time.length()) {
                        for (int i = indexOfDelimiter + 1; i < time.length(); i++) {
                            if (String.valueOf(time.charAt(i)).equals(" ") || String.valueOf(time.charAt(i)).equals(":")) {
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
                    }

                    for (int i = 0; i < data[0].length(); i++) {
                        numberOfDig++;
                    }
                    if (numberOfDig >= 3) {
                        data[2] = data[0];
                        data[0] = "01";
                    }
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println();
            System.out.println("incorrect");
            timeMain.runStartData();
        }
        return data;
    }

    public boolean exam(String[] data) {
        boolean trueExam = true;
        try {
            if (!((Integer.parseInt(data[1]) > 12)||((Integer.parseInt(data[1]) <= 0)) || (Integer.parseInt(data[2]) > 9999) || (Integer.parseInt(data[3]) > 23) || (Integer.parseInt(data[4]) > 59) || (Integer.parseInt(data[5]) > 59) || (Integer.parseInt(data[6]) > 999) || (Integer.parseInt(data[1]) > 12) || (Integer.parseInt(data[2]) < 0))) {
                if ((Integer.parseInt(data[1]) == 1) || (Integer.parseInt(data[1]) == 3) || (Integer.parseInt(data[1]) == 5) || (Integer.parseInt(data[1]) == 7) || (Integer.parseInt(data[1]) == 8) || (Integer.parseInt(data[1]) == 10) || (Integer.parseInt(data[1]) == 12)) {
                    if ((Integer.parseInt(data[0]) > 31) || (Integer.parseInt(data[0]) < 0)) {
                        trueExam = false;
                    }
                } else if ((Integer.parseInt(data[1]) == 4) || (Integer.parseInt(data[1]) == 6) || (Integer.parseInt(data[1]) == 9) || (Integer.parseInt(data[1]) == 11)) {
                    if ((Integer.parseInt(data[0]) > 30) || (Integer.parseInt(data[0]) < 0)) {
                        trueExam = false;
                    }
                } else {
                    if (((Integer.parseInt(data[2]) % 4 != 0) || ((Integer.parseInt(data[2]) % 100 == 0))) && (Integer.parseInt(data[2]) % 400 != 0)) {
                        if ((Integer.parseInt(data[0]) > 28) || (Integer.parseInt(data[0]) < 0)) {
                            trueExam = false;
                        }
                    } else {
                        if ((Integer.parseInt(data[0]) > 29) || (Integer.parseInt(data[0]) < 0)) {
                            trueExam = false;
                        }
                    }
                }
            } else {
                trueExam = false;
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("incorrect");
            timeMain.runStartData();
        }
        return trueExam;
    }

    public int[] parseInteger(String[] data) {
        int[] intData = new int[7];
        if (exam(data)) {
            for (int i = 0; i < intData.length; i++) {
                intData[i] = Integer.parseInt(data[i]);
            }
        } else {
            System.out.println("Wrong data entry");
            return timeMain.runStartData();
        }
        return intData;
    }

    public String StringMonth(String time) {
        String[] months = {"????????????", "??????????????", "????????", "????????????", "??????", "????????", "????????", "????????????", "????????????????", "??????????????", "????????????", "??????????????"};
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
            case "????????????":
                return "01";
            case "??????????????":
                return "02";
            case "????????":
                return "03";
            case "????????????":
                return "04";
            case "??????":
                return "05";
            case "????????":
                return "06";
            case "????????":
                return "07";
            case "????????????":
                return "08";
            case "????????????????":
                return "09";
            case "??????????????":
                return "10";
            case "????????????":
                return "11";
            case "??????????????":
                return "12";
            default:
                System.out.println("Wrong month");
                timeMain.runStartData();
                return "00";
        }
    }
}
