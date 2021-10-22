package ua.com.alevel.level_1.task1;

import java.util.ArrayList;
import java.util.Scanner;

public class NumberOfUniqueCharacters {

    public int findNumOfUniqueChar() {
        int indexOfDigits = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the array : ");
        String StringArray = in.nextLine();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < StringArray.length(); i++) {
            if(Character.isDigit(StringArray.charAt(i))){
                arrayList.add(Character.getNumericValue(StringArray.charAt(indexOfDigits)));
                indexOfDigits ++;
            }
        }

        int numOfRepeatElem = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(j) == arrayList.get(i)) {
                    numOfRepeatElem++;
                    break;
                }
            }
        }
        return arrayList.size() - numOfRepeatElem;
    }
}
