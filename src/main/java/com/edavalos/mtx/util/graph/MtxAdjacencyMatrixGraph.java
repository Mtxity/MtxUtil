package com.edavalos.mtx.util.graph;

import com.edavalos.mtx.util.string.MtxMatrixFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class MtxAdjacencyMatrixGraph implements MtxGraph {
    private static final String EMPTY_MARKER = "_BLANK_";
    private final List<List<Boolean>> adjacencyMatrix;
    private int vertices;
    private final Map<String, Integer> vertexIdxMap;
    private final Map<Integer, String> idxVertexMap;
    private int nextSpot;

    public MtxAdjacencyMatrixGraph(int startingVertexCount) {
        this.adjacencyMatrix = new ArrayList<>();
        this.vertices = startingVertexCount;
        this.vertexIdxMap = new HashMap<>();
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

    @Override
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
        if (this.vertexIdxMap.containsKey(label) || this.idxVertexMap.containsValue(label)) {
            return false;
        }

        this.vertexIdxMap.put(label, this.nextSpot);
        this.idxVertexMap.put(this.nextSpot, label);

        if (this.nextSpot >= this.vertices) {
            this.vertices ++;
            for (List<Boolean> row : this.adjacencyMatrix) {
                row.add(false);
            }

            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int i = 0; i < this.vertices; i++) {
                newRow.add(false);
            }
            this.adjacencyMatrix.add(newRow);
        }

        this.nextSpot ++;
        return true;
    }

    @Override
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
        if (!this.vertexIdxMap.containsKey(label) || !this.idxVertexMap.containsValue(label)) {
            return false;
        }

        this.idxVertexMap.remove(this.vertexIdxMap.remove(label), label);
        return true;
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
        // no nonexistent labels
        if (!this.vertexIdxMap.containsKey(label1) || !this.vertexIdxMap.containsKey(label2)) {
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

        int idx1 = this.vertexIdxMap.get(label1);
        int idx2 = this.vertexIdxMap.get(label2);

        this.adjacencyMatrix.get(idx1).set(idx2, direction);
        this.adjacencyMatrix.get(idx2).set(idx1, direction);
        return true;
    }

    @Override
    public List<MtxVertex> getAdjVertex(String label) {
        // no null labels
        if (label == null || "".equals(label)) {
            return null;
        }

        // no using the null placeholder
        if (EMPTY_MARKER.equals(label)) {
            return null;
        }

        // no nonexistent labels
        if (!this.vertexIdxMap.containsKey(label) || !this.idxVertexMap.containsValue(label)) {
            return null;
        }

        LinkedList<MtxVertex> vertexList = new LinkedList<>();
        int innerIdx = 0;
        for (boolean vertexIdx : this.adjacencyMatrix.get(this.vertexIdxMap.get(label))) {
            if (vertexIdx) {
                vertexList.add(new MtxVertex(this.idxVertexMap.get(innerIdx)));
            }
            innerIdx ++;
        }
        return vertexList;
    }

    @Override
    public String toString() {
        int strMatSize = this.vertexIdxMap.keySet().size() + 1;
        String[][] matrix = new String[strMatSize][strMatSize];
        matrix[0][0] = "";
        for (Map.Entry<String, Integer> pair : this.vertexIdxMap.entrySet()) {
            matrix[0][pair.getValue() + 1] = pair.getKey();
        }

        for (int row = 0; row < this.adjacencyMatrix.size(); row++) {
            matrix[row + 1][0] = this.idxVertexMap.get(row);
            for (int col = 0; col < this.adjacencyMatrix.size(); col++) {
                if (this.idxVertexMap.containsKey(col)) {
                    matrix[row + 1][col + 1] = this.adjacencyMatrix.get(row).get(col) ? "1" : "0";
                }
            }
        }
        return MtxMatrixFormatter.formatBorder(matrix);
    }
}
