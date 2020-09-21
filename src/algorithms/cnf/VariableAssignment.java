package algorithms.cnf;

/**
 * Pair object containing a variable and its assignment.
 * Used by the DPLL algorithm.
 */
public class VariableAssignment {
    /**
     * A variable of a satisfiability problem formula.
     */
    private Variable variable;

    /**
     * The truth value assigned to the variable.
     */
    private boolean assignment;

    /**
     * Constructor.
     *
     * @param variable   The variable.
     * @param assignment Truth value assigned to the value.
     */
    public VariableAssignment(Variable variable, boolean assignment) {
        this.variable = variable;
        this.assignment = assignment;
    }

    /**
     * Returns the variable.
     *
     * @return The variable.
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Returns the truth value assigned to the variable.
     *
     * @return The assignment.
     */
    public boolean getAssignment() {
        return assignment;
    }

    /**
     * Returns the variable assignment in a readable form.
     *
     * @return Variable assignment as String.
     */
    @Override
    public String toString() {
        return variable.toString() + ": " + assignment;
    }

    /**
     * Two variable assignments are equal if they have the same atom variable and assignment.
     *
     * @param obj The object being compared.
     * @return If the objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        VariableAssignment that = (VariableAssignment) obj;
        return variable.getVar() == that.variable.getVar() && assignment == that.assignment;
    }

    /**
     * Two objects that have the same atomic variable assignment return the same hashcode.
     *
     * @return The hashcode of the variable assignment object.
     */
    @Override
    public int hashCode() {
        int result = variable != null ? variable.hashCode() : 0;
        result = 31 * result + (assignment ? 1 : 0);
        return result;
    }
}
