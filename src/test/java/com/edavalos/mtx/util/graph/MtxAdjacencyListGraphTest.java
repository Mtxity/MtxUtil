package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxAdjacencyListGraphTest {

    @Test
    public void test_poc() {
        List<MtxVertex> adjacentVertices;

        MtxAdjacencyListGraph graph = new MtxAdjacencyListGraph();
        graph.addVertex("Bob");
        graph.addVertex("Alice");
        graph.addVertex("Mark");
        graph.addVertex("Rob");
        graph.addVertex("Maria");
        graph.addEdge("Bob", "Alice");
        graph.addEdge("Bob", "Rob");
        graph.addEdge("Alice", "Mark");
        graph.addEdge("Rob", "Mark");
        graph.addEdge("Alice", "Maria");
        graph.addEdge("Rob", "Maria");

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Alice"));
                add(new MtxVertex("Rob"));
        }};
        assertEquals(adjacentVertices, graph.getAdjVertex("Bob"));
        assertEquals(adjacentVertices, graph.getAdjVertex("Mark"));
        assertEquals(adjacentVertices, graph.getAdjVertex("Maria"));

        adjacentVertices = new ArrayList<>() {{
                add(new MtxVertex("Bob"));
                add(new MtxVertex("Mark"));
                add(new MtxVertex("Maria"));
        }};
        assertEquals(adjacentVertices, graph.getAdjVertex("Alice"));
        assertEquals(adjacentVertices, graph.getAdjVertex("Rob"));
        // Need a better way to verify these
    }
}
