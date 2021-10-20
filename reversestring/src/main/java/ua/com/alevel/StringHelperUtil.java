package ua.com.alevel;

public final class StringHelperUtil {

    private StringHelperUtil() { }

    public static String reverseAll(String text, int numOfWord) {

        if(numOfWord == 0) {
            text = reverseText(text);
            System.out.println(text);
        }else {
            text = reverseWords(text, numOfWord);
            System.out.println(text);
        }
        return text;
    }

    public static String reverseText(String text) {

        char arr[] = text.toCharArray();
        char temp;
        for (int i = 0, j = text.length()-1; i < j; i++, j--) {
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        text = String.valueOf(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            text += String.valueOf(arr[i]);
        }
        return text;
    }

    public static String reverseWords(String text, int numOfWord) {

        numOfWord--;
        String[] words = text.split(" ");
        words[numOfWord] = reverseText(words[numOfWord]);
        text = String.valueOf(words[0]);
        for (int i = 1; i < words.length; i++) {
            text += " " + String.valueOf(words[i]);
        }
        return text;
    }
}
