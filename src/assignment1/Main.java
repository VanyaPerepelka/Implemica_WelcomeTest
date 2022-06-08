package assignment1;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ivanov I.
 *
 * This class takes as input a number (N) equal to the number of pairs of brackets of mathematical expressions.
 * The result of the program is the number of all possible correct combinations, built from N mathematical brackets
 * Example: n = 1 -> 1 = () -> result = 1 : "((" / "))" / ")(" is invalid
 *------------
 * This is one of Catalan's numbers application.
 * Here is the implementation of Nth Catalan's search based on binomial coeficcient in O(n) time
 *
 *  */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an positive integer number: ");

        try {
            int input = scanner.nextInt();
            System.out.println("Total number of correct combinations: " + calculateCorrectCases(input));

        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
        }
    }

    /**
     *
     * @param input - order of wanted catalans numb
     * @return input-th order Catalan's number */
    private static long calculateCorrectCases(int input) {
         if (input > 0){
            return getBinomialCoefficient(2L * input, input) / (input + 1); // calculating 2nCn / (n+1)
        } else { //for all <= 0 numbs res is 1
            return 1;
        }
    }

    /**
     * Returns value of binomial coeff C(n, k)
     *
     * @param n - input * 2
     * @param k  input
     *
     * @return binom coef
     * */
    private static long getBinomialCoefficient(long n, long k){
        long coefficient = 1;

        for (int i = 0; i < k; ++i) { //calculating nCk
            coefficient *= (n - i);
            coefficient /= (i + 1);
        }

        return coefficient;
    }
}
