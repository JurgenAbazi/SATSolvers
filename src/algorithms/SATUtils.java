package algorithms;

import algorithms.cnf.Clause;
import algorithms.cnf.Formula;
import algorithms.cnf.Literal;

/**
 * Utility method for SAT checking and solving.
 */
public class SATUtils {
    /**
     * Checks whether the assignment satisfies the formula or not
     *
     * @param formula    The formula in conjunctive normal form
     * @param assignment The candidate assignment for the formula
     * @return Is the formula satisfied
     */
    public static boolean checkAssignment(Formula formula, boolean[] assignment) {
        for (Clause clause : formula.getClauses()) {
            boolean clauseValue = false;
            for (Literal literal : clause.getLiterals()) {
                boolean literalAssignment = assignment[literal.getAtom().getVar() - 1];
                if ((!literal.isNegated() && literalAssignment) || (literal.isNegated() && !literalAssignment)) {
                    clauseValue = true;
                    break;
                }
            }

            if (!clauseValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the formula is an instance of 2-SAT or not
     *
     * @param formula The formula in conjunctive normal form
     * @return Is the formula 2-SAT
     */
    public static boolean is2SAT(Formula formula) {
        for (Clause clause : formula.getClauses()) {
            if (clause.getNumberOfLiterals() != 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the formula is an instance of Horn-SAT or not
     *
     * @param formula The formula in conjunctive normal form
     * @return Is the formula Horn-SAT
     */
    public static boolean isHornSAT(Formula formula) {
        for (Clause clause : formula.getClauses()) {
            if (clause.getPositiveLiteralsCounter() > 1) {
                return false;
            }
        }
        return true;
    }
}
