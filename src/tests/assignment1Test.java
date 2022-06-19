package tests;

import assignments.assignment1.Main;
import java.util.HashMap;

/**
 * Just bored to type some Units
 * There is kind of test-system
 *
 * List of catalans numbers via link
 *
 * https://oeis.org/A000108
 * */
public class assignment1Test {

    /**
     * Just copied list of numbs out of encyclopedia
     * */
    private static final String catalansList = "1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, " +
            "2674440, 9694845, 35357670, 129644790, 477638700, 1767263190, 6564120420, 24466267020, 91482563640," +
            " 343059613650, 1289904147324, 4861946401452, 18367353072152, 69533550916004, 263747951750360, " +
            "1002242216651368, 3814986502092304";

    /**
     * To store catalans numbs
     * */
    private static final HashMap<Integer, Long> catalansNumbers = new HashMap<>();

    public static void main(String[] args) {
        String[] source = prepareSource();
        for (int i = 0; i < source.length; i++) {
            test(i);
        }
    }

    /**
     * Feel map with expected results
     * */
    private static String[] prepareSource(){
        String[] numbsArray = catalansList.replace(" ", "").split(",");
        for (int i = 0; i < numbsArray.length; i++) {
            catalansNumbers.put(i, Long.parseLong(numbsArray[i]));
        }
        return numbsArray;
    }

    /**
     * UX :)
     * */
    private static void test(int i){
        System.out.println("___TEST_" + i + "__");
        System.out.println("Input: " + i);
        Main assignment1 = new Main(i);
        if (assignment1.getResult() == catalansNumbers.get(i)){
            System.out.println("    PASS: result = " + assignment1.getResult() + " expected: " + catalansNumbers.get(i));
        } else {
            System.out.println("!   FAILED: result = " + assignment1.getResult() + " expected: " + catalansNumbers.get(i));
        }
    }
}
