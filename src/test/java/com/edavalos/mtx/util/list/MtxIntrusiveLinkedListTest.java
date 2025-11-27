package com.edavalos.mtx.util.list;

import com.edavalos.mtx.util.list.line.MtxStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxIntrusiveLinkedListTest {
    private MtxIntrusiveLinkedList<Object> mtxIntrusiveLinkedList;

    @BeforeEach
    public void setUp() {
        mtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>();
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxIntrusiveLinkedList.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxIntrusiveLinkedList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxIntrusiveLinkedList.toString();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AddTests {

        @Test
        public void testAdd_emptyList() {
            mtxIntrusiveLinkedList.add("Sample Object");
            String listExpectedState = "[Sample Object]";

            assertEquals(listExpectedState, mtxIntrusiveLinkedList.toString());
        }

        @Test
        public void testAdd_fullList() {
            int numberOfElements = 3;
            for (int i = 0; i < numberOfElements; i++) {
                mtxIntrusiveLinkedList.add("Sample Object " + i);
            }
            String listExpectedState1 = "[Sample Object 0, Sample Object 1, Sample Object 2]";

            assertEquals(listExpectedState1, mtxIntrusiveLinkedList.toString());

            for (int i = 0; i < numberOfElements; i++) {
                mtxIntrusiveLinkedList.add("Sample Object " + (i + numberOfElements));
            }
            String listExpectedState2 = "[Sample Object 0, Sample Object 1, Sample Object 2, " +
                                         "Sample Object 3, Sample Object 4, Sample Object 5]";

            assertEquals(listExpectedState2, mtxIntrusiveLinkedList.toString());
        }
    }

    @Nested
    class RemoveTests {
        @BeforeEach
        public void setUp() {
            int numberOfElements = 5;
            String sample = "Sample Object ";
            for (int i = 0; i < numberOfElements; i++) {
                mtxIntrusiveLinkedList.add(sample + i);
            }
        }

        @Test
        public void testRemove_emptyList() {
            MtxIntrusiveLinkedList<Object> mtxIntrusiveLinkedList_empty = new MtxIntrusiveLinkedList<>();

            assertEquals(0, mtxIntrusiveLinkedList_empty.size());
            String sample = "Sample Nonexistent Object";

            assertFalse(mtxIntrusiveLinkedList_empty.remove(sample));
        }

        @Test
        public void testRemove_firstElement() {
            String elementToRemove = "Sample Object 0";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxIntrusiveLinkedList.size());
            assertEquals(elementsBefore, mtxIntrusiveLinkedList.toString());

            assertTrue(mtxIntrusiveLinkedList.remove(elementToRemove));
            assertEquals(4, mtxIntrusiveLinkedList.size());

            String elementsAfter = "[Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxIntrusiveLinkedList.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String elementToRemove = "Sample Object 2";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxIntrusiveLinkedList.size());
            assertEquals(elementsBefore, mtxIntrusiveLinkedList.toString());

            assertTrue(mtxIntrusiveLinkedList.remove(elementToRemove));
            assertEquals(4, mtxIntrusiveLinkedList.size());

            String elementsAfter = "[Sample Object 0, Sample Object 1, Sample Object 3, Sample Object 4]";
            assertEquals(elementsAfter, mtxIntrusiveLinkedList.toString());
        }

        @Test
        public void testRemove_elementNotFound() {
            String elementToRemove = "Sample Object 9";
            String elementsBefore = "[Sample Object 0, Sample Object 1, Sample Object 2, Sample Object 3, Sample Object 4]";
            assertEquals(5, mtxIntrusiveLinkedList.size());
            assertEquals(elementsBefore, mtxIntrusiveLinkedList.toString());

            assertFalse(mtxIntrusiveLinkedList.remove(elementToRemove));
            assertEquals(5, mtxIntrusiveLinkedList.size());

            assertEquals(elementsBefore, mtxIntrusiveLinkedList.toString());
        }
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxIntrusiveLinkedList.size());
            mtxIntrusiveLinkedList.add(i);
        }
        assertEquals(numberOfElements, mtxIntrusiveLinkedList.size());

        for (int j = 0; j < numberOfElements - 10; j++) {
            mtxIntrusiveLinkedList.remove(j);
        }
        assertEquals(numberOfElements - 10, mtxIntrusiveLinkedList.size());
    }

    @Test
    public void testConstructor_startingContents() {
        String[] sampleElements = {"One", "Two", "Three"};
        String expectedElements = "[One, Two, Three]";

        MtxIntrusiveLinkedList<String> customMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(sampleElements);
        assertEquals(expectedElements, customMtxIntrusiveLinkedList.toString());
    }

    @Test
    public void testIsEmpty() {
        String sample = "Sample Object";
        assertTrue(mtxIntrusiveLinkedList.isEmpty());

        mtxIntrusiveLinkedList.add(sample);
        assertFalse(mtxIntrusiveLinkedList.isEmpty());

        mtxIntrusiveLinkedList.remove(sample);
        assertTrue(mtxIntrusiveLinkedList.isEmpty());
    }

    @Test
    public void testContains_yes() {
        mtxIntrusiveLinkedList.add('a');
        mtxIntrusiveLinkedList.add('b');
        mtxIntrusiveLinkedList.add('c');

        assertTrue(mtxIntrusiveLinkedList.contains('b'));
    }

    @Test
    public void testContains_no() {
        mtxIntrusiveLinkedList.add('a');
        mtxIntrusiveLinkedList.add('b');
        mtxIntrusiveLinkedList.add('c');

        assertFalse(mtxIntrusiveLinkedList.contains('d'));
    }

    @Test
    public void testCountOccurrences() {
        assertEquals(0, mtxIntrusiveLinkedList.countOccurrences('a'));

        for (int i = 0; i < 15; i++) {
            mtxIntrusiveLinkedList.add('a');
            mtxIntrusiveLinkedList.add('b');

            assertEquals(i + 1, mtxIntrusiveLinkedList.countOccurrences('a'));
            assertEquals((i + 1) * 2, mtxIntrusiveLinkedList.size());
        }
    }

    @Test
    public void testGet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.get(0));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxIntrusiveLinkedList.add(element);
        }

        assertEquals("Zero", mtxIntrusiveLinkedList.get(0));
        assertEquals("Two", mtxIntrusiveLinkedList.get(2));
        assertEquals("Four", mtxIntrusiveLinkedList.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.get(-1));
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

        mtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(sampleList.toArray(new String[0]));

        assertEquals(sampleList.hashCode(), mtxIntrusiveLinkedList.hashCode());
    }

    @Test
    public void testIndexOf() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            mtxIntrusiveLinkedList.add(element);
        }

        for (String sampleElement : sampleElements) {
            assertEquals(sampleElement, mtxIntrusiveLinkedList.get(mtxIntrusiveLinkedList.indexOf(sampleElement)));
        }
        assertEquals(-1, mtxIntrusiveLinkedList.indexOf("Five"));
    }

    @Test
    public void testClear() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxIntrusiveLinkedList.add(i);
        }
        assertEquals(numberOfElements, mtxIntrusiveLinkedList.size());

        mtxIntrusiveLinkedList.clear();
        assertEquals(0, mtxIntrusiveLinkedList.size());
        assertEquals("[]", mtxIntrusiveLinkedList.toString());
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
            mtxIntrusiveLinkedList.add(element);
        }
        assertEquals(sampleElements.size(), mtxIntrusiveLinkedList.size());

        for (Object iteration : mtxIntrusiveLinkedList) {
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
            mtxIntrusiveLinkedList.add(element);
        }
        assertEquals(startingContents, mtxIntrusiveLinkedList.toString());

        String sub_0_to_3 = "[Zero, One, Two]";
        assertEquals(sub_0_to_3, mtxIntrusiveLinkedList.subList(0, 3).toString());
        String sub_1_to_5 = "[One, Two, Three, Four]";
        assertEquals(sub_1_to_5, mtxIntrusiveLinkedList.subList(1, 5).toString());
        String sub_3_to_7 = "[Three, Four, Five, Six]";
        assertEquals(sub_3_to_7, mtxIntrusiveLinkedList.subList(3, 7).toString());
        String sub_2_to_6 = "[Two, Three, Four, Five]";
        assertEquals(sub_2_to_6, mtxIntrusiveLinkedList.subList(2, 6).toString());

        assertEquals(startingContents, mtxIntrusiveLinkedList.subList(0, mtxIntrusiveLinkedList.size()).toString());

        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.subList(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.subList(1, 8));
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxIntrusiveLinkedList.toArray().length);

        String[] contents = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : contents) {
            mtxIntrusiveLinkedList.add(element);
        }

        Object[] generatedArray = mtxIntrusiveLinkedList.toArray();

        assertEquals(contents.length, generatedArray.length);
        for (int i = 0; i < contents.length; i++) {
            assertEquals(contents[i], generatedArray[i]);
        }
    }

    // TODO: Move this method to MtxListTest
    @Test
    public void testCopy() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String startingContents = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxIntrusiveLinkedList.add(element);
        }

        MtxList<Object> otherList = mtxIntrusiveLinkedList.copy();

        assertEquals(mtxIntrusiveLinkedList.size(), otherList.size());
        assertTrue(mtxIntrusiveLinkedList.equals(otherList));
        assertEquals(startingContents, otherList.toString());
    }

    @Test
    public void testRemoveAt() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : sampleElements) {
            mtxIntrusiveLinkedList.add(element);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.removeAt(mtxIntrusiveLinkedList.size()));

        String removeTest1 = "Three";
        String[] sampleElementsMinus1 = {"Zero", "One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest1, mtxIntrusiveLinkedList.removeAt(3));
        assertEquals(sampleElementsMinus1.length, mtxIntrusiveLinkedList.size());
        Object[] testArray1 = mtxIntrusiveLinkedList.toArray();
        for (int i = 0; i < testArray1.length; i++) {
            assertEquals(sampleElementsMinus1[i], testArray1[i]);
        }

        String removeTest2 = "Zero";
        String[] sampleElementsMinus2 = {"One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest2, mtxIntrusiveLinkedList.removeAt(0));
        assertEquals(sampleElementsMinus2.length, mtxIntrusiveLinkedList.size());
        Object[] testArray2 = mtxIntrusiveLinkedList.toArray();
        for (int i = 0; i < testArray2.length; i++) {
            assertEquals(sampleElementsMinus2[i], testArray2[i]);
        }

        String removeTest3 = "Six";
        String[] sampleElementsMinus3 = {"One", "Two", "Four", "Five"};
        assertEquals(removeTest3, mtxIntrusiveLinkedList.removeAt(4));
        assertEquals(sampleElementsMinus3.length, mtxIntrusiveLinkedList.size());
        Object[] testArray3 = mtxIntrusiveLinkedList.toArray();
        for (int i = 0; i < testArray3.length; i++) {
            assertEquals(sampleElementsMinus3[i], testArray3[i]);
        }

        String newElement = "Eight";
        String[] sampleElementsPlus1 = {"One", "Two", "Four", "Five", "Eight"};
        mtxIntrusiveLinkedList.add(newElement);
        assertEquals(sampleElementsPlus1.length, mtxIntrusiveLinkedList.size());
        Object[] testArray4 = mtxIntrusiveLinkedList.toArray();
        for (int i = 0; i < testArray4.length; i++) {
            assertEquals(sampleElementsPlus1[i], testArray4[i]);
        }
    }

    @Test
    public void testSet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.set(-1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxIntrusiveLinkedList.set(mtxIntrusiveLinkedList.size(), null));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String sampleElementsString = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxIntrusiveLinkedList.add(element);
        }
        assertEquals(sampleElementsString, mtxIntrusiveLinkedList.toString());

        String testSet1_oldVal = "Zero";
        String testSet1_newVal = "Thorough";
        int testSetIdx1 = 0;
        String sampleStringTest1 = "[Thorough, One, Two, Three, Four, Five, Six]";
        assertEquals(testSet1_oldVal, mtxIntrusiveLinkedList.set(testSetIdx1, testSet1_newVal));
        assertEquals(sampleStringTest1, mtxIntrusiveLinkedList.toString());

        String testSet2_oldVal = "Two";
        String testSet2_newVal = "Through";
        int testSetIdx2 = 2;
        String sampleStringTest2 = "[Thorough, One, Through, Three, Four, Five, Six]";
        assertEquals(testSet2_oldVal, mtxIntrusiveLinkedList.set(testSetIdx2, testSet2_newVal));
        assertEquals(sampleStringTest2, mtxIntrusiveLinkedList.toString());

        String testSet3_oldVal = "Six";
        String testSet3_newVal = "Zinc";
        int testSetIdx3 = 6;
        String sampleStringTest3 = "[Thorough, One, Through, Three, Four, Five, Zinc]";
        assertEquals(testSet3_oldVal, mtxIntrusiveLinkedList.set(testSetIdx3, testSet3_newVal));
        assertEquals(sampleStringTest3, mtxIntrusiveLinkedList.toString());
    }

    @Test
    public void testSort() {
        Integer[] unsortedArray = {2, 7, 4, 0, 9, 1, 3, 8, 5, 6};
        MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(unsortedArray);
        integerMtxIntrusiveLinkedList.sort(Comparator.naturalOrder());

        for (int i = 0; i < unsortedArray.length; i++) {
            assertEquals(i, integerMtxIntrusiveLinkedList.get(i));
        }
    }

    @Test
    public void testRemoveDuplicates() {
        Integer[] arrayWithDuplicates = {2, 4, 2, 8, 3, 4, 4, 7, 9};
        Integer[] arrayWithoutDuplicates = {2, 4, 8, 3, 7, 9};
        MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(arrayWithDuplicates);

        assertTrue(integerMtxIntrusiveLinkedList.equals(arrayWithDuplicates));
        assertTrue(integerMtxIntrusiveLinkedList.removeDuplicates());
        assertTrue(integerMtxIntrusiveLinkedList.equals(arrayWithoutDuplicates));
        assertFalse(integerMtxIntrusiveLinkedList.removeDuplicates());
    }

    @Nested
    class RotateLeftTests {
        private static final Integer[] TEST_ARRAY = {1, 2, 3, 4, 5};

        @Test
        public void testRotateLeft_zeroTimes() {
            Integer[] rotatedArray = {1, 2, 3, 4, 5};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateLeft(0);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateLeft_oneTime() {
            Integer[] rotatedArray = {2, 3, 4, 5, 1};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateLeft(1);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateLeft_nTimes_noOverflow() {
            Integer[] rotatedArray = {5, 1, 2, 3, 4};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateLeft(4);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void dtestRotateLeft_nTimes_withOverflow() {
            Integer[] rotatedArray = {3, 4, 5, 1, 2};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateLeft(7);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateLeft_manyTimes() {
            Integer[][] rotatedArrays = {
                    {2, 3, 4, 5, 1},
                    {3, 4, 5, 1, 2},
                    {4, 5, 1, 2, 3},
                    {5, 1, 2, 3, 4},
                    {1, 2, 3, 4, 5}
            };

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            for (int i = 0; i < rotatedArrays.length * 3; i++) {
                integerMtxIntrusiveLinkedList.rotateLeft(1);
                assertArrayEquals(rotatedArrays[i % 5], integerMtxIntrusiveLinkedList.toArray());
            }
        }

        @Test
        public void testRotateLeft_oneElement() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(new Integer[]{1});
            integerMtxIntrusiveLinkedList.rotateLeft(1);
            assertArrayEquals(new Integer[]{1}, integerMtxIntrusiveLinkedList.toArray());
        }
    }

    @Nested
    class RotateRightTests {
        private static final Integer[] TEST_ARRAY = {1, 2, 3, 4, 5};

        @Test
        public void testRotateRight_zeroTimes() {
            Integer[] rotatedArray = {1, 2, 3, 4, 5};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateRight(0);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateRight_oneTime() {
            Integer[] rotatedArray = {5, 1, 2, 3, 4};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateRight(1);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateRight_nTimes_noOverflow() {
            Integer[] rotatedArray = {2, 3, 4, 5, 1};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateRight(4);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void dtestRotateRight_nTimes_withOverflow() {
            Integer[] rotatedArray = {4, 5, 1, 2, 3};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.rotateRight(7);

            assertArrayEquals(rotatedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testRotateRight_manyTimes() {
            Integer[][] rotatedArrays = {
                    {5, 1, 2, 3, 4},
                    {4, 5, 1, 2, 3},
                    {3, 4, 5, 1, 2},
                    {2, 3, 4, 5, 1},
                    {1, 2, 3, 4, 5}
            };

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            for (int i = 0; i < rotatedArrays.length * 3; i++) {
                integerMtxIntrusiveLinkedList.rotateRight(1);
                assertArrayEquals(rotatedArrays[i % 5], integerMtxIntrusiveLinkedList.toArray());
            }
        }

        @Test
        public void testRotateRight_oneElement() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(new Integer[]{1});
            integerMtxIntrusiveLinkedList.rotateRight(1);
            assertArrayEquals(new Integer[]{1}, integerMtxIntrusiveLinkedList.toArray());
        }
    }

    @Nested
    class ReverseTests {
        private static final Integer[] TEST_ARRAY = {30, 60, 90, 120, 150};

        @Test
        public void testReverse() {
            Integer[] reversedArray = {150, 120, 90, 60, 30};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.reverse();

            assertArrayEquals(reversedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testReverse_emptyList() {
            Integer[] reversedArray = {};

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(reversedArray);
            integerMtxIntrusiveLinkedList.reverse();

            assertArrayEquals(reversedArray, integerMtxIntrusiveLinkedList.toArray());
        }

        @Test
        public void testReverse_unreverse() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(TEST_ARRAY);
            integerMtxIntrusiveLinkedList.reverse();
            integerMtxIntrusiveLinkedList.reverse();

            assertArrayEquals(TEST_ARRAY, integerMtxIntrusiveLinkedList.toArray());
        }
    }

    @Nested
    class IsPalindromeTests {
        private static final Integer[] YES_PALINDROME = {30, 60, 90, 60, 30};
        private static final Integer[] NOT_PALINDROME = {30, 60, 90, 120, 150};
        private static final Integer[] EMPTY = {};
        private static final int LARGE_TEST_SIZE = 300;

        @Test
        public void testIsPalindrome_yes_short() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(YES_PALINDROME);
            assertTrue(integerMtxIntrusiveLinkedList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_yes_long_odd() {
            Random random = new Random();
            MtxStack<Integer> values = new MtxStack<>();
            Integer[] testArray = new Integer[LARGE_TEST_SIZE + 1];
            for (int i = 0; i <= LARGE_TEST_SIZE / 2; i++) {
                int randomInt = random.nextInt(1, 100);
                values.push(randomInt);
                testArray[i] = randomInt;
            }
            for (int i = LARGE_TEST_SIZE / 2; i <= LARGE_TEST_SIZE; i++) {
                testArray[i] = values.pop();
            }

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(testArray);
            assertTrue(integerMtxIntrusiveLinkedList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_yes_long_even() {
            Random random = new Random();
            MtxStack<Integer> values = new MtxStack<>();
            Integer[] testArray = new Integer[LARGE_TEST_SIZE];
            for (int i = 0; i <= LARGE_TEST_SIZE / 2 - 1; i++) {
                int randomInt = random.nextInt(1, 100);
                values.push(randomInt);
                testArray[i] = randomInt;
            }
            for (int i = LARGE_TEST_SIZE / 2; i < LARGE_TEST_SIZE; i++) {
                testArray[i] = values.pop();
            }

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(testArray);
            assertTrue(integerMtxIntrusiveLinkedList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_yes_empty() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(EMPTY);
            assertTrue(integerMtxIntrusiveLinkedList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_no_short() {
            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(NOT_PALINDROME);
            assertFalse(integerMtxIntrusiveLinkedList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_no_long() {
            Random random = new Random();
            MtxStack<Integer> values = new MtxStack<>();
            Integer[] testArray = new Integer[LARGE_TEST_SIZE];
            for (int i = 0; i < LARGE_TEST_SIZE; i++) {
                int randomInt = random.nextInt(1, 100);
                values.push(randomInt);
                testArray[i] = randomInt;
            }

            MtxIntrusiveLinkedList<Integer> integerMtxIntrusiveLinkedList = new MtxIntrusiveLinkedList<>(testArray);
            assertFalse(integerMtxIntrusiveLinkedList.isPalindrome());
        }
    }
}
