package algorithms.cnf;

import java.util.*;

/**
 * Class representing a Conjunctive Normal Form formula
 */
public class Formula {
    /**
     * Set of clauses in the formula.
     */
    private final Set<Clause> clauses;

    /**
     * Queue of variables that are present as an empty implication in a Horn formula.
     * Used by the HornSAT algorithm.
     */
    private final Queue<Variable> emptyImplications;

    /**
     * Number of variable in the formula.
     */
    private final int numberOfVariables;

    /**
     * Constructor.
     *
     * @param numberOfVariables Number of variables in the formula.
     */
    public Formula(int numberOfVariables) {
        clauses = new HashSet<>();
        emptyImplications = new LinkedList<>();
        this.numberOfVariables = numberOfVariables;
    }

    /**
     * Adds a clause to the formula.
     * If it corresponds to an empty implication, the variable is added to the empty implications queue.
     *
     * @param clause The clause being added.
     */
    public void addClause(Clause clause) {
        clauses.add(clause);
        if (clause.isEmptyImplication()) {
            addEmptyImplication(clause.getFirst().getAtom());
        }
    }

    /**
     * Adds a variable to the empty implications queue if it is not already present.
     *
     * @param atom The variable being added to the queue.
     */
    public void addEmptyImplication(Variable atom) {
        if (!emptyImplications.contains(atom)) {
            emptyImplications.add(atom);
        }
    }

    /**
     * Returns a set of clauses present in the formula.
     *
     * @return The set of clauses.
     */
    public Set<Clause> getClauses() {
        return clauses;
    }

    /**
     * Returns the total number of variables in the formula.
     *
     * @return Number of variables.
     */
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    /**
     * Returns the queue of variables that are present in an empty implication clause.
     * @return Empty implication variables queue.
     */
    public Queue<Variable> getEmptyImplications() {
        return emptyImplications;
    }

    /**
     * Removes the first variable from the empty implications queue.
     *
     * @return The removed variable.
     */
    public Variable removeEmptyImplication() {
        return emptyImplications.remove();
    }

}
