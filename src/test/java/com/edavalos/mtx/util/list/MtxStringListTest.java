package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
