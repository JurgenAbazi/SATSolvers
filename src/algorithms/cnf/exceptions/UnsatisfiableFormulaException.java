package algorithms.cnf.exceptions;

/**
 * Custom Exception
 * Thrown if there is no assignment of variables that can satisfy the CNF Formula
 */
public class UnsatisfiableFormulaException extends Exception {
    /**
     * 1-Parameter Constructor.
     *
     * @param message Exception message.
     */
    public UnsatisfiableFormulaException(String message) {
        super(message);
    }
}
