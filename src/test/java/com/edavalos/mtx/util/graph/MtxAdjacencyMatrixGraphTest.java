package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testConstructor() {
        mtxAdjacencyMatrixGraph = new MtxAdjacencyMatrixGraph(
                "Bob",
                "Alice",
                "Mark",
                "Rob",
                "Maria"
        );

        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Bob", "Alice"));
        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Bob", "Rob"));
        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Alice", "Mark"));
        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Rob", "Mark"));
        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Alice", "Maria"));
        assertTrue(mtxAdjacencyMatrixGraph.addEdge("Rob", "Maria"));
    }

    @Test
    public void testGetAdjVertex() {
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
    public void testGetAdjVertex_nonexistentVertex() {
        assertNull(mtxAdjacencyMatrixGraph.getAdjVertex("Yob"));
    }

    @Test
    public void testGetAdjVertex_invalidValue() {
        assertNull(mtxAdjacencyMatrixGraph.getAdjVertex(""));
        assertNull(mtxAdjacencyMatrixGraph.getAdjVertex("_BLANK_"));
    }

    @Test
    public void testAddEdge_nonexistentVertex() {
        assertFalse(mtxAdjacencyMatrixGraph.addEdge("Bob", null));
        assertFalse(mtxAdjacencyMatrixGraph.addEdge(null, "Bob"));
    }

    @Test
    public void testAddVertex_invalidValue() {
        assertFalse(mtxAdjacencyMatrixGraph.addVertex(null));
        assertFalse(mtxAdjacencyMatrixGraph.addVertex("Bob"));
        assertFalse(mtxAdjacencyMatrixGraph.addVertex("_BLANK_"));
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

    @Test
    public void testRemoveVertex_invalidValue() {
        assertFalse(mtxAdjacencyMatrixGraph.removeVertex(""));
        assertFalse(mtxAdjacencyMatrixGraph.removeVertex("_BLANK_"));
    }

    @Test
    public void testRemoveVertex_doesntExist() {
        String vertexToRemove = "Yob";
        assertFalse(mtxAdjacencyMatrixGraph.removeVertex(vertexToRemove));
    }

    @Test
    public void testRemoveEdge_exists() {
        String expected = "+------+------+------+------+------+------+\n" +
                "|      |   Bob| Alice|  Mark|   Rob| Maria|\n" +
                "+------+------+------+------+------+------+\n" +
                "|   Bob|     0|     0|     0|     1|     0|\n" +
                "+------+------+------+------+------+------+\n" +
                "| Alice|     0|     0|     1|     0|     1|\n" +
                "+------+------+------+------+------+------+\n" +
                "|  Mark|     0|     1|     0|     1|     0|\n" +
                "+------+------+------+------+------+------+\n" +
                "|   Rob|     1|     0|     1|     0|     1|\n" +
                "+------+------+------+------+------+------+\n" +
                "| Maria|     0|     1|     0|     1|     0|\n" +
                "+------+------+------+------+------+------+";
        String[] edgeToRemove = {"Bob", "Alice"};

        assertTrue(mtxAdjacencyMatrixGraph.removeEdge(edgeToRemove[0], edgeToRemove[1]));
        assertEquals(expected, mtxAdjacencyMatrixGraph.toString());
    }

    @Test
    public void testRemoveEdge_doesntExist() {
        assertFalse(mtxAdjacencyMatrixGraph.removeEdge("Bob", null));
        assertFalse(mtxAdjacencyMatrixGraph.removeEdge(null, "Bob"));
    }

    @Test
    public void testIsAdjVertex() {
        String[] yesAdj1 = {"Bob", "Rob"};
        String[] yesAdj2 = {"Alice", "Mark"};
        String[] noAdj1 = {"Bob", "Mark"};
        String[] noAdj2 = {"Alice", "Rob"};

        assertTrue(mtxAdjacencyMatrixGraph.isAdjacent(yesAdj1[0], yesAdj1[1]));
        assertTrue(mtxAdjacencyMatrixGraph.isAdjacent(yesAdj2[0], yesAdj2[1]));
        assertFalse(mtxAdjacencyMatrixGraph.isAdjacent(noAdj1[0], noAdj1[1]));
        assertFalse(mtxAdjacencyMatrixGraph.isAdjacent(noAdj2[0], noAdj2[1]));
    }
}
