package com.edavalos.mtx.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MtxAdjacencyMatrixGraph {
    private static final String EMPTY_MARKER = "_BLANK_";
    private final List<List<Boolean>> adjacencyMatrix;
    private int vertices;
    private final Map<String, Integer> vertexIdMap;
    private final Map<Integer, String> idxVertexMap;
    private int nextSpot;

    public MtxAdjacencyMatrixGraph(int startingVertexCount) {
        this.adjacencyMatrix = new ArrayList<>();
        this.vertices = startingVertexCount;
        this.vertexIdMap = new HashMap<>();
        this.idxVertexMap = new HashMap<>();
        this.nextSpot = 0;

        for (int i = 0; i < startingVertexCount; i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int j = 0; j < startingVertexCount; j++) {
                newRow.add(false);
            }
            this.adjacencyMatrix.add(newRow);
        }
    }

    public MtxAdjacencyMatrixGraph() {
        this(0);
    }

    public MtxAdjacencyMatrixGraph(String... vertices) {
        this(vertices.length);

        for (String vertex : vertices) {
            //add
        }
    }

    public boolean addVertex(String label) {
        // no null labels
        if (label == null || "".equals(label)) {
            return false;
        }

        // no using the null placeholder
        if (EMPTY_MARKER.equals(label)) {
            return false;
        }

        // no duplicate labels
        if (this.vertexIdMap.containsKey(label) || this.idxVertexMap.containsValue(label)) {
            return false;
        }

        this.vertexIdMap.put(label, this.nextSpot);
        this.idxVertexMap.put(this.nextSpot, label);

        if (this.nextSpot >= this.vertices) {
            for (List<Boolean> row : this.adjacencyMatrix) {
                row.add(false);
            }

            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int i = 0; i < this.vertices; i++) {
                newRow.add(false);
            }
            this.adjacencyMatrix.add(newRow);

            this.vertices ++;
        }

        this.nextSpot ++;
        return true;
    }

    public boolean removeVertex(String label) {
        // no null labels
        if (label == null || "".equals(label)) {
            return false;
        }

        // no using the null placeholder
        if (EMPTY_MARKER.equals(label)) {
            return false;
        }

        // no nonexistent labels
        if (!this.vertexIdMap.containsKey(label) || !this.idxVertexMap.containsValue(label)) {
            return false;
        }

        this.idxVertexMap.remove(this.vertexIdMap.remove(label), label);
        return true;
    }

    public boolean addEdge(String label1, String label2) {
        return flipEdge(label1, label2, true);
    }

    public boolean removeEdge(String label1, String label2) {
        return flipEdge(label1, label2, false);
    }

    private boolean flipEdge(String label1, String label2, boolean direction) {
        // no nonexistent labels
        if (!this.vertexIdMap.containsKey(label1) || !this.vertexIdMap.containsKey(label2)) {
            return false;
        }

        // no null labels
        if (label1 == null || "".equals(label1) || label2 == null || "".equals(label2)) {
            return false;
        }

        // no using the null placeholder
        if (EMPTY_MARKER.equals(label1) || EMPTY_MARKER.equals(label2)) {
            return false;
        }

        int idx1 = this.vertexIdMap.get(label1);
        int idx2 = this.vertexIdMap.get(label2);

        this.adjacencyMatrix.get(idx1).set(idx2, direction);
        this.adjacencyMatrix.get(idx2).set(idx1, direction);
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}
