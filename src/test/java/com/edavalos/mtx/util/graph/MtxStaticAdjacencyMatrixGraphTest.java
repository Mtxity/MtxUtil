package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxStaticAdjacencyMatrixGraphTest {
    private MtxStaticAdjacencyMatrixGraph mtxStaticAdjacencyMatrixGraph;

    @BeforeEach
    public void setUp() {
        mtxStaticAdjacencyMatrixGraph = new MtxStaticAdjacencyMatrixGraph(5);
        mtxStaticAdjacencyMatrixGraph.addVertex("Bob");
        mtxStaticAdjacencyMatrixGraph.addVertex("Alice");
        mtxStaticAdjacencyMatrixGraph.addVertex("Mark");
        mtxStaticAdjacencyMatrixGraph.addVertex("Rob");
        mtxStaticAdjacencyMatrixGraph.addVertex("Maria");
        mtxStaticAdjacencyMatrixGraph.addEdge("Bob", "Alice");
        mtxStaticAdjacencyMatrixGraph.addEdge("Bob", "Rob");
        mtxStaticAdjacencyMatrixGraph.addEdge("Alice", "Mark");
        mtxStaticAdjacencyMatrixGraph.addEdge("Rob", "Mark");
        mtxStaticAdjacencyMatrixGraph.addEdge("Alice", "Maria");
        mtxStaticAdjacencyMatrixGraph.addEdge("Rob", "Maria");
    }

    @Test
    public void test_poc() {
        List<MtxVertex> adjacentVertices;

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Alice"));
                add(new MtxVertex("Rob"));
        }};
        assertEquals(adjacentVertices, mtxStaticAdjacencyMatrixGraph.getAdjVertex("Bob"));
        assertEquals(adjacentVertices, mtxStaticAdjacencyMatrixGraph.getAdjVertex("Mark"));
        assertEquals(adjacentVertices, mtxStaticAdjacencyMatrixGraph.getAdjVertex("Maria"));

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Bob"));
                add(new MtxVertex("Mark"));
                add(new MtxVertex("Maria"));
        }};
        assertEquals(adjacentVertices, mtxStaticAdjacencyMatrixGraph.getAdjVertex("Alice"));
        assertEquals(adjacentVertices, mtxStaticAdjacencyMatrixGraph.getAdjVertex("Rob"));
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
        assertEquals(expected, mtxStaticAdjacencyMatrixGraph.toString());
    }
}
