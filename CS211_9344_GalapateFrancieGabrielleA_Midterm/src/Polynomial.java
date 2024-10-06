import Exceptions.NegativeExponentException;
import Exceptions.ZeroCoefficientException;

/**
 * The Polynomial class represents a polynomial where each term is stored as a Node in a linked list.
 * It provides functionality for polynomial operations such as insertion, sorting, display,
 * addition, subtraction, multiplication, and division.
 *
 * @author Francie Galapate
 */
public class Polynomial {
    private Node head;  // The head node of the polynomial linked list
    public int size;   // The number of terms in the polynomial

    /**
     * Default constructor that initializes an empty polynomial.
     */
    public Polynomial() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Constructor that initializes the polynomial with a single term.
     *
     * @param term The term to initialize the polynomial with.
     * @throws NegativeExponentException if the term has a negative exponent.
     */
    public Polynomial(Term term) throws NegativeExponentException {
        this.head = new Node(term, null);
        this.size = 1;
    }

    /**
     * Inserts a new term into the polynomial. The term is added at the end of the list.
     *
     * @param term The term to be inserted into the polynomial.
     */
    public void insertTerm(Term term) {
        Node newTerm = new Node(term, null);
        if (head == null) {
            head = newTerm;
        } else {
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newTerm);
        }
        size++;
    }

    /**
     * Sorts the polynomial terms in descending order based on the exponent of each term.
     */
    public void sortPolynomial() {
        if (head == null || head.getNext() == null) {
            return; // No need to sort if the list is empty or has only one term
        }

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.getNext() != null) {
                Node nextNode = current.getNext();
                if (current.getTerm().getExponent() < nextNode.getTerm().getExponent()) {
                    Term tempTerm = current.getTerm();
                    current.setTerm(nextNode.getTerm());
                    nextNode.setTerm(tempTerm);
                    swapped = true;
                }
                current = current.getNext();
            }
        } while (swapped);
    }

    /**
     * Displays the polynomial in standard form by sorting terms in descending order of exponents.
     * Prints "0" if the polynomial is empty.
     */
    public void displayPolynomial() {
        sortPolynomial();

        if (head == null) {
            System.out.println("0");
            return;
        }

        StringBuilder result = new StringBuilder();
        Node current = head;

        while (current != null) {
            Term term = current.getTerm(); // Get the term from the current node

            // If it's the first term, append it directly
            if (result.isEmpty()) {
                result.append(term.toString());
            } else {
                // Check the coefficient of the current term for formatting
                if (term.getCoefficient() > 0) {
                    result.append(" + ").append(term.toString());
                } else {
                    result.append(" ").append(term.toString());
                }
            }
            current = current.getNext(); // Move to the next node
        }
        System.out.println(result.toString()); // Print the polynomial
    }

    /**
     * Adds two polynomials and returns the result as a new Polynomial.
     *
     * @param p The polynomial to be added.
     * @return The sum of the two polynomials.
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     * @throws ZeroCoefficientException  If a term with zero coefficient is encountered.
     */
    public Polynomial add(Polynomial p) throws NegativeExponentException, ZeroCoefficientException {
        Polynomial result = new Polynomial();

        Node currentPointer = this.head;
        Node otherPointer = p.head;

        while (currentPointer != null && otherPointer != null) {
            if (currentPointer.getTerm().getExponent() == otherPointer.getTerm().getExponent()) {
                // Sum the coefficients
                int termSum = currentPointer.getTerm().getCoefficient() + otherPointer.getTerm().getCoefficient();

                // Only insert if the sum is not zero
                if (termSum != 0) {
                    result.insertTerm(new Term(termSum, currentPointer.getTerm().getExponent()));
                }
                currentPointer = currentPointer.getNext();
                otherPointer = otherPointer.getNext();
            } else if (currentPointer.getTerm().getExponent() > otherPointer.getTerm().getExponent()) {
                result.insertTerm(currentPointer.getTerm());
                currentPointer = currentPointer.getNext();
            } else {
                result.insertTerm(otherPointer.getTerm());
                otherPointer = otherPointer.getNext();
            }
        }

        // Add remaining terms from either polynomial
        while (currentPointer != null) {
            result.insertTerm(currentPointer.getTerm());
            currentPointer = currentPointer.getNext();
        }

        while (otherPointer != null) {
            result.insertTerm(otherPointer.getTerm());
            otherPointer = otherPointer.getNext();
        }

        return result;
    }

    /**
     * Subtracts the provided polynomial from the current polynomial.
     *
     * @param p The polynomial to subtract.
     * @return The result of the subtraction.
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     * @throws ZeroCoefficientException  If a term with zero coefficient is encountered.
     */
    public Polynomial subtract(Polynomial p) throws NegativeExponentException, ZeroCoefficientException {
        Polynomial negatedPolynomial = new Polynomial();
        Node otherPointer = p.head;

        while (otherPointer != null) {
            int negatedCoefficient = -otherPointer.getTerm().getCoefficient();
            int exponent = otherPointer.getTerm().getExponent();
            negatedPolynomial.insertTerm(new Term(negatedCoefficient, exponent));
            otherPointer = otherPointer.getNext();
        }

        return this.add(negatedPolynomial);
    }

    /**
     * Multiplies two polynomials and returns the result as a new Polynomial.
     *
     * @param p The polynomial to multiply with.
     * @return The product of the two polynomials.
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     * @throws ZeroCoefficientException  If a term with zero coefficient is encountered.
     */
    public Polynomial multiply(Polynomial p) throws NegativeExponentException, ZeroCoefficientException {
        Polynomial result = new Polynomial();

        Node currentPointer = this.head;

        while (currentPointer != null) {
            Node otherPointer = p.head;

            while (otherPointer != null) {
                int termProductCoefficient = currentPointer.getTerm().getCoefficient() * otherPointer.getTerm().getCoefficient();
                int termSumExponent = currentPointer.getTerm().getExponent() + otherPointer.getTerm().getExponent();

                Term productTerm = new Term(termProductCoefficient, termSumExponent);
                result.insertTerm(productTerm);

                otherPointer = otherPointer.getNext();
            }

            currentPointer = currentPointer.getNext();
        }

        result = combineLikeTerms(result);
        return result;
    }

    /**
     * Divides the current polynomial by the provided polynomial and returns the quotient.
     *
     * @param p The divisor polynomial.
     * @return The quotient of the division.
     * @throws ZeroCoefficientException If division by zero is attempted.
     * @throws NegativeExponentException If a term with a negative exponent is encountered.
     */
    public Polynomial divide(Polynomial p) throws ZeroCoefficientException, NegativeExponentException {
        Polynomial result = new Polynomial();
        Node divisorPointer = p.head;
        Node dividendPointer = this.head;

        if (divisorPointer == null || divisorPointer.getTerm().getCoefficient() == 0) {
            throw new IllegalArgumentException("Cannot divide by zero polynomial");
        }

        while (dividendPointer != null && dividendPointer.getTerm().getExponent() >= divisorPointer.getTerm().getExponent()) {
            int leadingCoefficient = dividendPointer.getTerm().getCoefficient() / divisorPointer.getTerm().getCoefficient();
            int leadingExponent = dividendPointer.getTerm().getExponent() - divisorPointer.getTerm().getExponent();
            Term leadingTerm = new Term(leadingCoefficient, leadingExponent);
            result.insertTerm(leadingTerm);

            Node tempDivisorPointer = divisorPointer;
            Node currentDividendPointer = this.head;

            while (tempDivisorPointer != null) {
                int productCoefficient = leadingTerm.getCoefficient() * tempDivisorPointer.getTerm().getCoefficient();
                int productExponent = leadingTerm.getExponent() + tempDivisorPointer.getTerm().getExponent();

                while (currentDividendPointer != null) {
                    if (currentDividendPointer.getTerm().getExponent() == productExponent) {
                        int newCoeff = currentDividendPointer.getTerm().getCoefficient() - productCoefficient;
                        if (newCoeff == 0) {
                            removeTerm(currentDividendPointer);
                        } else {
                            currentDividendPointer.getTerm().setCoefficient(newCoeff);
                        }
                        break;
                    }
                    currentDividendPointer = currentDividendPointer.getNext();
                }

                tempDivisorPointer = tempDivisorPointer.getNext();
            }

            dividendPointer = this.head;
        }

        return result;
    }

    /**
     * Removes a specific term from the polynomial based on the given node reference.
     *
     * @param termNode The node representing the term to be removed.
     */
    private void removeTerm(Node termNode) {
        if (this.head == termNode) {
            this.head = this.head.getNext();
        } else {
            Node prev = this.head;
            while (prev.getNext() != termNode) {
                prev = prev.getNext();
            }
            prev.setNext(termNode.getNext());
        }
    }

    /**
     * Combines like terms in the provided polynomial by merging terms with the same exponent.
     *
     * @param poly The polynomial to combine like terms in.
     * @return The polynomial with combined like terms.
     * @throws ZeroCoefficientException If a term with zero coefficient is encountered.
     */
    private Polynomial combineLikeTerms(Polynomial poly) throws ZeroCoefficientException {
        Polynomial combined = new Polynomial();
        Node currentPointer = poly.head;

        while (currentPointer != null) {
            Node tempPointer = combined.head;
            boolean isCombined = false;

            while (tempPointer != null) {
                if (tempPointer.getTerm().getExponent() == currentPointer.getTerm().getExponent()) {
                    int newCoefficient = tempPointer.getTerm().getCoefficient() + currentPointer.getTerm().getCoefficient();
                    tempPointer.getTerm().setCoefficient(newCoefficient);

                    isCombined = true;

                    if (newCoefficient == 0) {
                        combined.removeTerm(tempPointer.getTerm().getExponent());
                    }
                    break;
                }
                tempPointer = tempPointer.getNext();
            }

            if (!isCombined) {
                combined.insertTerm(currentPointer.getTerm());
            }

            currentPointer = currentPointer.getNext();
        }

        return combined;
    }

    /**
     * Removes a term from the polynomial based on the exponent value.
     *
     * @param exponent The exponent of the term to remove.
     */
    private void removeTerm(int exponent) {
        if (head == null) return;

        if (head.getTerm().getExponent() == exponent) {
            head = head.getNext();
            size--;
            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.getTerm().getExponent() == exponent) {
                if (previous != null) {
                    previous.setNext(current.getNext());
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }
}