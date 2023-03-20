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
    public void testConstructor() {
        mtxStaticAdjacencyMatrixGraph = new MtxStaticAdjacencyMatrixGraph(
                "Bob",
                "Alice",
                "Mark",
                "Rob",
                "Maria"
        );

        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Bob", "Alice"));
        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Bob", "Rob"));
        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Alice", "Mark"));
        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Rob", "Mark"));
        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Alice", "Maria"));
        assertTrue(mtxStaticAdjacencyMatrixGraph.addEdge("Rob", "Maria"));
    }

    @Test
    public void testGetAdjVertex() {
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
    public void testGetAdjVertex_nonexistentVertex() {
        assertNull(mtxStaticAdjacencyMatrixGraph.getAdjVertex("Yob"));
    }

    @Test
    public void testAddEdge_nonexistentVertex() {
        assertFalse(mtxStaticAdjacencyMatrixGraph.addEdge("Bob", null));
        assertFalse(mtxStaticAdjacencyMatrixGraph.addEdge(null, "Bob"));
    }

    @Test
    public void testAddVertex_invalidValue() {
        assertFalse(mtxStaticAdjacencyMatrixGraph.addVertex(null));
        assertFalse(mtxStaticAdjacencyMatrixGraph.addVertex("Bob"));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxStaticAdjacencyMatrixGraph.addVertex("Yob"));
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

    @Test
    public void testRemoveVertex_exists() {
        String vertexToRemove = "Bob";
        assertFalse(mtxStaticAdjacencyMatrixGraph.removeVertex(vertexToRemove));
    }

    @Test
    public void testRemoveVertex_doesntExist() {
        String vertexToRemove = "Yob";
        assertFalse(mtxStaticAdjacencyMatrixGraph.removeVertex(vertexToRemove));
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

        assertTrue(mtxStaticAdjacencyMatrixGraph.removeEdge(edgeToRemove[0], edgeToRemove[1]));
        assertEquals(expected, mtxStaticAdjacencyMatrixGraph.toString());
    }

    @Test
    public void testRemoveEdge_doesntExist() {
        assertFalse(mtxStaticAdjacencyMatrixGraph.removeEdge("Bob", null));
        assertFalse(mtxStaticAdjacencyMatrixGraph.removeEdge(null, "Bob"));
    }

    @Test
    public void testIsAdjVertex() {
        String[] yesAdj1 = {"Bob", "Rob"};
        String[] yesAdj2 = {"Alice", "Mark"};
        String[] noAdj1 = {"Bob", "Mark"};
        String[] noAdj2 = {"Alice", "Rob"};

        assertTrue(mtxStaticAdjacencyMatrixGraph.isAdjacent(yesAdj1[0], yesAdj1[1]));
        assertTrue(mtxStaticAdjacencyMatrixGraph.isAdjacent(yesAdj2[0], yesAdj2[1]));
        assertFalse(mtxStaticAdjacencyMatrixGraph.isAdjacent(noAdj1[0], noAdj1[1]));
        assertFalse(mtxStaticAdjacencyMatrixGraph.isAdjacent(noAdj2[0], noAdj2[1]));
    }
}
