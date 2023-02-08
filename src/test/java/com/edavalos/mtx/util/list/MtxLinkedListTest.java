package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        for (int j = 0; j < numberOfElements - 10; j++) {
            mtxLinkedList.remove(j);
        }
        assertEquals(numberOfElements - 10, mtxLinkedList.size());
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

    @Test
    public void testContains_yes() {
        mtxLinkedList.add('a');
        mtxLinkedList.add('b');
        mtxLinkedList.add('c');

        assertTrue(mtxLinkedList.contains('b'));
    }

    @Test
    public void testContains_no() {
        mtxLinkedList.add('a');
        mtxLinkedList.add('b');
        mtxLinkedList.add('c');

        assertFalse(mtxLinkedList.contains('d'));
    }

    @Test
    public void testCountOccurrences() {
        assertEquals(0, mtxLinkedList.countOccurrences('a'));

        for (int i = 0; i < 15; i++) {
            mtxLinkedList.add('a');
            mtxLinkedList.add('b');

            assertEquals(i + 1, mtxLinkedList.countOccurrences('a'));
            assertEquals((i + 1) * 2, mtxLinkedList.size());
        }
    }

    @Test
    public void testGet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxLinkedList.get(0));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
        }

        assertEquals("Zero", mtxLinkedList.get(0));
        assertEquals("Two", mtxLinkedList.get(2));
        assertEquals("Four", mtxLinkedList.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxLinkedList.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxLinkedList.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxLinkedList.get(-1));
    }

    // TODO: Move this method to MtxListTest
    @Test
    public void testEquals() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        String[] otherList1 = {"Zero", "One", "Two", "Three", "Four", "Five"};
        String[] otherList2 = {"Thero", "Won", "Too", "Free", "For"};
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
        }

        assertTrue(mtxLinkedList.equals(new MtxLinkedList<>(sampleElements)));
        assertTrue(mtxLinkedList.equals(List.of(sampleElements)));
        assertTrue(mtxLinkedList.equals(sampleElements));

        assertFalse(mtxLinkedList.equals(new MtxLinkedList<>(otherList1)));
        assertFalse(mtxLinkedList.equals(List.of(otherList1)));
        assertFalse(mtxLinkedList.equals(otherList1));

        assertFalse(mtxLinkedList.equals(new MtxLinkedList<>(otherList2)));
        assertFalse(mtxLinkedList.equals(List.of(otherList2)));
        assertFalse(mtxLinkedList.equals(otherList2));

        assertFalse(mtxLinkedList.equals(mtxLinkedList.toString()));
    }

    @Test
    public void testHashCode() {
        List<String> sampleList = new LinkedList<>(){
            {
                add("One");
                add("Two");
                add("Six");
            }
        };

        mtxLinkedList = new MtxLinkedList<>(sampleList.toArray(new String[0]));

        assertEquals(sampleList.hashCode(), mtxLinkedList.hashCode());
    }

    @Test
    public void testIndexOf() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
        }

        for (String sampleElement : sampleElements) {
            assertEquals(sampleElement, mtxLinkedList.get(mtxLinkedList.indexOf(sampleElement)));
        }
        assertEquals(-1, mtxLinkedList.indexOf("Five"));
    }

    @Test
    public void testClear() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxLinkedList.add(i);
        }
        assertEquals(numberOfElements, mtxLinkedList.size());

        mtxLinkedList.clear();
        assertEquals(0, mtxLinkedList.size());
        assertEquals("[]", mtxLinkedList.toString());
    }

    @Test
    public void testIterator() {
        List<String> sampleElements = new LinkedList<>(){
            {
                add("One");
                add("Two");
                add("Three");
                add("Four");
            }
        };
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
        }
        assertEquals(sampleElements.size(), mtxLinkedList.size());

        for (Object iteration : mtxLinkedList) {
            assertTrue(sampleElements.contains(iteration));
            sampleElements.remove(iteration);
        }
        assertEquals(0, sampleElements.size());
    }
}
