package com.edavalos.mtx.util.graph;

import com.edavalos.mtx.util.string.MtxMatrixFormatter;
import com.edavalos.mtx.util.string.MtxStringUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Based on https://www.programiz.com/dsa/graph-adjacency-matrix
public final class MtxStaticAdjacencyMatrixGraph implements MtxGraph {
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

    @Override
    public boolean addVertex(String label) {
        // no null labels
        if (MtxStringUtil.isEmpty(label)) {
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

    @Override
    public boolean removeVertex(String label) {
        // no removing vertices
        return false;
    }

    @Override
    public boolean addEdge(String label1, String label2) {
        return flipEdge(label1, label2, true);
    }

    @Override
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

    @Override
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
        String[][] matrix = new String[this.vertices + 1][this.vertices + 1];
        matrix[0][0] = "";
        for (int k = 1; k < this.vertices + 1; k++) {
            matrix[0][k] = this.idxVertexMap.get(k - 1);
        }

        for (int row = 1; row < this.vertices + 1; row++) {
            matrix[row][0] = this.idxVertexMap.get(row - 1);
            for (int col = 1; col < this.vertices + 1; col++) {
                matrix[row][col] = this.adjacencyMatrix[row - 1][col - 1] ? "1" : "0";
            }
        }
        return MtxMatrixFormatter.formatBorder(matrix);
    }
}
