package com.edavalos.mtx.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

// Based on: https://www.baeldung.com/java-graphs
public final class MtxAdjacencyListGraph {
    private final Map<MtxVertex, List<MtxVertex>> adjacencyVertices;

    public MtxAdjacencyListGraph() {
        this.adjacencyVertices = new HashMap<>();
    }

    public MtxAdjacencyListGraph(String... vertices) {
        this();

        for (String vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    public List<MtxVertex> addVertex(String label) {
        return adjacencyVertices.putIfAbsent(new MtxVertex(label), new ArrayList<>());
    }

    public List<MtxVertex> removeVertex(String label) {
        MtxVertex v = new MtxVertex(label);
        adjacencyVertices.values().stream().forEach(e -> e.remove(v));
        return adjacencyVertices.remove(new MtxVertex(label));
    }

    public boolean addEdge(String label1, String label2) {
        MtxVertex v1 = new MtxVertex(label1);
        MtxVertex v2 = new MtxVertex(label2);
        if (adjacencyVertices.get(v1) == null || adjacencyVertices.get(v2) == null) {
             return false;
        }
        adjacencyVertices.get(v1).add(v2);
        adjacencyVertices.get(v2).add(v1);
        return true;
    }

    public boolean removeEdge(String label1, String label2) {
        MtxVertex v1 = new MtxVertex(label1);
        MtxVertex v2 = new MtxVertex(label2);

        List<MtxVertex> eV1 = adjacencyVertices.get(v1);
        List<MtxVertex> eV2 = adjacencyVertices.get(v2);

        boolean containedEdge = false;
        if (eV1 != null) {
            containedEdge = eV1.remove(v2);
        }
        if (eV2 != null) {
            containedEdge = containedEdge || eV2.remove(v2);
        }
        return containedEdge;
    }

    public List<MtxVertex> getAdjVertex(String label) {
        return adjacencyVertices.get(new MtxVertex(label));
    }

    public Set<String> depthFirstTraversal(MtxAdjacencyListGraph graph, String root) {
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (MtxVertex v : graph.getAdjVertex(vertex)) {
                    stack.push(v.label());
                }
            }
        }
        return visited;
    }

    public Set<String> breadthFirstTraversal(MtxAdjacencyListGraph graph, String root) {
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            for (MtxVertex v : graph.getAdjVertex(vertex)) {
                if (!visited.contains(v.label())) {
                    visited.add(v.label());
                    queue.add(v.label());
                }
            }
        }
        return visited;
    }

    @Override
    public String toString() {
        StringBuilder stringRep = new StringBuilder();
        for (MtxVertex vertex : this.adjacencyVertices.keySet()) {
            stringRep.append(vertex.label() + " -> ");

            String[] vertexArray = new String[this.adjacencyVertices.get(vertex).size()];
            for (int i = 0; i < this.adjacencyVertices.get(vertex).size(); i++) {
                vertexArray[i] = this.adjacencyVertices.get(vertex).get(i).label();
            }
            stringRep.append(String.join(", ", vertexArray)).append("\n");
        }
        return stringRep.toString();
    }
}
