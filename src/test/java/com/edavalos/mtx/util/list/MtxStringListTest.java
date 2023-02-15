package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        mtxStringList = new MtxStringList<>(intStringDecoder);
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

        MtxStringList<String> customMtxStringList = new MtxStringList<>(stringStringDecoder, sampleElements);
        assertEquals(expectedElements, customMtxStringList.toString());
    }
}
