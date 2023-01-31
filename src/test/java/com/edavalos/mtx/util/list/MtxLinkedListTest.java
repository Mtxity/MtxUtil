package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

public final class MtxLinkedListTest {
    @Spy
    private MtxLinkedList<Object> mtxLinkedListSpy;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mtxLinkedListSpy = spy(MtxLinkedList.class);
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedListSpy.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxLinkedListSpy.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxLinkedListSpy.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxLinkedListSpy.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxLinkedListSpy.toString());
        }

        @Test
        public void testAdd_fullList() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedListSpy.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxLinkedListSpy.toString());

            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedListSpy.add("Sample Object " + (i + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxLinkedListSpy.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxLinkedListSpy.add(sample + i);
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
            assertEquals(5, mtxLinkedListSpy.size());
            assertEquals(elementsBefore, mtxLinkedListSpy.toString());

            assertTrue(mtxLinkedListSpy.remove(elementToRemove));
            assertEquals(4, mtxLinkedListSpy.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxLinkedListSpy.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxLinkedListSpy.size());
            assertEquals(elementsBefore, mtxLinkedListSpy.toString());

            assertTrue(mtxLinkedListSpy.remove(elementToRemove));
            assertEquals(4, mtxLinkedListSpy.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxLinkedListSpy.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxLinkedListSpy.size());
            assertEquals(elementsBefore, mtxLinkedListSpy.toString());

            assertFalse(mtxLinkedListSpy.remove(elementToRemove));
            assertEquals(5, mtxLinkedListSpy.size());

            assertEquals(elementsBefore, mtxLinkedListSpy.toString());
        }
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxLinkedListSpy.size());
            mtxLinkedListSpy.add(i);
        }

        assertEquals(numberOfElements, mtxLinkedListSpy.size());
    }
}
