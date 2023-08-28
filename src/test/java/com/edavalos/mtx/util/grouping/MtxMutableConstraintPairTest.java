package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxMutableConstraintPairTest {
    private static final MtxMutableConstraintPair.MtxPairConstraint<String, Integer> SAMPLE_CONSTRAINT =
            (key, value) -> key != null && key.length() > value;
    private static final String SAMPLE_KEY = "Sample Key";
    private static final int SAMPLE_VALUE = 8;

    private MtxMutableConstraintPair<String, Integer> mtxMutableConstraintPair;

    @BeforeEach
    public void setUp() {
        mtxMutableConstraintPair = new MtxMutableConstraintPair<>(SAMPLE_CONSTRAINT, SAMPLE_KEY, SAMPLE_VALUE);
    }

    @Test
    public void testConstructor_nullKeyValue() {
        IllegalArgumentException iae = assertThrows(
                IllegalArgumentException.class,
                () -> new MtxMutableConstraintPair<>(SAMPLE_CONSTRAINT)
        );

        assertTrue(iae.getMessage().contains("MtxPairConstraint"));
        assertTrue(iae.getMessage().contains("key and value"));
    }

    @Test
    public void testConstructor_violatesConstraint() {
        IllegalArgumentException iae = assertThrows(
                IllegalArgumentException.class,
                () -> new MtxMutableConstraintPair<>(SAMPLE_CONSTRAINT, SAMPLE_KEY, 30)
        );

        assertTrue(iae.getMessage().contains("MtxPairConstraint"));
        assertTrue(iae.getMessage().contains("key and value"));
    }

    @Test
    public void testGetKey() {
        assertEquals(SAMPLE_KEY, mtxMutableConstraintPair.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals(SAMPLE_VALUE, mtxMutableConstraintPair.getValue());
    }

    @Test
    public void testSetKey_doesntViolateConstraint() {
        String newKey = "Replacement Key";
        mtxMutableConstraintPair.setKey(newKey);

        assertEquals(newKey, mtxMutableConstraintPair.getKey());
    }

    @Test
    public void testSetKey_violatesConstraint() {
        String newKey = "New Key";
        IllegalArgumentException iae = assertThrows(
                IllegalArgumentException.class,
                () -> mtxMutableConstraintPair.setKey(newKey)
        );

        assertTrue(iae.getMessage().contains("MtxPairConstraint"));
        assertTrue(iae.getMessage().contains("key"));
        assertFalse(iae.getMessage().contains("value"));

        assertEquals(SAMPLE_KEY, mtxMutableConstraintPair.getKey());
    }

    @Test
    public void testSetValue_doesntViolateConstraint() {
        int newVal = 2;
        mtxMutableConstraintPair.setValue(newVal);

        assertEquals(newVal, mtxMutableConstraintPair.getValue());
    }

    @Test
    public void testSetValue_violatesConstraint() {
        int newVal = 75;
        IllegalArgumentException iae = assertThrows(
                IllegalArgumentException.class,
                () -> mtxMutableConstraintPair.setValue(newVal)
        );

        assertTrue(iae.getMessage().contains("MtxPairConstraint"));
        assertTrue(iae.getMessage().contains("value"));
        assertFalse(iae.getMessage().contains("key"));

        assertEquals(SAMPLE_VALUE, mtxMutableConstraintPair.getValue());
    }

    @Test
    public void testHasChanged_whenKeyChanges() {
        assertFalse(mtxMutableConstraintPair.hasChanged());

        mtxMutableConstraintPair.setKey("Longer Sample Key");

        assertTrue(mtxMutableConstraintPair.hasChanged());
    }

    @Test
    public void testHasChanged_whenValueChanges() {
        assertFalse(mtxMutableConstraintPair.hasChanged());

        mtxMutableConstraintPair.setValue(5);

        assertTrue(mtxMutableConstraintPair.hasChanged());
    }
}
