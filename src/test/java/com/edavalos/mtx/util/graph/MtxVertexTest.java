package com.edavalos.mtx.util.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxVertexTest {
    private static final String SAMPLE_LABEL = "sample";
    private MtxVertex mtxVertex;

    @BeforeEach
    public void setUp() {
        mtxVertex = new MtxVertex(SAMPLE_LABEL);
    }

    @Test
    public void testEquals_otherMtxVertex() {
        MtxVertex sameVtx = new MtxVertex(SAMPLE_LABEL);
        MtxVertex otherVtx = new MtxVertex("other");

        assertTrue(mtxVertex.equals(sameVtx));
        assertFalse(mtxVertex.equals(otherVtx));
    }

    @Test
    public void testEquals_otherObject() {
        assertFalse(mtxVertex.equals(new MtxAdjacencyListGraph()));
    }

    @Test
    public void testHashCode() {
        int expected = -909675063;
        assertEquals(expected, mtxVertex.hashCode());
    }
}
