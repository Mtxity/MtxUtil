package com.edavalos.mtx.util.graph;

import java.util.List;

public interface MtxGraph {

    /**
     * Adds a new vertex to this graph
     * @return true if this vertex was successfully added, false otherwise
     */
    boolean addVertex(String label);

    /**
     * Removes a vertex from this graph
     * @return true if this vertex was successfully removed, false otherwise
     */
    boolean removeVertex(String label);

    /**
     * Adds a new edge to this graph, given two vertices
     * @return true if this edge was successfully added, false otherwise
     */
    boolean addEdge(String label1, String label2);

    /**
     * Removes an edge from this graph, given two vertices
     * @return true if this edge was successfully removed, false otherwise
     */
    boolean removeEdge(String label1, String label2);

    /**
     * @return a list of MtxVertex that represent every label adjacent to given label
     */
    List<MtxVertex> getAdjVertex(String label);

    /**
     * @return a string representation of this list
     */
    @Override
    String toString();
}
