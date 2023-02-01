package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

public final class MtxArrayListTest {
    @Spy
    private MtxArrayList<Object> mtxArrayListSpy;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mtxArrayListSpy = spy(MtxArrayList.class);
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayListSpy.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxArrayListSpy.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxArrayListSpy.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxArrayListSpy.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxArrayListSpy.toString());
        }

        @Test
        public void testAdd_fullList_initialCapacity() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayListSpy.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxArrayListSpy.toString());

            for (int j = 0; j < numberOfElements; j++) {
                mtxArrayListSpy.add("Sample Object " + (j + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxArrayListSpy.toString());
        }

        @Test
        public void testAdd_fullList_increasedCapacity() {
            int numberOfElements = 9;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayListSpy.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5, " +
                                         "Sample Object 6, Sample Object 7, Sample Object 8]";

            assertEquals(listExpectedState1, mtxArrayListSpy.toString());

            int numberOfNewElements = 6;
            for (int j = 0; j < numberOfNewElements; j++) {
                mtxArrayListSpy.add("Sample Object " + (j + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5, " +
                                         "Sample Object 6, Sample Object 7, Sample Object 8, " +
                                         "Sample Object 9, Sample Object 10, Sample Object 11, " +
                                         "Sample Object 12, Sample Object 13, Sample Object 14]";

            assertEquals(listExpectedState2, mtxArrayListSpy.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayListSpy.add(sample + i);
            }
        }

        @Test
        public void testRemove_emptyList() {
            MtxLinkedList<Object> mtxLinkedList_empty = new MtxLinkedList<>();

            assertEquals(0, mtxLinkedList_empty.size());
            String sample = "Sample Nonexistent Object";

            assertFalse(mtxLinkedList_empty.remove(sample));
        }

        @Test
        public void testRemove_firstElement() {
            String elementToRemove = "Sample Object 0";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxArrayListSpy.size());
            assertEquals(elementsBefore, mtxArrayListSpy.toString());

            assertTrue(mtxArrayListSpy.remove(elementToRemove));
            assertEquals(4, mtxArrayListSpy.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxArrayListSpy.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxArrayListSpy.size());
            assertEquals(elementsBefore, mtxArrayListSpy.toString());

            assertTrue(mtxArrayListSpy.remove(elementToRemove));
            assertEquals(4, mtxArrayListSpy.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxArrayListSpy.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxArrayListSpy.size());
            assertEquals(elementsBefore, mtxArrayListSpy.toString());

            assertFalse(mtxArrayListSpy.remove(elementToRemove));
            assertEquals(5, mtxArrayListSpy.size());

            assertEquals(elementsBefore, mtxArrayListSpy.toString());
        }
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxArrayListSpy.size());
            mtxArrayListSpy.add(i);
        }

        assertEquals(numberOfElements, mtxArrayListSpy.size());
    }

    @Test
    public void testGetSpaceLeftBeforeArrayIncrease() {
        int numberOfElements = 5;
        int defaultCapacity = 10;
        for (int i = 0; i < numberOfElements; i++) {
            int expectedSizeLeft = defaultCapacity - i;
            assertEquals(expectedSizeLeft, mtxArrayListSpy.getSpaceLeftBeforeArrayIncrease());
            mtxArrayListSpy.add(i);
        }
    }
}
