package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxLinkedListTest {
    private MtxLinkedList<Object> mtxLinkedList;

    @BeforeEach
    public void setUp() {
        mtxLinkedList = new MtxLinkedList<>();
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedList.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxLinkedList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxLinkedList.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxLinkedList.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxLinkedList.toString());
        }

        @Test
        public void testAdd_fullList() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedList.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxLinkedList.toString());

            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedList.add("Sample Object " + (i + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxLinkedList.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedList.add(sample + i);
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
            assertEquals(5, mtxLinkedList.size());
            assertEquals(elementsBefore, mtxLinkedList.toString());

            assertTrue(mtxLinkedList.remove(elementToRemove));
            assertEquals(4, mtxLinkedList.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxLinkedList.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxLinkedList.size());
            assertEquals(elementsBefore, mtxLinkedList.toString());

            assertTrue(mtxLinkedList.remove(elementToRemove));
            assertEquals(4, mtxLinkedList.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxLinkedList.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxLinkedList.size());
            assertEquals(elementsBefore, mtxLinkedList.toString());

            assertFalse(mtxLinkedList.remove(elementToRemove));
            assertEquals(5, mtxLinkedList.size());

            assertEquals(elementsBefore, mtxLinkedList.toString());
        }
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxLinkedList.size());
            mtxLinkedList.add(i);
        }

        assertEquals(numberOfElements, mtxLinkedList.size());
    }

    @Test
    public void testConstructor_startingContents() {
        String[] sampleElements = {"One", "Two", "Three"};
        String expectedElements = "[One, Two, Three]";

        MtxLinkedList<String> customMtxLinkedList = new MtxLinkedList<>(sampleElements);
        assertEquals(expectedElements, customMtxLinkedList.toString());
    }

    @Test
    public void testIsEmpty() {
        String sample = "Sample Object";
        assertTrue(mtxLinkedList.isEmpty());

        mtxLinkedList.add(sample);
        assertFalse(mtxLinkedList.isEmpty());

        mtxLinkedList.remove(sample);
        assertTrue(mtxLinkedList.isEmpty());
    }
}
