package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxStringListTest {
    private MtxStringList<Object> mtxStringList;

    @BeforeEach
    public void setUp() {
        mtxStringList = new MtxStringList<>();
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

        MtxStringList<String> customMtxStringList = new MtxStringList<>(sampleElements);
        assertEquals(expectedElements, customMtxStringList.toString());
    }
}
