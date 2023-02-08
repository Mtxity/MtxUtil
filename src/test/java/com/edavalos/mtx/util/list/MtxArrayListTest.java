package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxArrayListTest {
    private MtxArrayList<Object> mtxArrayList;

    @BeforeEach
    public void setUp() {
        mtxArrayList = new MtxArrayList<>();
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayList.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxArrayList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxArrayList.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxArrayList.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxArrayList.toString());
        }

        @Test
        public void testAdd_fullList_initialCapacity() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayList.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxArrayList.toString());

            for (int j = 0; j < numberOfElements; j++) {
                mtxArrayList.add("Sample Object " + (j + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxArrayList.toString());
        }

        @Test
        public void testAdd_fullList_increasedCapacity() {
            int numberOfElements = 9;
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayList.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5, " +
                                         "Sample Object 6, Sample Object 7, Sample Object 8]";

            assertEquals(listExpectedState1, mtxArrayList.toString());

            int numberOfNewElements = 6;
            for (int j = 0; j < numberOfNewElements; j++) {
                mtxArrayList.add("Sample Object " + (j + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5, " +
                                         "Sample Object 6, Sample Object 7, Sample Object 8, " +
                                         "Sample Object 9, Sample Object 10, Sample Object 11, " +
                                         "Sample Object 12, Sample Object 13, Sample Object 14]";

            assertEquals(listExpectedState2, mtxArrayList.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxArrayList.add(sample + i);
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
            assertEquals(5, mtxArrayList.size());
            assertEquals(elementsBefore, mtxArrayList.toString());

            assertTrue(mtxArrayList.remove(elementToRemove));
            assertEquals(4, mtxArrayList.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxArrayList.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxArrayList.size());
            assertEquals(elementsBefore, mtxArrayList.toString());

            assertTrue(mtxArrayList.remove(elementToRemove));
            assertEquals(4, mtxArrayList.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxArrayList.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxArrayList.size());
            assertEquals(elementsBefore, mtxArrayList.toString());

            assertFalse(mtxArrayList.remove(elementToRemove));
            assertEquals(5, mtxArrayList.size());

            assertEquals(elementsBefore, mtxArrayList.toString());
        }
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxArrayList.size());
            mtxArrayList.add(i);
        }
        assertEquals(numberOfElements, mtxArrayList.size());

        for (int j = 0; j < numberOfElements - 10; j++) {
            mtxArrayList.remove(j);
        }
        assertEquals(numberOfElements - 10, mtxArrayList.size());
    }

    @Test
    public void testGetSpaceLeftBeforeArrayIncrease() {
        int numberOfElements = 5;
        int defaultCapacity = 10;
        for (int i = 0; i < numberOfElements; i++) {
            int expectedSizeLeft = defaultCapacity - i;
            assertEquals(expectedSizeLeft, mtxArrayList.getSpaceLeftBeforeArrayIncrease());
            mtxArrayList.add(i);
        }
    }

    @Test
    public void testConstructor_customSize() {
        MtxArrayList<Integer> customMtxArrayList;
        int numberOfTests = 10;
        for (int i = 0; i < numberOfTests; i++) {
            customMtxArrayList = new MtxArrayList<>(i);
            assertEquals(i, customMtxArrayList.getSpaceLeftBeforeArrayIncrease());
        }
    }

    @Test
    public void testConstructor_startingContents() {
        String[] sampleElements = {"One", "Two", "Three"};
        String expectedElements = "[One, Two, Three]";

        MtxArrayList<String> customMtxLinkedList = new MtxArrayList<>(sampleElements);
        assertEquals(expectedElements, customMtxLinkedList.toString());
    }

    @Test
    public void testIsEmpty() {
        String sample = "Sample Object";
        assertTrue(mtxArrayList.isEmpty());

        mtxArrayList.add(sample);
        assertFalse(mtxArrayList.isEmpty());

        mtxArrayList.remove(sample);
        assertTrue(mtxArrayList.isEmpty());
    }

    @Test
    public void testContains_yes() {
        mtxArrayList.add('a');
        mtxArrayList.add('b');
        mtxArrayList.add('c');

        assertTrue(mtxArrayList.contains('b'));
    }

    @Test
    public void testContains_no() {
        mtxArrayList.add('a');
        mtxArrayList.add('b');
        mtxArrayList.add('c');

        assertFalse(mtxArrayList.contains('d'));
    }

    @Test
    public void testCountOccurrences() {
        assertEquals(0, mtxArrayList.countOccurrences('a'));

        for (int i = 0; i < 15; i++) {
            mtxArrayList.add('a');
            mtxArrayList.add('b');

            assertEquals(i + 1, mtxArrayList.countOccurrences('a'));
            assertEquals((i + 1) * 2, mtxArrayList.size());
        }
    }

    @Test
    public void testGet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.get(0));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxArrayList.add(element);
        }

        assertEquals("Zero", mtxArrayList.get(0));
        assertEquals("Two", mtxArrayList.get(2));
        assertEquals("Four", mtxArrayList.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.get(-1));
    }

    @Test
    public void testHashCode() {
        List<String> sampleList = new ArrayList<>(){
            {
                add("One");
                add("Two");
                add("Six");
            }
        };

        mtxArrayList = new MtxArrayList<>(sampleList.toArray(new String[0]));

        assertEquals(sampleList.hashCode(), mtxArrayList.hashCode());
    }

    @Test
    public void testIndexOf() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxArrayList.add(element);
        }

        for (String sampleElement : sampleElements) {
            assertEquals(sampleElement, mtxArrayList.get(mtxArrayList.indexOf(sampleElement)));
        }
        assertEquals(-1, mtxArrayList.indexOf("Five"));
    }

    @Test
    public void testClear() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxArrayList.add(i);
        }
        assertEquals(numberOfElements, mtxArrayList.size());

        mtxArrayList.clear();
        assertEquals(0, mtxArrayList.size());
        assertEquals("[]", mtxArrayList.toString());
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
            mtxArrayList.add(element);
        }
        assertEquals(sampleElements.size(), mtxArrayList.size());

        for (Object iteration : mtxArrayList) {
            assertTrue(sampleElements.contains(iteration));
            sampleElements.remove(iteration);
        }
        assertEquals(0, sampleElements.size());
    }
}
