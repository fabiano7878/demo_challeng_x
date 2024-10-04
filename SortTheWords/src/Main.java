import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("What word would you like to sorted today? input it that:");
        String sentenceInputed = requestSentece();
        List<String> arraysOfWords = responseArrayOfWords(sentenceInputed);
        sortedArrayOfWords(arraysOfWords);
    }

    /**
     * here I will scanner the message of user and return the sentence String
     * @return sentece
     */
    public static String requestSentece(){
        //Here I get the input the user
        Scanner sc = new Scanner(System.in);
        String sentence =  sc.nextLine();
        System.out.println("The original sentence inputed was: "+sentence);
        return sentence;
    }

    /**
     * Here I will receive a String with sentence and become to List of String
     * @param sentence
     * @return
     */
    public static List<String> responseArrayOfWords(String sentence){
        List<String> words = new ArrayList<>();
        for (char c : sentence.toCharArray()){
            words.add(String.valueOf(c));
        }
        System.out.println("The original array the words is: "+words);
        return words;
    }

    /**
     * Here I will sorted List and them I will build the new String with List String received.
     * @param words
     */
    public static void sortedArrayOfWords(List<String> words){
        words = words.stream().sorted((l1, l2) -> l1.compareTo(l2)).toList();
        System.out.println("Sorted Array: "+words);
        // Use the stringbuild because is mutable
        StringBuilder b = new StringBuilder();
        for(String c : words){
            b.append(c);
        }
        String newWord = String.valueOf(b);
        System.out.println("newWord: : "+newWord);
    }
}