package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxAdjacencyListGraphTest {
    private MtxAdjacencyListGraph mtxAdjacencyListGraph;

    @BeforeEach
    public void setUp() {
        mtxAdjacencyListGraph = new MtxAdjacencyListGraph();
        mtxAdjacencyListGraph.addVertex("Bob");
        mtxAdjacencyListGraph.addVertex("Alice");
        mtxAdjacencyListGraph.addVertex("Mark");
        mtxAdjacencyListGraph.addVertex("Rob");
        mtxAdjacencyListGraph.addVertex("Maria");
        mtxAdjacencyListGraph.addEdge("Bob", "Alice");
        mtxAdjacencyListGraph.addEdge("Bob", "Rob");
        mtxAdjacencyListGraph.addEdge("Alice", "Mark");
        mtxAdjacencyListGraph.addEdge("Rob", "Mark");
        mtxAdjacencyListGraph.addEdge("Alice", "Maria");
        mtxAdjacencyListGraph.addEdge("Rob", "Maria");
    }

    @Test
    public void testConstructor() {
        mtxAdjacencyListGraph = new MtxAdjacencyListGraph(
                "Bob",
                "Alice",
                "Mark",
                "Rob",
                "Maria"
        );

        assertTrue(mtxAdjacencyListGraph.addEdge("Bob", "Alice"));
        assertTrue(mtxAdjacencyListGraph.addEdge("Bob", "Rob"));
        assertTrue(mtxAdjacencyListGraph.addEdge("Alice", "Mark"));
        assertTrue(mtxAdjacencyListGraph.addEdge("Rob", "Mark"));
        assertTrue(mtxAdjacencyListGraph.addEdge("Alice", "Maria"));
        assertTrue(mtxAdjacencyListGraph.addEdge("Rob", "Maria"));
    }

    @Test
    public void testGetAdjVertex() {
        List<MtxVertex> adjacentVertices;

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Alice"));
                add(new MtxVertex("Rob"));
        }};
        assertEquals(adjacentVertices, mtxAdjacencyListGraph.getAdjVertex("Bob"));
        assertEquals(adjacentVertices, mtxAdjacencyListGraph.getAdjVertex("Mark"));
        assertEquals(adjacentVertices, mtxAdjacencyListGraph.getAdjVertex("Maria"));

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Bob"));
                add(new MtxVertex("Mark"));
                add(new MtxVertex("Maria"));
        }};
        assertEquals(adjacentVertices, mtxAdjacencyListGraph.getAdjVertex("Alice"));
        assertEquals(adjacentVertices, mtxAdjacencyListGraph.getAdjVertex("Rob"));
        // Need a better way to verify these
    }

    @Test
    public void testAddEdge_nonexistentVertex() {
        assertFalse(mtxAdjacencyListGraph.addEdge("Bob", null));
        assertFalse(mtxAdjacencyListGraph.addEdge(null, "Bob"));
    }

    @Test
    public void testToString() {
        String expected =
                """
                Bob -> Alice, Rob
                Rob -> Bob, Mark, Maria
                Mark -> Alice, Rob
                Alice -> Bob, Mark, Maria
                Maria -> Alice, Rob
                """;
        assertEquals(expected, mtxAdjacencyListGraph.toString());
    }

    @Test
    public void testRemoveVertex_exists() {
        String expected =
                """
                Rob -> Mark, Maria
                Mark -> Alice, Rob
                Alice -> Mark, Maria
                Maria -> Alice, Rob
                """;
        String vertexToRemove = "Bob";

        assertTrue(mtxAdjacencyListGraph.removeVertex(vertexToRemove));
        assertEquals(expected, mtxAdjacencyListGraph.toString());
    }

    @Test
    public void testRemoveVertex_doesntExist() {
        String expected =
                """
                Bob -> Alice, Rob
                Rob -> Bob, Mark, Maria
                Mark -> Alice, Rob
                Alice -> Bob, Mark, Maria
                Maria -> Alice, Rob
                """;
        String vertexToRemove = "Yob";

        assertFalse(mtxAdjacencyListGraph.removeVertex(vertexToRemove));
        assertEquals(expected, mtxAdjacencyListGraph.toString());
    }

    @Test
    public void testRemoveEdge_exists() {
        String expected =
                """
                Bob -> Rob
                Rob -> Bob, Mark, Maria
                Mark -> Alice, Rob
                Alice -> Mark, Maria
                Maria -> Alice, Rob
                """;
        String[] edgeToRemove = {"Bob", "Alice"};

        assertTrue(mtxAdjacencyListGraph.removeEdge(edgeToRemove[0], edgeToRemove[1]));
        assertEquals(expected, mtxAdjacencyListGraph.toString());
    }

    @Test
    public void testRemoveEdge_doesntExist() {
        assertFalse(mtxAdjacencyListGraph.removeEdge("Bob", null));
        assertFalse(mtxAdjacencyListGraph.removeEdge(null, "Bob"));
    }

    @Test
    public void testIsAdjVertex() {
        String[] yesAdj1 = {"Bob", "Rob"};
        String[] yesAdj2 = {"Alice", "Mark"};
        String[] noAdj1 = {"Bob", "Mark"};
        String[] noAdj2 = {"Alice", "Rob"};

        assertTrue(mtxAdjacencyListGraph.isAdjacent(yesAdj1[0], yesAdj1[1]));
        assertTrue(mtxAdjacencyListGraph.isAdjacent(yesAdj2[0], yesAdj2[1]));
        assertFalse(mtxAdjacencyListGraph.isAdjacent(noAdj1[0], noAdj1[1]));
        assertFalse(mtxAdjacencyListGraph.isAdjacent(noAdj2[0], noAdj2[1]));
    }

    @Test
    public void testDepthFirstTraversal() {
        assertEquals("[Bob, Rob, Maria, Alice, Mark]", mtxAdjacencyListGraph.depthFirstTraversal("Bob").toString());
    }

    @Test
    public void testBreadthFirstTraversal() {
        assertEquals("[Bob, Alice, Rob, Mark, Maria]", mtxAdjacencyListGraph.breadthFirstTraversal("Bob").toString());
    }
}
