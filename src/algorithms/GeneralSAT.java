package algorithms;

import algorithms.cnf.*;
import algorithms.cnf.exceptions.UnsatisfiableFormulaException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class containing a General SAT solver implemented using DPLL algorithm.
 */
public class GeneralSAT {
    /**
     * Computes a satisfying assignment for a General CNF SAT formula.
     *
     * @param formula The formula in Conjunctive Normal Form.
     * @return A satisfying assignment of the variables.
     * @throws UnsatisfiableFormulaException No satisfiable solution exist.
     */
    public static boolean[] solveGeneralSAT(Formula formula) throws UnsatisfiableFormulaException {
        boolean[] solution = new boolean[formula.getNumberOfVariables()];

        List<Variable> variables = new ArrayList<>();
        for (int i = 1; i <= formula.getNumberOfVariables(); i++) {
            variables.add(new Variable(i));
        }

        if (dpll(formula, variables, new Model(), solution)) {
            return solution;
        } else {
            throw new UnsatisfiableFormulaException("No satisfying assignments exists for the SAT Formula.");
        }
    }

    /**
     * The recursive Davis–Putnam–Logemann–Loveland algorithm implementation.
     *
     * @param formula   The formula in CNF that is being solved.
     * @param variables The list of variables with no assigned values.
     * @param model     A model of some variable and their assignments, under which the formula is tested.
     * @param solution  the array where the solution will be written.
     * @return Is the formula solvable under current model.
     */
    public static boolean dpll(Formula formula, List<Variable> variables, Model model, boolean[] solution) {
        if (everyClauseTrueUnderModel(formula, model)) {
            for (Variable variable : model.getAssignments().keySet()) {
                solution[variable.getVar() - 1] = model.getAssignments().get(variable);
            }
            return true;
        }
        if (someClauseFalseUnderModel(formula, model)) {
            return false;
        }

        VariableAssignment pureVariable = findPureVariable(formula, variables, model);
        if (pureVariable != null) {
            List<Variable> unassigned = removeFromList(variables, pureVariable.getVariable());
            Model union = model.addToModelAndCopy(pureVariable.getVariable(), pureVariable.getAssignment());
            return dpll(formula, unassigned, union, solution);
        }

        VariableAssignment unitClauseVariable = findUnitClause(formula, model);
        if (unitClauseVariable != null) {
            List<Variable> unassigned = removeFromList(variables, unitClauseVariable.getVariable());
            Model union = model.addToModelAndCopy(unitClauseVariable.getVariable(), unitClauseVariable.getAssignment());
            return dpll(formula, unassigned, union, solution);
        }

        Variable first = variables.get(0);
        List<Variable> rest = removeFromList(variables, first);

        Model unionTrue = model.addToModelAndCopy(first, true);
        Model unionFalse = model.addToModelAndCopy(first, false);
        return dpll(formula, rest, unionTrue, solution) || dpll(formula, rest, unionFalse, solution);
    }

    /**
     * Checks if the formula is true under the current values of the model.
     *
     * @param formula The formula in CNF that is being solved.
     * @param model   A model of some variable and their assignments, under which the formula is tested.
     * @return Is the formula true.
     */
    private static boolean everyClauseTrueUnderModel(Formula formula, Model model) {
        return formula.getClauses()
                .stream()
                .map(model::determineClauseValue)
                .noneMatch(value -> value == null || !value);
    }

    /**
     * Checks if the formula is false under the current values of the model.
     *
     * @param formula The formula in CNF that is being solved.
     * @param model   A model of some variable and their assignments, under which the formula is tested.
     * @return Is the formula false.
     */
    private static boolean someClauseFalseUnderModel(Formula formula, Model model) {
        return formula.getClauses()
                .stream()
                .map(model::determineClauseValue)
                .anyMatch(value -> value != null && !value);
    }

    /**
     * Finds a DPLL pure variable and returns it with the proper assignment.
     *
     * @param formula   The formula in CNF that is being solved.
     * @param variables The list of variables with no assigned values.
     * @param model     A model of some variable and their assignments, under which the formula is tested.
     * @return A pure variable and its assignment.
     */
    public static VariableAssignment findPureVariable(Formula formula, List<Variable> variables, Model model) {
        VariableAssignment pureVariable = null;

        Set<Variable> variablesToKeep = new HashSet<>(variables);
        Set<Variable> candidatePurePositive = new HashSet<>();
        Set<Variable> candidatePureNegative = new HashSet<>();

        for (Clause clause : formula.getClauses()) {
            Boolean value = model.determineClauseValue(clause);
            if (value != null && value) {
                continue;
            }

            clause.getPositiveVariables()
                    .stream()
                    .filter(variablesToKeep::contains)
                    .forEach(candidatePurePositive::add);

            clause.getNegativeVariables()
                    .stream()
                    .filter(variablesToKeep::contains)
                    .forEach(candidatePureNegative::add);
        }

        for (Variable variable : variablesToKeep) {
            if (candidatePurePositive.contains(variable) && candidatePureNegative.contains(variable)) {
                candidatePurePositive.remove(variable);
                candidatePureNegative.remove(variable);
            }
        }

        if (candidatePurePositive.size() > 0) {
            pureVariable = new VariableAssignment(candidatePurePositive.iterator().next(), true);
        } else if (candidatePureNegative.size() > 0) {
            pureVariable = new VariableAssignment(candidatePureNegative.iterator().next(), false);
        }

        return pureVariable;
    }


    /**
     * Finds a variable in a DPLL unit clause and returns it with the proper assignment.
     *
     * @param formula The formula in CNF that is being solved.
     * @param model   The current model of solutions.
     * @return The variable and its assignment which makes the unit clause true.
     */
    public static VariableAssignment findUnitClause(Formula formula, Model model) {
        VariableAssignment unitClauseVariable = null;

        for (Clause clause : formula.getClauses()) {
            if (model.determineClauseValue(clause) != null) {
                continue;
            }

            Literal unitClauseLiteral = null;
            if (clause.getNumberOfLiterals() == 1) {
                unitClauseLiteral = clause.getFirst();
            } else {
                for (Literal literal : clause.getLiterals()) {
                    if (model.getAssignments().get(literal.getAtom()) != null) {
                        continue;
                    }
                    if (unitClauseLiteral == null) {
                        unitClauseLiteral = literal;
                    } else {
                        unitClauseLiteral = null;
                        break;
                    }

                }
            }

            if (unitClauseLiteral != null) {
                int variable = unitClauseLiteral.getAtom().getVar();
                boolean isPositive = !unitClauseLiteral.isNegated();
                unitClauseVariable = new VariableAssignment(new Variable(variable), isPositive);
                break;
            }
        }

        return unitClauseVariable;
    }

    /**
     * Returns a new list with the variable removed.
     *
     * @param variables The list of unassigned variables.
     * @param variable  The variable being removed from the list.
     * @return The new list of variables.
     */
    private static List<Variable> removeFromList(List<Variable> variables, Variable variable) {
        return variables.stream()
                .filter(symbol -> !variable.equals(symbol))
                .collect(Collectors.toCollection(() -> new ArrayList<>(variables.size())));
    }
}
