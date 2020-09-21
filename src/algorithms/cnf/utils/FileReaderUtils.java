package algorithms.cnf.utils;

import algorithms.cnf.Clause;
import algorithms.cnf.Formula;
import algorithms.cnf.Literal;
import algorithms.cnf.Variable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Class with utility methods for reading and writing input files
 */
public class FileReaderUtils {
    /**
     * Method that takes a file as argument and returns a CNF formula
     *
     * @param fileName the name of the file being read
     * @return a formula in CNF form
     */
    public static Formula parseFormulaFromFile(String fileName) {
        try {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int numberOfVariables = Integer.parseInt(reader.readLine());
            Formula formula = new Formula(numberOfVariables);

            HashMap<Integer, Variable> variablesMap = new HashMap<>();
            for (int i = 1; i <= numberOfVariables; i++) {
                variablesMap.put(i, new Variable(i));
            }

            reader.readLine();
            reader.lines().forEachOrdered(line -> {
                Clause clause = new Clause();
                for (String s : line.split(",")) {
                    int literal = Integer.parseInt(s.trim());
                    int variableNumber = Math.abs(literal);
                    boolean isNegated = literal < 0;

                    clause.addLiteral(new Literal(variablesMap.get(variableNumber), isNegated));
                    if (isNegated) {
                        variablesMap.get(variableNumber).addClause(clause);
                    }
                }
                formula.addClause(clause);
            });
            return formula;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Formula(0);
    }

}
