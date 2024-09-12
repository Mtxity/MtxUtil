package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxRunningTotalTest {
    private static final int[] SAMPLE_VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    private MtxRunningTotal mtxRunningTotal;

    @BeforeEach
    public void setUp() {
        mtxRunningTotal = new MtxRunningTotal(10);
        for (int sampleValue : SAMPLE_VALUES) {
            mtxRunningTotal.add(sampleValue);
        }
    }

    @Nested
    public class ConstructorTests {
        private MtxRunningTotal mtxRunningTotal_constructorTests;

        @Test
        public void testConstructor_defaultLimit() {
            mtxRunningTotal_constructorTests = new MtxRunningTotal();
            assertEquals(100, mtxRunningTotal_constructorTests.limit());
        }

        @Test
        public void testConstructor_specifiedLimit() {
            mtxRunningTotal_constructorTests = new MtxRunningTotal(5);
            assertEquals(5, mtxRunningTotal_constructorTests.limit());
        }

        @Test
        public void testConstructor_specifiedValues_lessThanMax() {
            mtxRunningTotal_constructorTests = new MtxRunningTotal(() -> new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < 25;
                }

                @Override
                public Integer next() {
                    return i++;
                }
            });

            assertEquals(25, mtxRunningTotal_constructorTests.limit());
        }

        @Test
        public void testConstructor_specifiedValues_greaterThanMax() {
            mtxRunningTotal_constructorTests = new MtxRunningTotal(() -> new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < 10000;
                }

                @Override
                public Integer next() {
                    return i++;
                }
            });

            assertEquals(10000, mtxRunningTotal_constructorTests.limit());
        }
    }

    @Nested
    public class CalculationsTests {

        @Test
        public void testGetAvg() {
            assertEquals(10.5, mtxRunningTotal.getAvg());
        }

        @Test
        public void testGetMedian_even() {
            assertEquals(10.5, mtxRunningTotal.getMedian());
        }

        @Test
        public void testGetMedian_odd() {
            mtxRunningTotal = new MtxRunningTotal(9);
            for (int sampleValue : SAMPLE_VALUES) {
                mtxRunningTotal.add(sampleValue);
            }
            assertEquals(11, mtxRunningTotal.getMedian());
        }

        @Test
        public void testGetMax() {
            assertEquals(15, mtxRunningTotal.getMax());
        }

        @Test
        public void testGetMin() {
            assertEquals(6, mtxRunningTotal.getMin());
        }

        @Test
        public void testGetSum() {
            assertEquals(105, mtxRunningTotal.getSum());
        }

        @Test
        public void testRange() {
            assertEquals(9, mtxRunningTotal.getRange());
        }
    }

    @Nested
    public class AddTests {

        @Test
        public void testAdd_oneValue_underLimit() {
            mtxRunningTotal = new MtxRunningTotal(17);
            for (int sampleValue : SAMPLE_VALUES) {
                mtxRunningTotal.add(sampleValue);
            }

            assertEquals(10.5, mtxRunningTotal.getAvg());
            assertEquals(10.5, mtxRunningTotal.getMedian());
            assertEquals(15, mtxRunningTotal.getMax());
            assertEquals(6, mtxRunningTotal.getMin());
            assertEquals(105, mtxRunningTotal.getSum());

            mtxRunningTotal.add(16);
            assertEquals(11.5, mtxRunningTotal.getAvg());
            assertEquals(11.5, mtxRunningTotal.getMedian());
            assertEquals(16, mtxRunningTotal.getMax());
            assertEquals(7, mtxRunningTotal.getMin());
            assertEquals(115, mtxRunningTotal.getSum());
        }

        @Test
        public void testAdd_oneValue_overLimit() {
            assertEquals(10.5, mtxRunningTotal.getAvg());
            assertEquals(10.5, mtxRunningTotal.getMedian());
            assertEquals(15, mtxRunningTotal.getMax());
            assertEquals(6, mtxRunningTotal.getMin());
            assertEquals(105, mtxRunningTotal.getSum());

            mtxRunningTotal.add(16);
            assertEquals(11.5, mtxRunningTotal.getAvg());
            assertEquals(11.5, mtxRunningTotal.getMedian());
            assertEquals(16, mtxRunningTotal.getMax());
            assertEquals(7, mtxRunningTotal.getMin());
            assertEquals(115, mtxRunningTotal.getSum());
        }

        @Test
        public void testAdd_multipleValues() {
            assertEquals(10.5, mtxRunningTotal.getAvg());
            assertEquals(10.5, mtxRunningTotal.getMedian());
            assertEquals(15, mtxRunningTotal.getMax());
            assertEquals(6, mtxRunningTotal.getMin());
            assertEquals(105, mtxRunningTotal.getSum());

            mtxRunningTotal.add(() -> new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < 3;
                }

                @Override
                public Integer next() {
                    return 16 + i++;
                }
            });
            assertEquals(13.5, mtxRunningTotal.getAvg());
            assertEquals(13.5, mtxRunningTotal.getMedian());
            assertEquals(18, mtxRunningTotal.getMax());
            assertEquals(9, mtxRunningTotal.getMin());
            assertEquals(135, mtxRunningTotal.getSum());
        }
    }

    @Test
    public void testRemove() {
        assertEquals(10.5, mtxRunningTotal.getAvg());
        assertEquals(10.5, mtxRunningTotal.getMedian());
        assertEquals(15, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(105, mtxRunningTotal.getSum());

        mtxRunningTotal.remove(10);
        assertEquals(10.555555555555555, mtxRunningTotal.getAvg());
        assertEquals(11, mtxRunningTotal.getMedian());
        assertEquals(15, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(95, mtxRunningTotal.getSum());
    }

    @Test
    public void testRemoveLast_one() {
        assertEquals(10.5, mtxRunningTotal.getAvg());
        assertEquals(10.5, mtxRunningTotal.getMedian());
        assertEquals(15, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(105, mtxRunningTotal.getSum());

        mtxRunningTotal.removeLast();
        assertEquals(10, mtxRunningTotal.getAvg());
        assertEquals(10, mtxRunningTotal.getMedian());
        assertEquals(14, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(90, mtxRunningTotal.getSum());
    }

    @Test
    public void testRemoveLast_multiple() {
        assertEquals(10.5, mtxRunningTotal.getAvg());
        assertEquals(10.5, mtxRunningTotal.getMedian());
        assertEquals(15, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(105, mtxRunningTotal.getSum());

        mtxRunningTotal.removeLast(3);
        assertEquals(9, mtxRunningTotal.getAvg());
        assertEquals(9, mtxRunningTotal.getMedian());
        assertEquals(12, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(63, mtxRunningTotal.getSum());
    }

    @Test
    public void testRemoveLast_all() {
        assertEquals(10.5, mtxRunningTotal.getAvg());
        assertEquals(10.5, mtxRunningTotal.getMedian());
        assertEquals(15, mtxRunningTotal.getMax());
        assertEquals(6, mtxRunningTotal.getMin());
        assertEquals(105, mtxRunningTotal.getSum());

        mtxRunningTotal.removeLast(20);
        assertEquals(0, mtxRunningTotal.getAvg());
        assertEquals(0, mtxRunningTotal.getMedian());
        assertEquals(0, mtxRunningTotal.getMax());
        assertEquals(0, mtxRunningTotal.getMin());
        assertEquals(0, mtxRunningTotal.getSum());
    }

    @Test
    public void testLength() {
        final int testCases = 50;
        mtxRunningTotal = new MtxRunningTotal();
        for (int i = 0; i < Math.min(testCases, 100000); i++) {
            mtxRunningTotal.add(i);
            assertEquals(i + 1, mtxRunningTotal.length());
        }
    }

    @Test
    public void testLimit() {
        final int testCases = 50;
        for (int i = 0; i < testCases; i++) {
            assertEquals(i, new MtxRunningTotal(i).limit());
        }
    }

    @Test
    public void testHasDuplicates_allList() {
        mtxRunningTotal = new MtxRunningTotal();
        assertFalse(mtxRunningTotal.hasDuplicates());

        mtxRunningTotal.add(10);
        mtxRunningTotal.add(20);
        mtxRunningTotal.add(30);
        assertFalse(mtxRunningTotal.hasDuplicates());

        mtxRunningTotal.add(30);
        assertTrue(mtxRunningTotal.hasDuplicates());
        assertTrue(mtxRunningTotal.hasDuplicates(5));
    }

    @Test
    public void testHasDuplicates_partialList() {
        mtxRunningTotal = new MtxRunningTotal();
        mtxRunningTotal.add(10);
        mtxRunningTotal.add(20);
        mtxRunningTotal.add(20);
        mtxRunningTotal.add(30);
        mtxRunningTotal.add(40);
        mtxRunningTotal.add(50);
        assertFalse(mtxRunningTotal.hasDuplicates(4));
        assertTrue(mtxRunningTotal.hasDuplicates(5));

        mtxRunningTotal.add(60);
        assertFalse(mtxRunningTotal.hasDuplicates(5));
        assertTrue(mtxRunningTotal.hasDuplicates(6));
    }
}
