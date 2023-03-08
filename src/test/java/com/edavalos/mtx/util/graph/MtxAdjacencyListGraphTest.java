package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void test_poc() {
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
}
