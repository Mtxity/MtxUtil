package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxImmutablePairTest {
    private static final String SAMPLE_KEY = "Sample Key";
    private static final int SAMPLE_VALUE = 27;

    private MtxImmutablePair<String, Integer> mtxImmutablePair;

    @BeforeEach
    public void setUp() {
        mtxImmutablePair = new MtxImmutablePair<>(SAMPLE_KEY, SAMPLE_VALUE);
    }

    @Test
    public void testGetKey() {
        assertEquals(SAMPLE_KEY, mtxImmutablePair.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals(SAMPLE_VALUE, mtxImmutablePair.getValue());
    }

    @Test
    public void testSetKey() {
        UnsupportedOperationException uoe = assertThrows(
                UnsupportedOperationException.class,
                () -> mtxImmutablePair.setKey("Replacement Key")
        );

        assertTrue(uoe.getMessage().contains("MtxImmutablePair") && uoe.getMessage().contains("key"));
    }

    @Test
    public void testSetValue() {
        UnsupportedOperationException uoe = assertThrows(
                UnsupportedOperationException.class,
                () -> mtxImmutablePair.setValue(23)
        );

        assertTrue(uoe.getMessage().contains("MtxImmutablePair") && uoe.getMessage().contains("value"));
    }
}
