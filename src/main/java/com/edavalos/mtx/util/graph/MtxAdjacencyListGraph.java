package com.edavalos.mtx.util.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Based on: https://www.baeldung.com/java-graphs
public final class MtxAdjacencyListGraph {
    private Map<MtxVertex, List<MtxVertex>> adjacencyVertices;

    public void addVertex(String label) {
        adjacencyVertices.putIfAbsent(new MtxVertex(label), new ArrayList<>());
    }
}
