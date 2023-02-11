package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
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

    @Test
    public void testClear() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxHashList.add(i);
        }
        assertEquals(numberOfElements, mtxHashList.size());

        mtxHashList.clear();
        assertEquals(0, mtxHashList.size());
        assertEquals("[]", mtxHashList.toString());
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
            mtxHashList.add(element);
        }
        mtxHashList.remove("Two");
        mtxHashList.add("Two");
        mtxHashList.remove("One");
        mtxHashList.add("One");
        assertEquals(sampleElements.size(), mtxHashList.size());

        for (Object iteration : mtxHashList) {
            assertTrue(sampleElements.contains(iteration));
            sampleElements.remove(iteration);
        }
        assertEquals(0, sampleElements.size());
    }

    @Test
    public void testSubList() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String startingContents = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxHashList.add(element);
            mtxHashList.remove(element);
            mtxHashList.add(element);
        }
        assertEquals(startingContents, mtxHashList.toString());

        String sub_0_to_3 = "[Zero, One, Two]";
        assertEquals(sub_0_to_3, mtxHashList.subList(0, 3).toString());
        String sub_1_to_5 = "[One, Two, Three, Four]";
        assertEquals(sub_1_to_5, mtxHashList.subList(1, 5).toString());
        String sub_3_to_7 = "[Three, Four, Five, Six]";
        assertEquals(sub_3_to_7, mtxHashList.subList(3, 7).toString());
        String sub_2_to_6 = "[Two, Three, Four, Five]";
        assertEquals(sub_2_to_6, mtxHashList.subList(2, 6).toString());

        assertEquals(startingContents, mtxHashList.subList(0, mtxHashList.size()).toString());

        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.subList(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.subList(1, 8));
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxHashList.toArray().length);

        String[] contents = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : contents) {
            mtxHashList.add(element);
            mtxHashList.remove(element);
            mtxHashList.add(element);
        }

        Object[] generatedArray = mtxHashList.toArray();

        assertEquals(contents.length, generatedArray.length);
        for (int i = 0; i < contents.length; i++) {
            assertEquals(contents[i], generatedArray[i]);
        }
    }

    @Test
    public void testRemoveAt() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : sampleElements) {
            mtxHashList.add(element);
            mtxHashList.remove(element);
            mtxHashList.add(element);
        }
        assertEquals(sampleElements.length, mtxHashList.size());
        for (int i = 0; i < sampleElements.length; i++) {
            assertEquals(sampleElements[i], mtxHashList.toArray()[i]);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.removeAt(mtxHashList.size()));

        String removeTest1 = "Three";
        String[] sampleElementsMinus1 = {"Zero", "One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest1, mtxHashList.removeAt(3));
        assertEquals(sampleElementsMinus1.length, mtxHashList.size());
        Object[] testArray1 = mtxHashList.toArray();
        for (int i = 0; i < testArray1.length; i++) {
            assertEquals(sampleElementsMinus1[i], testArray1[i]);
        }

        String removeTest2 = "Zero";
        String[] sampleElementsMinus2 = {"One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest2, mtxHashList.removeAt(0));
        assertEquals(sampleElementsMinus2.length, mtxHashList.size());
        Object[] testArray2 = mtxHashList.toArray();
        for (int i = 0; i < testArray2.length; i++) {
            assertEquals(sampleElementsMinus2[i], testArray2[i]);
        }

        String removeTest3 = "Six";
        String[] sampleElementsMinus3 = {"One", "Two", "Four", "Five"};
        assertEquals(removeTest3, mtxHashList.removeAt(4));
        assertEquals(sampleElementsMinus3.length, mtxHashList.size());
        Object[] testArray3 = mtxHashList.toArray();
        for (int i = 0; i < testArray3.length; i++) {
            assertEquals(sampleElementsMinus3[i], testArray3[i]);
        }

        String newElement = "Eight";
        String[] sampleElementsPlus1 = {"One", "Two", "Four", "Five", "Eight"};
        mtxHashList.add(newElement);
        assertEquals(mtxHashList.size(), sampleElementsPlus1.length);
        Object[] testArray4 = mtxHashList.toArray();
        for (int i = 0; i < testArray4.length; i++) {
            assertEquals(sampleElementsPlus1[i], testArray4[i]);
        }
    }

    @Test
    public void testSet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.set(-1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxHashList.set(mtxHashList.size(), null));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String sampleElementsString = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxHashList.add(element);
            mtxHashList.remove(element);
            mtxHashList.add(element);
        }
        assertEquals(sampleElementsString, mtxHashList.toString());

        String testSet1_oldVal = "Zero";
        String testSet1_newVal = "Thorough";
        int testSetIdx1 = 0;
        String sampleStringTest1 = "[Thorough, One, Two, Three, Four, Five, Six]";
        assertEquals(testSet1_oldVal, mtxHashList.set(testSetIdx1, testSet1_newVal));
        assertEquals(sampleStringTest1, mtxHashList.toString());

        String testSet2_oldVal = "Two";
        String testSet2_newVal = "Through";
        int testSetIdx2 = 2;
        String sampleStringTest2 = "[Thorough, One, Through, Three, Four, Five, Six]";
        assertEquals(testSet2_oldVal, mtxHashList.set(testSetIdx2, testSet2_newVal));
        assertEquals(sampleStringTest2, mtxHashList.toString());

        String testSet3_oldVal = "Six";
        String testSet3_newVal = "Zinc";
        int testSetIdx3 = 6;
        String sampleStringTest3 = "[Thorough, One, Through, Three, Four, Five, Zinc]";
        assertEquals(testSet3_oldVal, mtxHashList.set(testSetIdx3, testSet3_newVal));
        assertEquals(sampleStringTest3, mtxHashList.toString());
    }

    @Test
    public void testSort() {
        Integer[] unsortedArray = {2, 7, 4, 0, 9, 1, 3, 8, 5, 6};
        MtxHashList<Integer> integerMtxHashList = new MtxHashList<>(unsortedArray);
        integerMtxHashList.sort(Comparator.naturalOrder());

        for (int i = 0; i < unsortedArray.length; i++) {
            assertEquals(i, integerMtxHashList.get(i));
        }
    }
}
