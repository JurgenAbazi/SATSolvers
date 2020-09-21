package algorithms.graph;

import java.util.*;

/**
 * Generic Graph Implementation used in solving 2-SAT.
 */
public class Graph<T> {
    /**
     * Improved Adjacency List Implementation of the Graph.
     */
    private final Map<T, List<T>> graph;

    /**
     * Stack of Vertices ordered by Strongly Connected Components.
     */
    private Stack<T> verticesStack;

    /**
     * Internal Map containing if vertex is visited during DFS.
     */
    private Map<T, Boolean> visitedMap;

    /**
     * Maps each Vertex to its Strongly Connected Component.
     */
    private Map<T, Integer> stronglyConnectedComponentsMap;

    /**
     * Internal counter used for numbering the Strongly Connected Components.
     */
    private int stronglyConnectedComponentsCounter;

    /**
     * Variable storing if the graph is directed or not
     */
    private final boolean directed;

    /**
     * 1-Parameter Constructor.
     *
     * @param directed Is the graph directed or not.
     */
    public Graph(boolean directed) {
        this.directed = directed;
        graph = new HashMap<>();
        stronglyConnectedComponentsMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex Vertex being added.
     */
    public void addVertex(T vertex) {
        graph.put(vertex, new ArrayList<>());
    }

    /**
     * Adds an edge to the graph. It also adds the vertices if not present.
     *
     * @param source      Source vertex of the edge.
     * @param destination Destination vertex of the edge.
     */
    public void addEdge(T source, T destination) {
        if (!graph.containsKey(source)) {
            addVertex(source);
        }
        if (!graph.containsKey(destination)) {
            addVertex(destination);
        }

        graph.get(source).add(destination);
        if (!directed) {
            graph.get(destination).add(source);
        }
    }

    /**
     * Returns the reverse (transpose) of the graph.
     *
     * @return The reverse graph.
     */
    public Graph<T> getReverseGraph() {
        Graph<T> reverse = new Graph<>(directed);
        for (T source : graph.keySet()) {
            for (T destination : graph.get(source)) {
                reverse.addEdge(destination, source);
            }
        }

        return reverse;
    }

    /**
     * Performs the Depth First Search algorithm on the graph.
     */
    public void depthFirstSearch() {
        verticesStack = new Stack<>();

        resetVisitedMap();
        for (T vertex : graph.keySet()) {
            if (!visitedMap.get(vertex)) {
                stronglyConnectedComponentsCounter++;
                explore(vertex);
            }
        }
    }

    /**
     * Recursively finds all vertices reachable from a certain vertex.
     *
     * @param vertex The vertex being explored.
     */
    public void explore(T vertex) {
        visitedMap.put(vertex, true);
        stronglyConnectedComponentsMap.put(vertex, stronglyConnectedComponentsCounter);
        for (T v : graph.get(vertex)) {
            if (!visitedMap.get(v)) {
                explore(v);
            }
        }
        verticesStack.push(vertex);
    }

    /**
     * Perform Kosaraju's Strongly Connected Components Algorithm on the graph.
     *
     * @return Map of each vertex and its Strongly Connected Component.
     */
    public Map<T, Integer> getStronglyConnectedComponents() {
        stronglyConnectedComponentsMap = new HashMap<>();
        stronglyConnectedComponentsCounter = 0;

        Graph<T> reverse = getReverseGraph();
        reverse.depthFirstSearch();

        resetVisitedMap();
        verticesStack = new Stack<>();
        while (!reverse.verticesStack.empty()) {
            T vertex = reverse.verticesStack.pop();
            if (!visitedMap.get(vertex)) {
                stronglyConnectedComponentsCounter++;
                explore(vertex);
            }
        }

        return stronglyConnectedComponentsMap;
    }

    /**
     * Internal Method for resetting the visited vertices map.
     */
    private void resetVisitedMap() {
        visitedMap = new HashMap<>();
        for (T vertex : graph.keySet()) {
            visitedMap.put(vertex, false);
        }
    }

    /**
     * Returns the vertices stack. Usually in reverse topological ordering.
     *
     * @return the vertices stack
     */
    public Stack<T> getVerticesStack() {
        return verticesStack;
    }

    /**
     * Returns the graph as String in a readable form.
     *
     * @return Graph as String.
     */
    @Override
    public String toString() {
        StringBuilder graphAsString = new StringBuilder();
        for (T vertex : graph.keySet()) {
            graphAsString.append(vertex)
                    .append(": ")
                    .append(graph.get(vertex))
                    .append("\n");
        }
        return graphAsString.toString();
    }

}
