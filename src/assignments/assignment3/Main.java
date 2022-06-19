package assignments.assignment3;

import java.math.BigInteger;

/**
 * Simple two-method class to find sum of digits of !100
 */
public class Main {

    /**
     * Finding factorial at first
     * @param number numb to find factorial
     * @return factorial value in String representation
     * */
    public static String factorial(int number) {
        BigInteger result = BigInteger.valueOf(1);
        for (long factor = 2; factor <= number; factor++) { //factor 2 bc 1 * 1 = 1 anyway :)
            result = result.multiply(BigInteger.valueOf(factor));
        }
        return String.valueOf(result);
    }

    /**
     * Finally sum
     * Find sum of all digits using walk through factorial digits array
     * @param factorial String of value to find sum of digits
     * @return sum of digits
     *
     * */
    public static int findSum(String factorial){
        char[] numb = factorial.toCharArray();
        int sum = 0;
        for (char c : numb) {
            sum += Integer.parseInt(String.valueOf(c));
        }
        return sum;
    }

    public static void main(String[] args) {
        int problem = 100;
        String factorial = factorial(problem);
        int solving = findSum(factorial);
        System.out.println(solving);
    }
}
