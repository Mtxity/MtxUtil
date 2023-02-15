package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxStringListTest {
    private final MtxStringList.MtxStringDecoder<String> stringStringDecoder = new MtxStringList.MtxStringDecoder<String>() {
        @Override
        public String fromString(String stringRepresentation) {
            return stringRepresentation;
        }
    };
    private final MtxStringList.MtxStringDecoder<Integer> intStringDecoder = new MtxStringList.MtxStringDecoder<Integer>() {
        @Override
        public Integer fromString(String stringRepresentation) {
            return Integer.valueOf(stringRepresentation);
        }
    };
    private final MtxStringList.MtxStringDecoder<Character> charStringDecoder = new MtxStringList.MtxStringDecoder<Character>() {
        @Override
        public Character fromString(String stringRepresentation) {
            if (stringRepresentation.length() != 1) {
                return null;
            }
            return stringRepresentation.charAt(0);
        }
    };

    private MtxStringList<Integer> mtxStringList;

    @BeforeEach
    public void setUp() {
        mtxStringList = new MtxStringList<>(intStringDecoder, Integer.class);
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_fullList() {
            int numberOfElements = 10;
            for (int i = 0; i < numberOfElements; i++) {
                mtxStringList.add(i);
            }

            String expected = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
            String actual = mtxStringList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_emptyList() {
            String expected = "[]";
            String actual = mtxStringList.toString();

            assertEquals(expected, actual);
        }

        @Test
        public void testToString_singletonList() {
            mtxStringList.add(3);

            String expected = "[3]";
            String actual = mtxStringList.toString();

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testConstructor_startingContents() {
        String[] sampleElements = {"One", "Two", "Three"};
        String expectedElements = "[One, Two, Three]";

        MtxStringList<String> customMtxStringList = new MtxStringList<>(stringStringDecoder, String.class, sampleElements);
        assertEquals(expectedElements, customMtxStringList.toString());
    }

    @Test
    public void testSize() {
        int numberOfElements = 20;
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(i, mtxStringList.size());
            mtxStringList.add(i);
        }
        assertEquals(numberOfElements, mtxStringList.size());

//        for (int j = 0; j < numberOfElements - 10; j++) {
//            mtxStringList.remove(j);
//        }
//        assertEquals(numberOfElements - 10, mtxStringList.size());
    }

    @Test
    public void testToArray() {
        assertEquals(0, mtxStringList.toArray().length);

        Integer[] contents = {0, 1, 2, 3, 4, 5, 6};
        for (int element : contents) {
            mtxStringList.add(element);
        }

        Integer[] generatedArray = mtxStringList.toArray();

        assertEquals(contents.length, generatedArray.length);
        for (int i = 0; i < contents.length; i++) {
            assertEquals(contents[i], generatedArray[i]);
        }
    }

    @Test
    public void testIterator() {
        List<Integer> sampleElements = new LinkedList<>(){
            {
                add(2);
                add(4);
                add(6);
                add(8);
            }
        };
        for (int element : sampleElements) {
            mtxStringList.add(element);
        }
        assertEquals(sampleElements.size(), mtxStringList.size());

        for (Integer iteration : mtxStringList) {
            assertTrue(sampleElements.contains(iteration));
            sampleElements.remove(iteration);
        }
        assertEquals(0, sampleElements.size());
    }

    @Nested
    class RemoveTests {
        Character[] charElements = {'a', 'b', 'c', 'x', 'y', 'z'};
        String charElementsString = "[a, b, c, x, y, z]";
        MtxStringList<Character> charMtxStringList;

        @BeforeEach
        public void setUp() {
            charMtxStringList = new MtxStringList<>(charStringDecoder, Character.class, charElements);
            assertEquals(charElements.length, charMtxStringList.size());
            assertEquals(charElementsString, charMtxStringList.toString());
        }

        @Test
        public void testRemove_middleElement() {
            String charElementsAfterRemoveString = "[a, b, c, y, z]";
            assertTrue(charMtxStringList.remove('x'));

            assertEquals(charElements.length - 1, charMtxStringList.size());
            assertEquals(charElementsAfterRemoveString, charMtxStringList.toString());
        }

        @Test
        public void testRemove_firstElement() {
            String charElementsAfterRemoveString = "[b, c, x, y, z]";
            assertTrue(charMtxStringList.remove('a'));

            assertEquals(charElements.length - 1, charMtxStringList.size());
            assertEquals(charElementsAfterRemoveString, charMtxStringList.toString());
        }

        @Test
        public void testRemove_lastElement() {
            String charElementsAfterRemoveString = "[a, b, c, x, y]";
            assertTrue(charMtxStringList.remove('z'));

            assertEquals(charElements.length - 1, charMtxStringList.size());
            assertEquals(charElementsAfterRemoveString, charMtxStringList.toString());
        }

        @Test
        public void testRemove_nonExistentElement() {
            assertFalse(charMtxStringList.remove('o'));

            assertEquals(charElements.length, charMtxStringList.size());
            assertEquals(charElementsString, charMtxStringList.toString());
        }

        @Test
        public void testRemove_emptyList() {
            charMtxStringList = new MtxStringList<>(charStringDecoder, Character.class, new Character[0]);

            assertFalse(charMtxStringList.remove('a'));
            assertEquals(0, charMtxStringList.size());
            assertEquals("[]", charMtxStringList.toString());
        }
    }

    @Test
    public void testRemoveDuplicates() {
        Integer[] arrayWithDuplicates = {2, 4, 2, 8, 3, 4, 4, 7, 9};
        Integer[] arrayWithoutDuplicates = {2, 4, 8, 3, 7, 9};
        mtxStringList = new MtxStringList<>(intStringDecoder, Integer.class, arrayWithDuplicates);

        assertTrue(mtxStringList.equals(arrayWithDuplicates));
        assertTrue(mtxStringList.removeDuplicates());
        assertTrue(mtxStringList.equals(arrayWithoutDuplicates));
        assertFalse(mtxStringList.removeDuplicates());
    }

    @Test
    public void testGet() {
        MtxStringList<String> stringMtxStringList = new MtxStringList<>(stringStringDecoder, String.class);
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.get(0));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        for (String element : sampleElements) {
            stringMtxStringList.add(element);
        }

        assertEquals("Zero", stringMtxStringList.get(0));
        assertEquals("Two", stringMtxStringList.get(2));
        assertEquals("Four", stringMtxStringList.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.get(-1));
    }

    @Test
    public void testSort() {
        Integer[] unsortedArray = {2, 7, 4, 0, 9, 1, 3, 8, 5, 6};
        MtxStringList<Integer> integerMtxStringList = new MtxStringList<>(intStringDecoder, Integer.class, unsortedArray);
        integerMtxStringList.sort(Comparator.naturalOrder());

        for (int i = 0; i < unsortedArray.length; i++) {
            assertEquals(i, integerMtxStringList.get(i));
        }
    }

    @Test
    public void testClear() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxStringList.add(i);
        }
        assertEquals(numberOfElements, mtxStringList.size());

        mtxStringList.clear();
        assertEquals(0, mtxStringList.size());
        assertEquals("[]", mtxStringList.toString());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mtxStringList.isEmpty());
        mtxStringList.add(2);
        assertFalse(mtxStringList.isEmpty());
        mtxStringList.remove(2);
        assertTrue(mtxStringList.isEmpty());
    }

    @Test
    public void testContains() {
        int numberOfElements = 10;
        for (int i = 0; i < numberOfElements; i++) {
            mtxStringList.add(i);
        }
        assertEquals(numberOfElements, mtxStringList.size());

        for (int j = 0; j < numberOfElements; j++) {
            assertTrue(mtxStringList.contains(j));
        }
        assertFalse(mtxStringList.contains(numberOfElements + 1));
        assertFalse(mtxStringList.contains(numberOfElements + 2));
        assertFalse(mtxStringList.contains(numberOfElements + 3));
    }

    @Test
    public void testCountOccurrences() {
        Integer[] integers = {6, 2, 4, 5, 3, 2, 3, 3, 4, 4, 6, 4, 2, 8, 1, 1, 9, 6, 9};
        int numberOf2s = 3;
        int numberOf4s = 4;
        int numberOf5s = 1;
        int numberOf8s = 1;
        int numberOf1s = 2;
        int numberOf9s = 2;
        int numberOf6s = 3;
        int numberOf7s = 0;

        mtxStringList.addAll(integers);
        assertEquals(numberOf2s, mtxStringList.countOccurrences(2));
        assertEquals(numberOf4s, mtxStringList.countOccurrences(4));
        assertEquals(numberOf5s, mtxStringList.countOccurrences(5));
        assertEquals(numberOf8s, mtxStringList.countOccurrences(8));
        assertEquals(numberOf1s, mtxStringList.countOccurrences(1));
        assertEquals(numberOf9s, mtxStringList.countOccurrences(9));
        assertEquals(numberOf6s, mtxStringList.countOccurrences(6));
        assertEquals(numberOf7s, mtxStringList.countOccurrences(7));
    }

    @Test
    public void testIndexOf() {
        Integer[] sampleElements = {0, 1, 2, 3, 4};
        for (int element : sampleElements) {
            mtxStringList.add(element);
        }
        for (int element : sampleElements) {
            mtxStringList.remove(element);
        }
        for (int element : sampleElements) {
            mtxStringList.add(element);
        }

        for (int sampleElement : sampleElements) {
            assertEquals(sampleElement, mtxStringList.get(mtxStringList.indexOf(sampleElement)));
        }
        assertEquals(-1, mtxStringList.indexOf(5));
    }

    @Test
    public void testSet() {
        MtxStringList<String> stringMtxStringList = new MtxStringList<>(stringStringDecoder, String.class);

        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.set(-1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.set(stringMtxStringList.size(), null));

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String sampleElementsString = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            stringMtxStringList.add(element);
        }
        assertEquals(sampleElementsString, stringMtxStringList.toString());

        String testSet1_oldVal = "Zero";
        String testSet1_newVal = "Thorough";
        int testSetIdx1 = 0;
        String sampleStringTest1 = "[Thorough, One, Two, Three, Four, Five, Six]";
        assertEquals(testSet1_oldVal, stringMtxStringList.set(testSetIdx1, testSet1_newVal));
        assertEquals(sampleStringTest1, stringMtxStringList.toString());

        String testSet2_oldVal = "Two";
        String testSet2_newVal = "Through";
        int testSetIdx2 = 2;
        String sampleStringTest2 = "[Thorough, One, Through, Three, Four, Five, Six]";
        assertEquals(testSet2_oldVal, stringMtxStringList.set(testSetIdx2, testSet2_newVal));
        assertEquals(sampleStringTest2, stringMtxStringList.toString());

        String testSet3_oldVal = "Six";
        String testSet3_newVal = "Zinc";
        int testSetIdx3 = 6;
        String sampleStringTest3 = "[Thorough, One, Through, Three, Four, Five, Zinc]";
        assertEquals(testSet3_oldVal, stringMtxStringList.set(testSetIdx3, testSet3_newVal));
        assertEquals(sampleStringTest3, stringMtxStringList.toString());
    }

    @Test
    public void testRemoveAt() {
        MtxStringList<String> stringMtxStringList = new MtxStringList<>(stringStringDecoder, String.class);

        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        for (String element : sampleElements) {
            stringMtxStringList.add(element);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stringMtxStringList.removeAt(stringMtxStringList.size()));

        String removeTest1 = "Three";
        String[] sampleElementsMinus1 = {"Zero", "One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest1, stringMtxStringList.removeAt(3));
        assertEquals(sampleElementsMinus1.length, stringMtxStringList.size());
        String[] testArray1 = stringMtxStringList.toArray();
        for (int i = 0; i < testArray1.length; i++) {
            assertEquals(sampleElementsMinus1[i], testArray1[i]);
        }

        String removeTest2 = "Zero";
        String[] sampleElementsMinus2 = {"One", "Two", "Four", "Five", "Six"};
        assertEquals(removeTest2, stringMtxStringList.removeAt(0));
        assertEquals(sampleElementsMinus2.length, stringMtxStringList.size());
        String[] testArray2 = stringMtxStringList.toArray();
        for (int i = 0; i < testArray2.length; i++) {
            assertEquals(sampleElementsMinus2[i], testArray2[i]);
        }

        String removeTest3 = "Six";
        String[] sampleElementsMinus3 = {"One", "Two", "Four", "Five"};
        assertEquals(removeTest3, stringMtxStringList.removeAt(4));
        assertEquals(sampleElementsMinus3.length, stringMtxStringList.size());
        String[] testArray3 = stringMtxStringList.toArray();
        for (int i = 0; i < testArray3.length; i++) {
            assertEquals(sampleElementsMinus3[i], testArray3[i]);
        }

        String newElement = "Eight";
        String[] sampleElementsPlus1 = {"One", "Two", "Four", "Five", "Eight"};
        stringMtxStringList.add(newElement);
        assertEquals(sampleElementsPlus1.length, stringMtxStringList.size());
        String[] testArray4 = stringMtxStringList.toArray();
        for (int i = 0; i < testArray4.length; i++) {
            assertEquals(sampleElementsPlus1[i], testArray4[i]);
        }
    }
}
