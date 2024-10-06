/**
 * This class represents a Node in a linked list that holds a Term object and a reference to the next Node in the list.
 * It is primarily used to represent polynomials where each node stores a term of the polynomial.
 *
 * @author Francie Galapate
 */
public class Node {
    // Instance variables
    private Term term; // The term stored in this node
    private Node next; // Reference to the next node in the list

    /**
     * Default constructor.
     * Initializes the term and next to null.
     */
    public Node() {
        this.term = null;
        this.next = null;
    }

    /**
     * Constructor that initializes the node with a Term and a reference to the next node.
     *
     * @param term The term to be stored in this node.
     * @param next The reference to the next node in the list.
     */
    public Node(Term term, Node next) {
        this.term = term;
        this.next = next;
    }

    /**
     * Getter for the term stored in this node.
     *
     * @return The term stored in this node.
     */
    public Term getTerm() {
        return this.term;
    }

    /**
     * Setter for the term stored in this node.
     *
     * @param term The term to set for this node.
     */
    public void setTerm(Term term) {
        this.term = term;
    }

    /**
     * Getter for the reference to the next node in the list.
     *
     * @return The reference to the next node.
     */
    public Node getNext() {
        return this.next;
    }

    /**
     * Setter for the reference to the next node in the list.
     *
     * @param next The next node to link to this node.
     */
    public void setNext(Node next) {
        this.next = next;
    }
}