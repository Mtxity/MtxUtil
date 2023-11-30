package com.edavalos.mtx.util.db;

import com.edavalos.mtx.util.grouping.pair.MtxImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MtxRangedIntegerTest {
    private MtxRangedInteger mtxRInt;

    @Nested
    class TestConstructor {

        @Test
        public void testConstructor_noStartingValue_noException() {
            mtxRInt = new MtxRangedInteger(-5, 5, false);
            assertEquals(-5, mtxRInt.getMin());
            assertEquals(5, mtxRInt.getMax());
            assertThrows(NullPointerException.class, () -> mtxRInt.value());
            assertFalse(mtxRInt.throwsException());
        }

        @Test
        public void testConstructor_noStartingValue_throwsException() {
            mtxRInt = new MtxRangedInteger(-5, 5, true);
            assertEquals(-5, mtxRInt.getMin());
            assertEquals(5, mtxRInt.getMax());
            assertThrows(NullPointerException.class, () -> mtxRInt.value());
            assertTrue(mtxRInt.throwsException());
        }

        @Test
        public void testConstructor_withStartingValue_noException() {
            mtxRInt = new MtxRangedInteger(-5, 5, false, 0);
            assertEquals(-5, mtxRInt.getMin());
            assertEquals(5, mtxRInt.getMax());
            assertEquals(0, mtxRInt.value());
            assertFalse(mtxRInt.throwsException());
        }

        @Test
        public void testConstructor_withStartingValue_throwsException() {
            mtxRInt = new MtxRangedInteger(-5, 5, true, 0);
            assertEquals(-5, mtxRInt.getMin());
            assertEquals(5, mtxRInt.getMax());
            assertEquals(0, mtxRInt.value());
            assertTrue(mtxRInt.throwsException());
        }

        @Test
        public void testConstructor_maxGreaterThanMin() {
            String errMsg = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MtxRangedInteger(5, -5, false)
            ).getMessage();
            assertEquals("Minimum bound must be smaller than maximum bound", errMsg);
        }
    }

    @Test
    public void testGetValueClosestToLimit() {
        int min = 2;
        int max = 8;
        mtxRInt = new MtxRangedInteger(min, max, false);

        assertEquals(min, mtxRInt.getValueClosestToLimit(1));
        assertEquals(max, mtxRInt.getValueClosestToLimit(100));
        assertEquals(4, mtxRInt.getValueClosestToLimit(4));
    }

    @Nested
    class TestSetValue {

        @Test
        public void testSetValue_throwException() {
            mtxRInt = new MtxRangedInteger(2, 4, true);
            String errMsg = assertThrows(IllegalArgumentException.class, () -> mtxRInt.setValue(6)).getMessage();
            assertEquals("Value outside range: 6", errMsg);
        }

        @Test
        public void testSetValue_noException() {
            int min = 2;
            int max = 4;
            mtxRInt = new MtxRangedInteger(min, max, false);
            mtxRInt.setValue(1);
            assertEquals(min, mtxRInt.value());
            mtxRInt.setValue(7);
            assertEquals(max, mtxRInt.value());
            mtxRInt.setValue(3);
            assertEquals(3, mtxRInt.value());
        }
    }

    @Nested
    class TestValue {

        @BeforeEach
        public void setUp() {
            mtxRInt = new MtxRangedInteger(0, 10, true);
        }

        @Test
        public void testValue_hasValue() {
            mtxRInt.setValue(5);
            assertEquals(5, mtxRInt.value());
        }

        @Test
        public void testValue_noValue() {
            String errMsg = assertThrows(NullPointerException.class, () -> mtxRInt.value()).getMessage();
            assertEquals("MtxRangedInteger has no value", errMsg);
        }
    }

    @Test
    public void testGetters() {
        int min = 300;
        int max = 600;
        mtxRInt = new MtxRangedInteger(min, max, true);
        assertEquals(min, mtxRInt.getMin());
        assertEquals(max, mtxRInt.getMax());
        assertEquals(new MtxImmutablePair<>(min, max), mtxRInt.getRange());
        assertTrue(mtxRInt.throwsException());
    }

    @Nested
    class TestMathMethods {
        private static final int VAL = 6;

        @BeforeEach
        public void setUp() {
            mtxRInt = new MtxRangedInteger(0, 20, false, VAL);
        }

        @Test
        public void testAdd() {
            int newVal = VAL + 3; //9
            assertEquals(newVal, mtxRInt.add(3));
            assertEquals(newVal, mtxRInt.value());
        }

        @Test
        public void testSubtract() {
            int newVal = VAL - 3; //3
            assertEquals(newVal, mtxRInt.subtract(3));
            assertEquals(newVal, mtxRInt.value());
        }

        @Test
        public void testMultiply() {
            int newVal = VAL * 2; //12
            assertEquals(newVal, mtxRInt.multiply(2));
            assertEquals(newVal, mtxRInt.value());
        }

        @Test
        public void testDivideBy_wholeNumberResult() {
            int newVal = VAL / 3; //2
            assertEquals(newVal, mtxRInt.divideBy(3));
            assertEquals(newVal, mtxRInt.value());
        }

        @Test
        public void testDivideBy_decimalResult() {
            int newVal = VAL / 4; //1.5 (1 as an int)
            assertEquals(newVal, mtxRInt.divideBy(4));
            assertEquals(newVal, mtxRInt.value());
        }
    }

    @Nested
    class TestConversions {
        private static final int VAL = 7;

        @BeforeEach
        public void setUp() {
            mtxRInt = new MtxRangedInteger(0, 20, false, VAL);
        }

        @Test
        public void testByteValue() {
            assertEquals((byte) VAL, mtxRInt.byteValue());
        }

        @Test
        public void testShortValue() {
            assertEquals((short) VAL, mtxRInt.shortValue());
        }

        @Test
        public void testLongValue() {
            assertEquals((long) VAL, mtxRInt.longValue());
        }

        @Test
        public void testFloatValue() {
            assertEquals((float) VAL, mtxRInt.floatValue());
        }

        @Test
        public void testDoubleValue() {
            assertEquals((double) VAL, mtxRInt.doubleValue());
        }

        @Test
        public void testIntValue_noValue() {
            assertEquals(0, new MtxRangedInteger(0, 1, false).intValue());
        }

        @Test
        public void testIntValue_hasValue() {
            assertEquals(VAL, mtxRInt.intValue());
        }
    }

    @Test
    public void testToString() {
        String newVal = "43";
        mtxRInt = new MtxRangedInteger(42, 44, true, Integer.parseInt(newVal));
        assertEquals(newVal, mtxRInt.toString());
    }

    @Nested
    class TestEquals {
        private static final int VAL = 8;

        @BeforeEach
        public void setUp() {
            mtxRInt = new MtxRangedInteger(0, 20, false, VAL);
        }

        @Test
        public void testEquals_true() {
            MtxRangedInteger otherRInt = new MtxRangedInteger(0 + 2, 20 + 2, true, VAL);
            assertTrue(mtxRInt.equals(otherRInt));
        }

        @Test
        public void testEquals_false() {
            MtxRangedInteger otherRInt = new MtxRangedInteger(0 + 2, 20 + 2, true, VAL + 2);
            assertFalse(mtxRInt.equals(otherRInt));
        }

        @Test
        public void testEquals_differentTypes() {
            assertFalse(mtxRInt.equals(new MtxUnsignedInteger(VAL)));
        }
    }

    @Test
    public void testHashCode() {
        int val = 49;
        assertEquals(Integer.hashCode(val), new MtxRangedInteger(48, 58, false, val).hashCode());
    }

    @Test
    public void testCompareTo() {
        mtxRInt = new MtxRangedInteger(0, 10, true);
        for (int i = 1; i < 9; i++) {
            assertEquals(
                    Integer.compare(mtxRInt.intValue(), i),
                    mtxRInt.compareTo(new MtxRangedInteger(-1, 11, false, i))
            );
            assertEquals(
                    Integer.compare(i, mtxRInt.intValue()),
                    new MtxRangedInteger(-1, 11, false, i).compareTo(mtxRInt)
            );
        }
    }

    @Nested
    class TestParseRangedInt {

        @Test
        public void testParseRangedInt() {
            try {
                mtxRInt = MtxRangedInteger.parseRangedInt("45", 44, 46);
                assertEquals(45, mtxRInt.value());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        public void testParseRangedInt_invalidStr() {
            assertThrows(
                    NumberFormatException.class,
                    () -> MtxRangedInteger.parseRangedInt("4g5", 44, 46)
            );
        }

        @Test
        public void testParseRangedInt_outOfBounds() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxRangedInteger.parseRangedInt("47", 44, 46)
            );
        }
    }
}
