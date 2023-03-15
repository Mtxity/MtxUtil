package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxAdjacencyMatrixGraphTest {
    private MtxAdjacencyMatrixGraph mtxAdjacencyMatrixGraph;

    @BeforeEach
    public void setUp() {
        mtxAdjacencyMatrixGraph = new MtxAdjacencyMatrixGraph();
        mtxAdjacencyMatrixGraph.addVertex("Bob");
        mtxAdjacencyMatrixGraph.addVertex("Alice");
        mtxAdjacencyMatrixGraph.addVertex("Mark");
        mtxAdjacencyMatrixGraph.addVertex("Rob");
        mtxAdjacencyMatrixGraph.addVertex("Maria");
        mtxAdjacencyMatrixGraph.addEdge("Bob", "Alice");
        mtxAdjacencyMatrixGraph.addEdge("Bob", "Rob");
        mtxAdjacencyMatrixGraph.addEdge("Alice", "Mark");
        mtxAdjacencyMatrixGraph.addEdge("Rob", "Mark");
        mtxAdjacencyMatrixGraph.addEdge("Alice", "Maria");
        mtxAdjacencyMatrixGraph.addEdge("Rob", "Maria");
    }

    @Test
    public void test_poc() {
        List<MtxVertex> adjacentVertices;

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Alice"));
                add(new MtxVertex("Rob"));
        }};
        assertEquals(adjacentVertices, mtxAdjacencyMatrixGraph.getAdjVertex("Bob"));
        assertEquals(adjacentVertices, mtxAdjacencyMatrixGraph.getAdjVertex("Mark"));
        assertEquals(adjacentVertices, mtxAdjacencyMatrixGraph.getAdjVertex("Maria"));

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Bob"));
                add(new MtxVertex("Mark"));
                add(new MtxVertex("Maria"));
        }};
        assertEquals(adjacentVertices, mtxAdjacencyMatrixGraph.getAdjVertex("Alice"));
        assertEquals(adjacentVertices, mtxAdjacencyMatrixGraph.getAdjVertex("Rob"));
        // Need a better way to verify these
    }

    @Test
    public void testToString() {
        String expected = "+------+------+------+------+------+------+\n" +
                          "|      |   Bob| Alice|  Mark|   Rob| Maria|\n" +
                          "+------+------+------+------+------+------+\n" +
                          "|   Bob|     0|     1|     0|     1|     0|\n" +
                          "+------+------+------+------+------+------+\n" +
                          "| Alice|     1|     0|     1|     0|     1|\n" +
                          "+------+------+------+------+------+------+\n" +
                          "|  Mark|     0|     1|     0|     1|     0|\n" +
                          "+------+------+------+------+------+------+\n" +
                          "|   Rob|     1|     0|     1|     0|     1|\n" +
                          "+------+------+------+------+------+------+\n" +
                          "| Maria|     0|     1|     0|     1|     0|\n" +
                          "+------+------+------+------+------+------+";
        assertEquals(expected, mtxAdjacencyMatrixGraph.toString());
    }
}
