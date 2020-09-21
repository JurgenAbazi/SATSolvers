package algorithms.cnf;

import java.util.*;

/**
 * Class representing a Clause, a disjunction of Literals.
 */
public class Clause {
    /**
     * The set of literals in the clause.
     */
    private final Set<Literal> literals;

    /**
     * Number of positive literals in the clause.
     */
    private int positiveLiteralsCounter;

    /**
     * Stores if the clause is a tautology (always true) or not.
     * Used by DPLL algorithm.
     */
    private Boolean tautology;

    /**
     * The set of variable which appear as positive in the clause.
     * Used by DPLL algorithm.
     */
    private final Set<Variable> positiveVariables;

    /**
     * The set of variable which appear as negative in the clause.
     * Used by DPLL algorithm.
     */
    private final Set<Variable> negativeVariables;

    /**
     * Default constructor.
     */
    public Clause() {
        tautology = null;
        positiveLiteralsCounter = 0;
        literals = new HashSet<>();
        positiveVariables = new HashSet<>();
        negativeVariables = new HashSet<>();
    }

    /**
     * Adds a literal to the clause and to the other appropriate structures in the clause.
     *
     * @param literal The literal being added to the clause
     */
    public void addLiteral(Literal literal) {
        literals.add(literal);

        if (literal.isNegated()) {
            negativeVariables.add(literal.getAtom());
        } else {
            positiveLiteralsCounter++;
            positiveVariables.add(literal.getAtom());
        }
    }

    /**
     * Returns the set of literals.
     *
     * @return The literals.
     */
    public Set<Literal> getLiterals() {
        return literals;
    }

    /**
     * Returns the set of variables which appear as a positive literal in the clause.
     *
     * @return The positive variables set.
     */
    public Set<Variable> getPositiveVariables() {
        return positiveVariables;
    }

    /**
     * Returns the set of variables which appear as a negative literal in the clause.
     *
     * @return The negative variables set.
     */
    public Set<Variable> getNegativeVariables() {
        return negativeVariables;
    }

    /**
     * Returns the number of positive literals.
     *
     * @return Non-negated literals number.
     */
    public int getPositiveLiteralsCounter() {
        return positiveLiteralsCounter;
    }

    /**
     * Returns the number of literals.
     *
     * @return Literals number.
     */
    public int getNumberOfLiterals() {
        return literals.size();
    }

    /**
     * Return if the clause contains only 1 non-negated literal.
     *
     * @return Is the clause an empty implication.
     */
    public boolean isEmptyImplication() {
        return getNumberOfLiterals() == 1 && !getFirst().isNegated();
    }

    /**
     * Gives the first literal of the clause.
     *
     * @return First literal of the clause.
     */
    public Literal getFirst() {
        if (literals.size() < 1) {
            return null;
        }
        for (Literal literal : literals) {
            return literal;
        }
        return null;
    }

    /**
     * Gives the second literal of the clause. Used by 2-SAT.
     *
     * @return Second literal of the clause.
     */
    public Literal getSecond() {
        if (literals.size() < 2) {
            return null;
        }

        int i = 0;
        for (Literal literal : literals) {
            if (i == 1) {
                return literal;
            }
            i++;
        }
        return null;
    }

    /**
     * Computes if the current clause is a tautology or not.
     * Computes it only once and stores it in the instance variable.
     *
     * @return Is the clause a tautology.
     */
    public boolean isTautology() {
        if (tautology == null) {
            Set<Variable> intersection = new LinkedHashSet<>(positiveVariables);
            intersection.retainAll(negativeVariables);
            tautology = intersection.size() > 0;
        }
        return tautology;
    }

}
