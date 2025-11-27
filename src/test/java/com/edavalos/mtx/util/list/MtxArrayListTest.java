package com.edavalos.mtx.util.list;

import com.edavalos.mtx.util.list.line.MtxStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
            MtxArrayList<Object> MtxArrayList_empty = new MtxArrayList<>();

            assertEquals(0, MtxArrayList_empty.size());
            String sample = "Sample Nonexistent Object";

            assertFalse(MtxArrayList_empty.remove(sample));
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

        MtxArrayList<String> customMtxArrayList = new MtxArrayList<>(sampleElements);
        assertEquals(expectedElements, customMtxArrayList.toString());
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

    @Test
    public void testSubList() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String startingContents = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxArrayList.add(element);
        }
        assertEquals(startingContents, mtxArrayList.toString());

        String sub_0_to_3 = "[Zero, One, Two]";
        assertEquals(sub_0_to_3, mtxArrayList.subList(0, 3).toString());
        String sub_1_to_5 = "[One, Two, Three, Four]";
        assertEquals(sub_1_to_5, mtxArrayList.subList(1, 5).toString());
        String sub_3_to_7 = "[Three, Four, Five, Six]";
        assertEquals(sub_3_to_7, mtxArrayList.subList(3, 7).toString());
        String sub_2_to_6 = "[Two, Three, Four, Five]";
        assertEquals(sub_2_to_6, mtxArrayList.subList(2, 6).toString());

        assertEquals(startingContents, mtxArrayList.subList(0, mtxArrayList.size()).toString());

        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.subList(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.subList(1, 8));
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxArrayList.toArray().length);

        String[] contents = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : contents) {
            mtxArrayList.add(element);
        }

        Object[] generatedArray = mtxArrayList.toArray();

        assertEquals(contents.length, generatedArray.length);
        for (int i = 0; i < contents.length; i++) {
            assertEquals(contents[i], generatedArray[i]);
        }
    }

    @Test
    public void testRemoveAt() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : sampleElements) {
            mtxArrayList.add(element);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.removeAt(mtxArrayList.size()));

        String removeTest1 = "Three";
        String[] sampleElementsMinus1 = {"Zero", "One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest1, mtxArrayList.removeAt(3));
        assertEquals(sampleElementsMinus1.length, mtxArrayList.size());
        Object[] testArray1 = mtxArrayList.toArray();
        for (int i = 0; i < testArray1.length; i++) {
            assertEquals(sampleElementsMinus1[i], testArray1[i]);
        }

        String removeTest2 = "Zero";
        String[] sampleElementsMinus2 = {"One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest2, mtxArrayList.removeAt(0));
        assertEquals(sampleElementsMinus2.length, mtxArrayList.size());
        Object[] testArray2 = mtxArrayList.toArray();
        for (int i = 0; i < testArray2.length; i++) {
            assertEquals(sampleElementsMinus2[i], testArray2[i]);
        }

        String removeTest3 = "Six";
        String[] sampleElementsMinus3 = {"One", "Two", "Four", "Five"};
        assertEquals(removeTest3, mtxArrayList.removeAt(4));
        assertEquals(sampleElementsMinus3.length, mtxArrayList.size());
        Object[] testArray3 = mtxArrayList.toArray();
        for (int i = 0; i < testArray3.length; i++) {
            assertEquals(sampleElementsMinus3[i], testArray3[i]);
        }

        String newElement = "Eight";
        String[] sampleElementsPlus1 = {"One", "Two", "Four", "Five", "Eight"};
        mtxArrayList.add(newElement);
        assertEquals(sampleElementsPlus1.length, mtxArrayList.size());
        Object[] testArray4 = mtxArrayList.toArray();
        for (int i = 0; i < testArray4.length; i++) {
            assertEquals(sampleElementsPlus1[i], testArray4[i]);
        }
    }

    @Test
    public void testSet() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.set(-1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxArrayList.set(mtxArrayList.size(), null));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String sampleElementsString = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxArrayList.add(element);
        }
        assertEquals(sampleElementsString, mtxArrayList.toString());

        String testSet1_oldVal = "Zero";
        String testSet1_newVal = "Thorough";
        int testSetIdx1 = 0;
        String sampleStringTest1 = "[Thorough, One, Two, Three, Four, Five, Six]";
        assertEquals(testSet1_oldVal, mtxArrayList.set(testSetIdx1, testSet1_newVal));
        assertEquals(sampleStringTest1, mtxArrayList.toString());

        String testSet2_oldVal = "Two";
        String testSet2_newVal = "Through";
        int testSetIdx2 = 2;
        String sampleStringTest2 = "[Thorough, One, Through, Three, Four, Five, Six]";
        assertEquals(testSet2_oldVal, mtxArrayList.set(testSetIdx2, testSet2_newVal));
        assertEquals(sampleStringTest2, mtxArrayList.toString());

        String testSet3_oldVal = "Six";
        String testSet3_newVal = "Zinc";
        int testSetIdx3 = 6;
        String sampleStringTest3 = "[Thorough, One, Through, Three, Four, Five, Zinc]";
        assertEquals(testSet3_oldVal, mtxArrayList.set(testSetIdx3, testSet3_newVal));
        assertEquals(sampleStringTest3, mtxArrayList.toString());
    }

    @Test
    public void testSort() {
        Integer[] unsortedArray = {2, 7, 4, 0, 9, 1, 3, 8, 5, 6};
        MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(unsortedArray);
        integerMtxArrayList.sort(Comparator.naturalOrder());

        for (int i = 0; i < unsortedArray.length; i++) {
            assertEquals(i, integerMtxArrayList.get(i));
        }
    }

    @Test
    public void testRemoveDuplicates() {
        Integer[] arrayWithDuplicates = {2, 4, 2, 8, 3, 4, 4, 7, 9};
        Integer[] arrayWithoutDuplicates = {2, 4, 8, 3, 7, 9};
        MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(arrayWithDuplicates);

        assertTrue(integerMtxArrayList.equals(arrayWithDuplicates));
        assertTrue(integerMtxArrayList.removeDuplicates());
        assertTrue(integerMtxArrayList.equals(arrayWithoutDuplicates));
        assertFalse(integerMtxArrayList.removeDuplicates());
    }

    @Nested
    class RotateLeftTests {
        private static final Integer[] TEST_ARRAY = {1, 2, 3, 4, 5};

        @Test
        public void testRotateLeft_zeroTimes() {
            Integer[] rotatedArray = {1, 2, 3, 4, 5};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateLeft(0);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateLeft_oneTime() {
            Integer[] rotatedArray = {2, 3, 4, 5, 1};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateLeft(1);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateLeft_nTimes_noOverflow() {
            Integer[] rotatedArray = {5, 1, 2, 3, 4};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateLeft(4);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateLeft_nTimes_withOverflow() {
            Integer[] rotatedArray = {3, 4, 5, 1, 2};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateLeft(7);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
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

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            for (int i = 0; i < rotatedArrays.length * 3; i++) {
                integerMtxArrayList.rotateLeft(1);
                assertArrayEquals(rotatedArrays[i % 5], integerMtxArrayList.toArray());
            }
        }

        @Test
        public void testRotateLeft_oneElement() {
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(new Integer[]{1});
            integerMtxArrayList.rotateLeft(1);
            assertArrayEquals(new Integer[]{1}, integerMtxArrayList.toArray());
        }
    }

    @Nested
    class RotateRightTests {
        private static final Integer[] TEST_ARRAY = {1, 2, 3, 4, 5};

        @Test
        public void testRotateRight_zeroTimes() {
            Integer[] rotatedArray = {1, 2, 3, 4, 5};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateRight(0);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateRight_oneTime() {
            Integer[] rotatedArray = {5, 1, 2, 3, 4};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateRight(1);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateRight_nTimes_noOverflow() {
            Integer[] rotatedArray = {2, 3, 4, 5, 1};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateRight(4);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testRotateRight_nTimes_withOverflow() {
            Integer[] rotatedArray = {4, 5, 1, 2, 3};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.rotateRight(7);

            assertArrayEquals(rotatedArray, integerMtxArrayList.toArray());
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

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            for (int i = 0; i < rotatedArrays.length * 3; i++) {
                integerMtxArrayList.rotateRight(1);
                assertArrayEquals(rotatedArrays[i % 5], integerMtxArrayList.toArray());
            }
        }

        @Test
        public void testRotateRight_oneElement() {
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(new Integer[]{1});
            integerMtxArrayList.rotateRight(1);
            assertArrayEquals(new Integer[]{1}, integerMtxArrayList.toArray());
        }
    }

    @Nested
    class ReverseTests {
        private static final Integer[] TEST_ARRAY = {30, 60, 90, 120, 150};

        @Test
        public void testReverse() {
            Integer[] reversedArray = {150, 120, 90, 60, 30};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.reverse();

            assertArrayEquals(reversedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testReverse_emptyList() {
            Integer[] reversedArray = {};

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(reversedArray);
            integerMtxArrayList.reverse();

            assertArrayEquals(reversedArray, integerMtxArrayList.toArray());
        }

        @Test
        public void testReverse_unreverse() {
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(TEST_ARRAY);
            integerMtxArrayList.reverse();
            integerMtxArrayList.reverse();

            assertArrayEquals(TEST_ARRAY, integerMtxArrayList.toArray());
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
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(YES_PALINDROME);
            assertTrue(integerMtxArrayList.isPalindrome());
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

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(testArray);
            assertTrue(integerMtxArrayList.isPalindrome());
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

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(testArray);
            assertTrue(integerMtxArrayList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_yes_empty() {
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(EMPTY);
            assertTrue(integerMtxArrayList.isPalindrome());
        }

        @Test
        public void testIsPalindrome_no_short() {
            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(NOT_PALINDROME);
            assertFalse(integerMtxArrayList.isPalindrome());
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

            MtxArrayList<Integer> integerMtxArrayList = new MtxArrayList<>(testArray);
            assertFalse(integerMtxArrayList.isPalindrome());
        }
    }
}
