package algorithms.cnf;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a partial model of solution.
 * Used in the DPLL algorithm for solving general SAT formulas.
 */
public class Model {
    /**
     * The variables and their assignments map.
     */
    private Map<Variable, Boolean> assignments;

    /**
     * Default Constructor.
     */
    public Model() {
        assignments = new HashMap<>();
    }

    /**
     * Returns the map of the variable and their assignments.
     *
     * @return Assignments Map.
     */
    public Map<Variable, Boolean> getAssignments() {
        return assignments;
    }

    /**
     * Adds a new variable and assignment to the model, and returns it as a new object.
     *
     * @param variable   The variable being added to the model.
     * @param assignment The assignment of the variable.
     * @return The new joined model.
     */
    public Model addToModelAndCopy(Variable variable, boolean assignment) {
        Model model = new Model();
        model.assignments.putAll(this.assignments);
        model.assignments.put(variable, assignment);
        return model;
    }

    /**
     * Determines the value of the clause under the current model.
     *
     * @param clause The clause being checked.
     * @return True or false or null if not determinable
     */
    public Boolean determineClauseValue(Clause clause) {
        Boolean clauseValueResult = null;

        if (clause.getNumberOfLiterals() == 0) {
            clauseValueResult = false;
        } else if (clause.isTautology()) {
            clauseValueResult = true;
        } else {
            boolean unassignedVariablesPresent = false;
            Boolean value;

            for (Variable positive : clause.getPositiveVariables()) {
                value = assignments.get(positive);
                if (value == null) {
                    unassignedVariablesPresent = true;
                } else if (value) {
                    clauseValueResult = true;
                    break;
                }
            }

            if (clauseValueResult == null) {
                for (Variable negative : clause.getNegativeVariables()) {
                    value = assignments.get(negative);
                    if (value == null) {
                        unassignedVariablesPresent = true;
                    } else if (!value) {
                        clauseValueResult = true;
                        break;
                    }
                }

                if (clauseValueResult == null && !unassignedVariablesPresent) {
                    clauseValueResult = false;
                }
            }
        }

        return clauseValueResult;
    }

}
