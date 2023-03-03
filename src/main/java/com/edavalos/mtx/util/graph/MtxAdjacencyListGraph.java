package com.edavalos.mtx.util.graph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

// Based on: https://www.baeldung.com/java-graphs
public final class MtxAdjacencyListGraph {
    private Map<MtxVertex, List<MtxVertex>> adjacencyVertices;

    public void addVertex(String label) {
        adjacencyVertices.putIfAbsent(new MtxVertex(label), new ArrayList<>());
    }

    public void removeVertex(String label) {
        MtxVertex v = new MtxVertex(label);
        adjacencyVertices.values().stream().forEach(e -> e.remove(v));
        adjacencyVertices.remove(new MtxVertex(label));
    }

    public void addEdge(String label1, String label2) {
        MtxVertex v1 = new MtxVertex(label1);
        MtxVertex v2 = new MtxVertex(label2);
        adjacencyVertices.get(v1).add(v2);
        adjacencyVertices.get(v2).add(v1);
    }

    public void removeEdge(String label1, String label2) {
        MtxVertex v1 = new MtxVertex(label1);
        MtxVertex v2 = new MtxVertex(label2);

        List<MtxVertex> eV1 = adjacencyVertices.get(v1);
        List<MtxVertex> eV2 = adjacencyVertices.get(v2);

        if (eV1 != null) {
            eV1.remove(v2);
        }
        if (eV2 != null) {
            eV2.remove(v2);
        }
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
}
