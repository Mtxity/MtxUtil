package com.edavalos.mtx.util.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Based on https://www.programiz.com/dsa/graph-adjacency-matrix
public final class MtxStaticAdjacencyMatrixGraph {
    private final boolean[][] adjacencyMatrix;
    private final int vertices;
    private final Map<String, Integer> vertexIdxMap;
    private final Map<Integer, String> idxVertexMap;
    private int nextSpot;

    public MtxStaticAdjacencyMatrixGraph(int verticesCount) {
        this.adjacencyMatrix = new boolean[verticesCount][verticesCount];
        this.vertices = verticesCount;
        this.vertexIdxMap = new HashMap<>();
        this.idxVertexMap = new HashMap<>();
        this.nextSpot = 0;
    }

    public MtxStaticAdjacencyMatrixGraph(String... vertices) {
        this(vertices.length);

        for (String vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    public boolean addVertex(String label) {
        // no null labels
        if (label == null || "".equals(label)) {
            return false;
        }

        // no duplicate labels
        if (this.vertexIdxMap.containsKey(label)) {
            return false;
        }

        // no going over matrix size
        if (this.nextSpot == this.vertices) {
            throw new IndexOutOfBoundsException(this.nextSpot);
        }

        this.vertexIdxMap.put(label, this.nextSpot);
        this.idxVertexMap.put(this.nextSpot, label);
        this.nextSpot ++;
        return true;
    }

    public boolean removeVertex(String label) {
        // no removing vertices
        return false;
    }

    public boolean addEdge(String label1, String label2) {
        return flipEdge(label1, label2, true);
    }

    public boolean removeEdge(String label1, String label2) {
        return flipEdge(label1, label2, false);
    }

    private boolean flipEdge(String label1, String label2, boolean direction) {
        // no null labels
        if (!this.vertexIdxMap.containsKey(label1) || !this.vertexIdxMap.containsKey(label2)) {
            return false;
        }

        int idx1 = this.vertexIdxMap.get(label1);
        int idx2 = this.vertexIdxMap.get(label2);

        this.adjacencyMatrix[idx1][idx2] = direction;
        this.adjacencyMatrix[idx2][idx1] = direction;
        return true;
    }

    public List<MtxVertex> getAdjVertex(String label) {
        if (this.vertexIdxMap.get(label) == null) {
            return null;
        }

        LinkedList<MtxVertex> vertexList = new LinkedList<>();
        int innerIdx = 0;
        for (boolean vertexIdx : this.adjacencyMatrix[this.vertexIdxMap.get(label)]) {
            if (vertexIdx) {
                vertexList.add(new MtxVertex(this.idxVertexMap.get(innerIdx)));
            }
            innerIdx ++;
        }
        return vertexList;
    }

    // @TODO: Format this nicely
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int k = 0; k < this.vertices; k++) {
            s.append(this.idxVertexMap.get(k) + " ");
        }
        s.append("\n");

        for (int i = 0; i < this.vertices; i++) {
            s.append(this.idxVertexMap.get(i) + ": ");
            for (boolean j : this.adjacencyMatrix[i]) {
                s.append((j ? 1 : 0) + " ");
            }
            s.append("\n");
        }
        return s.toString().stripTrailing();
    }
}
