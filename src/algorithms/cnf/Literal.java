package algorithms.cnf;

/**
 * Class representing a Literal
 */
public class Literal {
    /**
     * The atomic variable of the Literal.
     */
    private final Variable atom;

    /**
     * Variable storing if the Literal is negated or not.
     */
    private final boolean negated;

    /**
     * Constructor used by 2-SAT.
     * Creates a new Literal from an int. Negated symbol is represented by minus (-).
     *
     * @param literal The literal as int.
     */
    public Literal(int literal) {
        this(new Variable(literal), literal < 0);
    }

    /**
     * Constructor.
     *
     * @param atom    The atomic variable.
     * @param negated Is the literal negated.
     */
    public Literal(Variable atom, boolean negated) {
        this.atom = atom;
        this.negated = negated;
    }

    /**
     * Gives the literal in int form. Negative if negated, positive otherwise.
     *
     * @return The literal as an int.
     */
    public int getAsInt() {
        return negated ? -atom.getVar() : atom.getVar();
    }

    /**
     * Gives the atomic variable.
     *
     * @return Atomic variable.
     */
    public Variable getAtom() {
        return atom;
    }

    /**
     * Gives true if the literal is negated. False otherwise.
     *
     * @return Is the literal negated.
     */
    public boolean isNegated() {
        return negated;
    }

    /**
     * Returns the Literal in a readable form.
     *
     * @return Literal as String.
     */
    @Override
    public String toString() {
        return String.valueOf(getAsInt());
    }

    /**
     * Two literals are equal if they have the same atom variable value and negation.
     *
     * @param o The object being compared.
     * @return If the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Literal literal = (Literal) o;
        return negated == literal.negated && atom.equals(literal.atom);
    }

    /**
     * Two literals that have the same negation and atom return the same hashcode.
     * Used by 2-SAT for retrieving from the SCC map by creating new Literals of same properties.
     *
     * @return The hashcode of the Literal.
     */
    @Override
    public int hashCode() {
        int result = atom.hashCode();
        result = 31 * result + (negated ? 1 : 0);
        return result;
    }
}
