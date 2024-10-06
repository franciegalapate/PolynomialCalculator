import Exceptions.NegativeExponentException;
import Exceptions.ZeroCoefficientException;
import java.util.Scanner;

/**
 * PolynomialApp is a console application that allows users to input a polynomial
 * and perform various operations such as addition, subtraction, multiplication,
 * and division with another polynomial. The application will repeatedly display
 * a menu of operations until the user chooses to quit.
 *
 * @author Francie Galapate
 */
public class PolynomialApp {

    /**
     * The main method initializes the application and prompts the user to enter a
     * polynomial. It then displays a menu for the user to choose between polynomial
     * operations such as adding, subtracting, multiplying, and dividing polynomials.
     *
     * @param args Command-line arguments (not used).
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     * @throws ZeroCoefficientException  If a term with a zero coefficient is encountered.
     */
    public static void main(String[] args) throws NegativeExponentException, ZeroCoefficientException {
        Scanner scanner = new Scanner(System.in);
        int degree, coefficient;

        Polynomial newPolynomial = enterPolynomial(scanner);
        System.out.println("The polynomial you have entered is: ");
        newPolynomial.displayPolynomial();

        while (true) {
            System.out.println("\nPolynomial Operations Menu");
            System.out.println("-------------------------------");
            System.out.println("1. Add Another Polynomial");
            System.out.println("2. Subtract Another Polynomial");
            System.out.println("3. Multiply with Another Polynomial");
            System.out.println("4. Divide by Another Polynomial");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.next();

            switch (choice) {
                case "1":
                    // Add another polynomial to the current polynomial
                    Polynomial p1 = enterPolynomial(scanner);
                    Polynomial sum = newPolynomial.add(p1);
                    System.out.println("The result of adding the two polynomials is: ");
                    sum.displayPolynomial();
                    break;

                case "2":
                    // Subtract another polynomial from the current polynomial
                    Polynomial p2 = enterPolynomial(scanner);
                    Polynomial difference = newPolynomial.subtract(p2);
                    System.out.println("The result of subtracting the two polynomials is: ");
                    difference.displayPolynomial();
                    break;

                case "3":
                    // Multiply the current polynomial with another polynomial
                    Polynomial p3 = enterPolynomial(scanner);
                    Polynomial product = newPolynomial.multiply(p3);
                    System.out.println("The result of multiplying the two polynomials is: ");
                    product.displayPolynomial();
                    break;

                case "4":
                    // Divide the current polynomial by another polynomial
                    Polynomial p4 = enterPolynomial(scanner);
                    Polynomial quotient = newPolynomial.divide(p4);
                    System.out.println("The result of dividing the two polynomials is: ");
                    quotient.displayPolynomial();
                    break;

                case "5":
                    // Quit the application
                    System.out.println("Exiting the application.");
                    return;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    /**
     * Prompts the user to enter a polynomial by specifying its degree and the
     * coefficients of each term.
     *
     * @param scanner The Scanner object used to read user input.
     * @return A Polynomial object representing the polynomial entered by the user.
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     * @throws ZeroCoefficientException  If a term with a zero coefficient is encountered.
     */
    private static Polynomial enterPolynomial(Scanner scanner) throws NegativeExponentException, ZeroCoefficientException {
        System.out.println("Enter the degree of the polynomial: ");
        int degree = scanner.nextInt();
        Polynomial polynomial = new Polynomial();

        // Enter coefficients for each term
        for (int i = degree; i >= 0; i--) {
            System.out.println("Enter the coefficient for the term with x^" + i + ":");
            int coefficient = scanner.nextInt();
            if (coefficient != 0) {
                polynomial.insertTerm(new Term(coefficient, i));
            }
        }
        return polynomial;
    }
}