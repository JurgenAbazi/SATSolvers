package algorithms;

import algorithms.cnf.Clause;
import algorithms.cnf.Formula;
import algorithms.cnf.Literal;
import algorithms.cnf.Variable;
import algorithms.cnf.exceptions.UnsatisfiableFormulaException;

/**
 * Class containing a Horn-SAT solver.
 */
public class HornSAT {
    /**
     * Computes a satisfying assignment for a Horn-SAT formula.
     *
     * @param formula The formula in 2-SAT Conjunctive Normal Form.
     * @return A satisfying assignment of the variables.
     * @throws UnsatisfiableFormulaException No satisfiable solution exist.
     */
     public static boolean[] solveHornSAT(Formula formula) throws UnsatisfiableFormulaException {
        boolean[] solution = new boolean[formula.getNumberOfVariables()];

        while (!formula.getEmptyImplications().isEmpty()) {
            Variable variable = formula.removeEmptyImplication();
            solution[variable.getVar() - 1] = true;
            for (Clause clause : variable.getClausesContainingNegation()) {
                if (clause.getNumberOfLiterals() == 1) {
                    throw new UnsatisfiableFormulaException("No satisfying assignments exist for the Horn-SAT.");
                }
                clause.removeLiteral(new Literal(variable, true));
                if (clause.isEmptyImplication() && !clause.getFirst().getAtom().equals(variable)) {
                    formula.addEmptyImplication(clause.getFirst().getAtom());
                }
            }
        }

        return solution;
    }

}
