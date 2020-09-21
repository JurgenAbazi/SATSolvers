import algorithms.GeneralSAT;
import algorithms.SATUtils;
import algorithms.TwoSAT;
import algorithms.HornSAT;
import algorithms.cnf.Formula;
import algorithms.cnf.exceptions.UnsatisfiableFormulaException;
import algorithms.cnf.utils.FileReaderUtils;

import java.util.*;

/**
 * Program Starting Point.
 */
public class App {
    /**
     * Scanner for reading user input.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        init();
    }

    /**
     * Initiates the command line program.
     */
    public static void init() {
        int input;

        System.out.println("Welcome to the CNF SAT solver.");
        do {
            System.out.println();
            System.out.println("Press a button from 1 to 7:");
            System.out.println("\t1. Check Assignment.");
            System.out.println("\t2. Is 2-SAT.");
            System.out.println("\t3. Is Horn-SAT.");
            System.out.println("\t4. Solve General SAT.");
            System.out.println("\t5. Solve 2-SAT.");
            System.out.println("\t6. Solve Horn-SAT.");
            System.out.println("\t7. Close");
            System.out.print("Enter your selection: ");
            input = scanner.nextInt();

            if (input == 1) {
                checkAssignmentOption();
            } else if (input == 2) {
                is2SATOption();
            } else if (input == 3) {
                isHornSATOption();
            } else if (input == 4) {
                solveGeneralSATOption();
            } else if (input == 5) {
                solve2SATOption();
            } else if (input == 6) {
                solveHornSATOption();
            } else if (input == 7) {
                closeOption();
            } else {
                System.out.println("Incorrect input.");
            }
        } while (input != 7);
    }

    /**
     * Option for testing the Check Assignment Option.
     */
    public static void checkAssignmentOption() {
        Formula formula = readFile();
        int numberOfVariables = formula.getNumberOfVariables();
        boolean[] assignment = new boolean[numberOfVariables];

        System.out.println("The formula contains " + numberOfVariables + " variables.");
        System.out.println("Enter the assignments one by one (enter 1 for true, any other int for false):");

        for (int i = 0; i < numberOfVariables; i++) {
            System.out.print("\tAssignment for variable " + (i + 1) + ": ");
            int intInput = scanner.nextInt();
            assignment[i] = intInput == 1;
        }

        if (SATUtils.checkAssignment(formula, assignment)) {
            System.out.println("The assignment satisfies the formula.");
        } else {
            System.out.println("The assignment does not satisfy the formula.");
        }
    }

    /**
     * Option for checking if formula is Horn-SAT
     */
    public static void isHornSATOption() {
        Formula formula = readFile();
        if (SATUtils.isHornSAT(formula)) {
            System.out.println("The formula entered is Horn-SAT.");
        } else {
            System.out.println("The formula entered is not Horn-SAT.");
        }
    }

    /**
     * Option for checking if formula is 2-SAT
     */
    public static void is2SATOption() {
        Formula formula = readFile();
        if (SATUtils.is2SAT(formula)) {
            System.out.println("The formula entered is 2-SAT.");
        } else {
            System.out.println("The formula entered is not 2-SAT.");
        }
    }

    /**
     * Option for testing Solve General SAT algorithm.
     */
    public static void solveGeneralSATOption() {
        try {
            boolean[] solution;
            Formula formula = readFile();
            solution = GeneralSAT.solveGeneralSAT(formula);

            System.out.println("The solution of the SAT Problem is:");
            System.out.println(Arrays.toString(solution));
        } catch (UnsatisfiableFormulaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Option for testing Solve 2-SAT algorithm.
     */
    public static void solve2SATOption() {
        try {
            Formula formula = readFile();
            if (SATUtils.is2SAT(formula)) {
                System.out.println("The solution of the 2-SAT Problem is:");
                System.out.println(Arrays.toString(TwoSAT.solve2SAT(formula)));
            } else {
                System.out.println("The formula entered is not 2-SAT.");
            }
        } catch (UnsatisfiableFormulaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Option for testing Solve Horn-SAT algorithm.
     */
    public static void solveHornSATOption() {
        try {
            Formula formula = readFile();
            if (SATUtils.isHornSAT(formula)) {
                System.out.println("The solution of the Horn-SAT Problem is:");
                System.out.println(Arrays.toString(HornSAT.solveHornSAT(formula)));
            } else {
                System.out.println("The formula entered is not Horn-SAT");
            }
        } catch (UnsatisfiableFormulaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints closing message.
     */
    public static void closeOption() {
        System.out.println("Exiting command line interface.");
    }

    /**
     * Prompts the user for the formula file path.
     *
     * @return The parsed formula
     */
    private static Formula readFile() {
        System.out.println("Please enter the path of the file containing the SAT Formula:");
        String path = scanner.next();

        return FileReaderUtils.readFile(path);
    }
}
