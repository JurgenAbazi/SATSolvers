package algorithms;

import algorithms.cnf.Clause;
import algorithms.cnf.Formula;
import algorithms.cnf.Literal;
import algorithms.cnf.exceptions.UnsatisfiableFormulaException;
import algorithms.graph.Graph;

import java.util.Map;
import java.util.Stack;

/**
 * Class containing a 2-SAT solver
 */
public class TwoSAT {
    /**
     * Computes a satisfying assignment for a 2-SAT formula.
     *
     * @param formula The formula in 2-SAT Conjunctive Normal Form.
     * @return A satisfying assignment of the variables.
     * @throws UnsatisfiableFormulaException No satisfiable solution exist.
     */
    public static boolean[] solve2SAT(Formula formula) throws UnsatisfiableFormulaException {
        boolean[] solution = new boolean[formula.getNumberOfVariables()];

        Graph<Literal> graph = new Graph<>(true);
        for (Clause clause : formula.getClauses()) {
            Literal first = clause.getFirst();
            Literal second = clause.getSecond();

            graph.addEdge(new Literal(-first.getAsInt()), second);
            graph.addEdge(new Literal(-second.getAsInt()), first);
        }

        Map<Literal, Integer> scc = graph.getStronglyConnectedComponents();
        for (int variable = 1; variable <= formula.getNumberOfVariables(); variable++) {
            if (scc.get(new Literal(variable)).equals(scc.get(new Literal(-variable)))) {
                throw new UnsatisfiableFormulaException("No satisfying assignments exists for the 2-SAT Formula.");
            }
        }

        Stack<Literal> verticesStack = graph.getVerticesStack();
        while (!verticesStack.empty()) {
            Literal sinkVertex = verticesStack.pop();
            solution[sinkVertex.getAtom().getVar() - 1] = !sinkVertex.isNegated();
        }

        return solution;
    }

}
