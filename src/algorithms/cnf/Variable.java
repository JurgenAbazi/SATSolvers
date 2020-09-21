package algorithms.cnf;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a variable. The atomic building block of SAT formula.
 */
public class Variable {
    /**
     * The variable numbering.
     */
    private int var;

    /**
     * Set of all clauses containing the variable as a negated literal.
     * Used by the HornSAT algorithm.
     */
    private Set<Clause> clausesContainingNegation;

    /**
     * Constructor.
     *
     * @param var The variable numbering.
     */
    public Variable(int var) {
        this.var = Math.abs(var);
        clausesContainingNegation = new HashSet<>();
    }

    /**
     * Returns the variable numbering value.
     *
     * @return The var int value.
     */
    public int getVar() {
        return var;
    }

    /**
     * Method that returns the clauses containing the variable as a negated literal.
     *
     * @return Clauses containing negated variable list.
     */
    public Set<Clause> getClausesContainingNegation() {
        return clausesContainingNegation;
    }

    /**
     * Adds the clause to the negation list.
     *
     * @param clause The clause containing the negation.
     */
    public void addClause(Clause clause) {
        clausesContainingNegation.add(clause);
    }

    /**
     * Two variables that have the same var value have the same hashcode.
     *
     * @return The hashcode of the Variable.
     */
    @Override
    public int hashCode() {
        return var;
    }

    /**
     * Two variable are equal if they have the same var number.
     *
     * @param o The object being compared.
     * @return If the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        Variable l = (Variable) o;
        return l.getVar() == var;
    }
}
