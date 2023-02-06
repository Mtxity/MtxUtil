package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxHashListTest {
    private MtxHashList<Object> mtxHashList;

    @BeforeEach
    public void setUp() {
        mtxHashList = new MtxHashList<>();
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxHashList.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxHashList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxHashList.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxHashList.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxHashList.toString());
        }

        @Test
        public void testAdd_fullList() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxHashList.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxHashList.toString());

            for (int i = 0; i < numberOfElements; i++) {
                mtxHashList.add("Sample Object " + (i + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxHashList.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxHashList.add(sample + i);
            }
        }

        @Test
        public void testRemove_emptyList() {
            MtxHashList<Object> mtxHashList_empty = new MtxHashList<>();

            assertEquals(0, mtxHashList_empty.size());
            String sample = "Sample Nonexistent Object";

            assertFalse(mtxHashList_empty.remove(sample));
        }

        @Test
        public void testRemove_firstElement() {
            String elementToRemove = "Sample Object 0";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxHashList.size());
            assertEquals(elementsBefore, mtxHashList.toString());

            assertTrue(mtxHashList.remove(elementToRemove));
            assertEquals(4, mtxHashList.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxHashList.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxHashList.size());
            assertEquals(elementsBefore, mtxHashList.toString());

            assertTrue(mtxHashList.remove(elementToRemove));
            assertEquals(4, mtxHashList.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxHashList.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxHashList.size());
            assertEquals(elementsBefore, mtxHashList.toString());

            assertFalse(mtxHashList.remove(elementToRemove));
            assertEquals(5, mtxHashList.size());

            assertEquals(elementsBefore, mtxHashList.toString());
        }

        @Test
        public void testRemove_elementPreviouslyDeleted() {
            String elementToRemove = "Sample Object 2";

            assertTrue(mtxHashList.remove(elementToRemove));
            assertFalse(mtxHashList.remove(elementToRemove));
        }
    }
    
    @Test
    public void testAddAndRemove() {
        assertEquals("[]", mtxHashList.toString());
        assertEquals(0, mtxHashList.size());
        
        for (char x = 'a'; x < 'f'; x++) {
            mtxHashList.add(x);
        }
        assertEquals("[a, b, c, d, e]", mtxHashList.toString());
        assertEquals(5, mtxHashList.size());

        mtxHashList.remove('b');
        mtxHashList.remove('d');
        assertEquals("[a, c, e]", mtxHashList.toString());
        assertEquals(3, mtxHashList.size());

        mtxHashList.add('b');
        mtxHashList.add('g');
        assertEquals("[a, c, e, b, g]", mtxHashList.toString());
        assertEquals(5, mtxHashList.size());
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxHashList.size());
            mtxHashList.add(i);
        }
        assertEquals(numberOfElements, mtxHashList.size());

        for (int j = 0; j < numberOfElements - 10; j++) {
            mtxHashList.remove(j);
        }
        assertEquals(numberOfElements - 10, mtxHashList.size());
    }

    @Test
    public void testConstructor_startingContents() {
        String[] sampleElements = {"One", "Two", "Three"};
        String expectedElements = "[One, Two, Three]";

        MtxHashList<String> customMtxHashList = new MtxHashList<>(sampleElements);
        assertEquals(expectedElements, customMtxHashList.toString());
    }

    @Test
    public void testIsEmpty() {
        String sample = "Sample Object";
        assertTrue(mtxHashList.isEmpty());

        mtxHashList.add(sample);
        assertFalse(mtxHashList.isEmpty());

        mtxHashList.remove(sample);
        assertTrue(mtxHashList.isEmpty());
    }

    @Test
    public void testContains_yes() {
        mtxHashList.add('a');
        mtxHashList.add('b');
        mtxHashList.add('c');

        mtxHashList.remove('b');
        mtxHashList.add('b');

        assertTrue(mtxHashList.contains('b'));
    }

    @Test
    public void testContains_no() {
        mtxHashList.add('a');
        mtxHashList.add('b');
        mtxHashList.add('c');

        mtxHashList.remove('b');
        mtxHashList.add('b');
        mtxHashList.remove('b');
        mtxHashList.add('b');
        mtxHashList.remove('b');

        assertFalse(mtxHashList.contains('b'));
    }

    @Test
    public void testCountOccurrences() {
        assertEquals(0, mtxHashList.countOccurrences('a'));

        for (int i = 0; i < 15; i++) {
            mtxHashList.add('a');
            mtxHashList.add('b');

            assertEquals(i + 1, mtxHashList.countOccurrences('a'));
            assertEquals((i + 1) * 2, mtxHashList.size());
        }
    }

    @Test
    public void testGet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.get(0));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxHashList.add(element);
        }

        assertEquals("Zero", mtxHashList.get(0));
        assertEquals("Two", mtxHashList.get(2));
        assertEquals("Four", mtxHashList.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.get(-1));

        mtxHashList.remove("One");
        mtxHashList.remove("Two");
        mtxHashList.add("One");
        assertEquals("[Zero, Three, Four, One]", mtxHashList.toString());

        assertEquals("Zero", mtxHashList.get(0));
        assertEquals("Three", mtxHashList.get(1));
        assertEquals("Four", mtxHashList.get(2));
        assertEquals("One", mtxHashList.get(3));
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

        mtxHashList = new MtxHashList<>(sampleList.toArray(new String[0]));
        mtxHashList.remove("Two");
        mtxHashList.remove("Six");
        mtxHashList.add("Two");
        mtxHashList.add("Six");

        assertEquals(sampleList.hashCode(), mtxHashList.hashCode());
    }

    @Test
    public void testIndexOf() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxHashList.add(element);
        }
        for (String element : sampleElements) {
            mtxHashList.remove(element);
        }
        for (String element : sampleElements) {
            mtxHashList.add(element);
        }

        for (String sampleElement : sampleElements) {
            assertEquals(sampleElement, mtxHashList.get(mtxHashList.indexOf(sampleElement)));
        }
        assertEquals(-1, mtxHashList.indexOf("Five"));
    }
}
