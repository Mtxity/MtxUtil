package com.edavalos.mtx.util.grouping;

import com.edavalos.mtx.util.db.MtxBitColumn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

        assertTrue(uoe.getMessage().contains("MtxImmutablePair"));
        assertTrue(uoe.getMessage().contains("key"));
        assertFalse(uoe.getMessage().contains("value"));
    }

    @Test
    public void testSetValue() {
        UnsupportedOperationException uoe = assertThrows(
                UnsupportedOperationException.class,
                () -> mtxImmutablePair.setValue(23)
        );

        assertTrue(uoe.getMessage().contains("MtxImmutablePair"));
        assertTrue(uoe.getMessage().contains("value"));
        assertFalse(uoe.getMessage().contains("key"));
    }

    @Nested
    public class EqualsTests {

        @Test
        public void testEquals_otherPairNull() {
            assertFalse(mtxImmutablePair.equals(null));
        }

        @Test
        public void testEquals_otherPairNotAPair() {
            assertFalse(mtxImmutablePair.equals(new MtxBitColumn()));
        }

        @Test
        public void testEquals_notEqual() {
            MtxImmutablePair<String, Integer> otherPair = new MtxImmutablePair<>("g", 6);
            assertFalse(mtxImmutablePair.equals(otherPair));
        }

        @Test
        public void testEquals_equal() {
            MtxImmutablePair<String, Integer> otherPair = new MtxImmutablePair<>(SAMPLE_KEY, SAMPLE_VALUE);
            assertTrue(mtxImmutablePair.equals(otherPair));
        }
    }

    @Test
    public void testToString() {
        String mtxPairAsString = "<" + SAMPLE_KEY + " : " + SAMPLE_VALUE + ">";
        assertEquals(mtxPairAsString, mtxImmutablePair.toString());
    }
}
