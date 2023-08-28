package com.edavalos.mtx.util.grouping.pair;

import com.edavalos.mtx.util.db.MtxBitColumn;

import com.edavalos.mtx.util.grouping.pair.MtxMutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxMutablePairTest {
    private static final String SAMPLE_KEY = "Sample Key";
    private static final int SAMPLE_VALUE = 27;

    private MtxMutablePair<String, Integer> mtxMutablePair;

    @BeforeEach
    public void setUp() {
        mtxMutablePair = new MtxMutablePair<>(SAMPLE_KEY, SAMPLE_VALUE);
    }

    @Test
    public void testGetKey() {
        assertEquals(SAMPLE_KEY, mtxMutablePair.getKey());
    }

    @Test
    public void testGetValue() {
        assertEquals(SAMPLE_VALUE, mtxMutablePair.getValue());
    }

    @Test
    public void testSetKey() {
        String newKey = "Replacement Key";
        mtxMutablePair.setKey(newKey);

        assertEquals(newKey, mtxMutablePair.getKey());
    }

    @Test
    public void testSetValue() {
        int newVal = 8;
        mtxMutablePair.setValue(newVal);

        assertEquals(newVal, mtxMutablePair.getValue());
    }

    @Test
    public void testHasChanged_whenKeyChanges() {
        assertFalse(mtxMutablePair.hasChanged());

        mtxMutablePair.setKey("Other");

        assertTrue(mtxMutablePair.hasChanged());
    }

    @Test
    public void testHasChanged_whenValueChanges() {
        assertFalse(mtxMutablePair.hasChanged());

        mtxMutablePair.setValue(23);

        assertTrue(mtxMutablePair.hasChanged());
    }

    @Nested
    public class EqualsTests {

        @Test
        public void testEquals_otherPairNull() {
            assertFalse(mtxMutablePair.equals(null));
        }

        @Test
        public void testEquals_otherPairNotAPair() {
            assertFalse(mtxMutablePair.equals(new MtxBitColumn()));
        }

        @Test
        public void testEquals_hasChanged() {
            MtxMutablePair<String, Integer> otherPair = new MtxMutablePair<>(SAMPLE_KEY, SAMPLE_VALUE);
            otherPair.setValue(SAMPLE_VALUE);
            assertFalse(mtxMutablePair.equals(otherPair));
        }

        @Test
        public void testEquals_notEqual() {
            MtxMutablePair<String, Integer> otherPair = new MtxMutablePair<>("g", 6);
            assertFalse(mtxMutablePair.equals(otherPair));
        }

        @Test
        public void testEquals_equal() {
            MtxMutablePair<String, Integer> otherPair = new MtxMutablePair<>(SAMPLE_KEY, SAMPLE_VALUE);
            assertTrue(mtxMutablePair.equals(otherPair));
        }
    }

    @Test
    public void testToString() {
        String mtxPairAsString = "<" + SAMPLE_KEY + " : " + SAMPLE_VALUE + ">";
        assertEquals(mtxPairAsString, mtxMutablePair.toString());
    }
}
