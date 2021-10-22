package ua.com.alevel;

public final class StringHelperUtil {

    private StringHelperUtil() { }

    public static String reverseAll(String text, int numOfWord) {

        if(numOfWord == 0) {
            text = reverseText(text);
        }else if(numOfWord!=-1){
            text = reverseWords(text, numOfWord);
        }else {
            String[] words = text.split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] = reverseText(words[i]);
            }
            text = String.valueOf(words[0]);
            for (int i = 1; i < words.length; i++) {
                text += " " + String.valueOf(words[i]);
            }
        }
        return text;
    }

    public static String reversePartOfSentence(String text, String part){

        String[] words = text.split(part);
        part = reverseText(part);
        text = String.valueOf(words[0] + part);
        for (int i = 1; i < words.length; i++) {
            text += String.valueOf(words[i]);
        }
        return text;
    }

    public static String reveverseByInterval(String text, int firstIndex, int lastIndex) {
        char startOfInterval = text.charAt(firstIndex);
        char finishOfInterval = text.charAt(lastIndex);
        String textOfInterval = String.valueOf(startOfInterval);
        for (int i = firstIndex + 1; i <= lastIndex; i++) {
            textOfInterval += String.valueOf(text.charAt(i));
        }
        textOfInterval = StringHelperUtil.reversePartOfSentence(text, textOfInterval);
        return textOfInterval;
    }

    public static String reverseText(String text) {

        char reverseArray[] = text.toCharArray();
        char temp;
        for (int i = 0, j = text.length()-1; i < j; i++, j--) {
            temp = reverseArray[i];
            reverseArray[i] = reverseArray[j];
            reverseArray[j] = temp;
        }
        text = String.valueOf(reverseArray[0]);
        for (int i = 1; i < reverseArray.length; i++) {
            text += String.valueOf(reverseArray[i]);
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
