import funcional.CaculateFuncional;

import java.util.*;

public class Main {
    private static List<Integer> integers;

    public static void main(String[] args) {
        int start = 1;
        int end = 20;
        //Here start the System, first prepare List with the range, after I will calling in a functional to make a count.
        checkDivision(preparList(start, end), (v) -> v % 3 == 0);
    }

    /**
     * Here I Will check the counts and print results
     * @param numbers
     * @param calculate
     */
    private static void checkDivision(List<Integer> numbers, CaculateFuncional<Integer> calculate) {
        numbers.forEach(n -> {
            boolean disibleBy3 = calculate.isDivisibleBy(n);
            boolean disibleBy5 = n % 5 == 0;
            boolean disibleBy3And5 = (n % 3 == 0) && (n % 5 == 0);
            if (disibleBy3) System.out.println("this numbers is divisible for 3: " + n + " so, I'll print A");
            if (disibleBy5) System.out.println("this numbers is divisible for 5: " + n + " so, I'll print B");
            if (disibleBy3And5) System.out.println("this numbers is divisible for 3 and 5: " + n + " so, I'll print C");
        });
    }

    /**
     * Here I will prepare the List of range the 1 to 20, also I will print before and after that I builded the list.
     * @param start
     * @param end
     * @return List<Integer>
     */
    public static List<Integer> preparList(int start, int end) {
        System.out.println("create one list that start: " + start + " and finish: " + end);
        List<Integer> number = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            number.add(i);
        }
        System.out.println("The list has size equal: " + number.size() + " they are: " + number);
        return number;
    }
}
