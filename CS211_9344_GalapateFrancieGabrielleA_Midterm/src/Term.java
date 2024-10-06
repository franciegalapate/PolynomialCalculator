import Exceptions.NegativeExponentException;
import Exceptions.ZeroCoefficientException;

/**
 * A class to represent a term in an algebraic expression. A term consists of
 * an integer coefficient and a non-negative integer exponent.
 *
 * @author Francie Galapate
 */

public class Term {
    private int coefficient, exponent;

    /**
     * A default constructor for a term without parameters.
     */
    public Term() {
        this.coefficient = 0;
        this.exponent = 0;
    }

    /**
     * A constructor for a term that includes a coefficient and a exponent.
     *
     * @param c The coefficient
     * @param e The exponent
     */
    public Term(int c, int e) throws NegativeExponentException, ZeroCoefficientException {
        if (e < 0) {
            throw new NegativeExponentException("Negative input. Try again.");
        } else if (c == 0) {
            throw new ZeroCoefficientException("Coefficient cannot be zero. Try again");
        } else {
            this.coefficient = c;
            this.exponent = e;
        }
    }

    /**
     * Mutator method for the coefficient.
     * @param c The coefficient
     */
    public void setCoefficient(int c) throws ZeroCoefficientException {
        this.coefficient = c;
    }

    /**
     * Mutator method for the exponent.
     * @param e The exponent
     */
    public void setExponent(int e) throws NegativeExponentException{
        this.exponent = e;
    }

    /**
     * Accessor method for the coefficient.
     * @return The coefficient
     */
    public int getCoefficient() {
        return coefficient;
    }

    /**
     * Accessor method for the exponent
     * @return The exponent
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * Overrides the toString method and returns a term in string form
     *  ex. (5, 2) == 5x^2
     * @return A term in string form.
     */

    public String toString() {
        String term = "";

        if (getCoefficient() == 1) { // If coefficient is 1...
            if (getExponent() == 1) { // ... and the exponent is 1
                term += "x"; // Print only the variable x
            } else if (getExponent() == 0) {
                term += getCoefficient();
            } else {
                term += "x^" + getExponent(); // ... otherwise, x^(exponent)
            }
        } else if (getExponent() == 1){ // If exponent is 1...
            term += getCoefficient() + "x"; // ... print only coefficient followed by the variable x
        } else if (getExponent() == 0) { // If exponent is 0...
            term += getCoefficient() + " "; // ... print only the coefficient
        } else {
            term += getCoefficient() + "x^" + getExponent(); // Else, print the coefficient(x)^(exponent)
        }
        return term;
    }

    /**
     * Compares this term to another term.
     * @param otherTerm the object to be compared
     * @return an integer either 1, 0 or -1
     */
    public int compareTo(Term otherTerm) {
        // Compare based on the exponent first
        if (this.exponent > otherTerm.exponent) {
            return 1;
        } else if (this.exponent < otherTerm.exponent) {
            return -1;
        } else { // exponents are the same, compare based on coefficient
            if (this.coefficient > otherTerm.coefficient) {
                return 1;
            } else if (this.coefficient < otherTerm.coefficient) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Compares this term to another term.
     * @param otherTerm the object to be compared
     * @return true if the terms are equal, and false if not.
     */
    public boolean equals(Term otherTerm) {
        if (this.toString().compareTo(otherTerm.toString()) == 0) {
            return true;
        }
        return false;
    }
}