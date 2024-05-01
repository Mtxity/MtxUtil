package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxStringBuilderTest {
    private static final String STARTING_STR = "abcd_efg_hijk";

    private MtxStringBuilder mtxStringBuilder;

    @BeforeEach
    public void setUp() {
        mtxStringBuilder = new MtxStringBuilder(STARTING_STR);
    }

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_blank() {
            mtxStringBuilder = new MtxStringBuilder();
            assertTrue(mtxStringBuilder.isEmpty());
        }

        @Test
        public void testConstructor_withString() {
            mtxStringBuilder = new MtxStringBuilder("test");
            assertFalse(mtxStringBuilder.isEmpty());
            assertEquals("test", mtxStringBuilder.toString());
        }

        @Test
        public void testConstructor_withObject() {
            int[] array = {1, 2, 3};
            mtxStringBuilder = new MtxStringBuilder(array);
            assertFalse(mtxStringBuilder.isEmpty());
            assertEquals(array.toString(), mtxStringBuilder.toString());
        }
    }

    @Nested
    class AppendTests {

        @Test
        public void testAppend_string_empty() {
            mtxStringBuilder = mtxStringBuilder.append("");
            assertEquals(STARTING_STR, mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_string_notEmpty() {
            mtxStringBuilder = mtxStringBuilder.append("_lmnop");
            assertEquals(STARTING_STR + "_lmnop", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_char() {
            mtxStringBuilder = mtxStringBuilder.append('x');
            assertEquals(STARTING_STR + "x", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_int() {
            int a = 5;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_long() {
            long a = 5;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_short() {
            short a = 5;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_byte() {
            byte a = 5;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_double() {
            double a = 5.1;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5.1", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_float() {
            float a = 5.1f;
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + "5.1", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_boolean_true() {
            mtxStringBuilder = mtxStringBuilder.append(true);
            assertEquals(STARTING_STR + "true", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_boolean_false() {
            mtxStringBuilder = mtxStringBuilder.append(false);
            assertEquals(STARTING_STR + "false", mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_object() {
            Map<Boolean, Boolean> a = Map.of(true, true, false, true);
            mtxStringBuilder = mtxStringBuilder.append(a);
            assertEquals(STARTING_STR + a, mtxStringBuilder.toString());
        }

        @Test
        public void testAppend_objects_array() {
            String[] arr = {"_", "l", "m", "n"};
            mtxStringBuilder = mtxStringBuilder.append(arr);
            assertEquals(
                    STARTING_STR + arr[0] + arr[1] + arr[2] + arr[3],
                    mtxStringBuilder.toString()
            );
        }

        @Test
        public void testAppend_objects_list() {
            List<List<Integer>> mat = List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6),
                    List.of(7, 8, 9)
            );
            mtxStringBuilder = mtxStringBuilder.append(mat);
            assertEquals(
                    STARTING_STR + mat.get(0) + mat.get(1) + mat.get(2),
                    mtxStringBuilder.toString()
            );
        }

        @Test
        public void testAppend_objects_list_null() {
            mtxStringBuilder = mtxStringBuilder.append((List<?>) null);
            assertEquals(STARTING_STR, mtxStringBuilder.toString());
        }
    }

    @Test
    public void testAppendSpace() {
        mtxStringBuilder = mtxStringBuilder.appendSpace();
        assertEquals(STARTING_STR + " ", mtxStringBuilder.toString());
    }

    @Test
    public void testAppendSpaces() {
        for (int i = 0; i < 5; i++) {
            mtxStringBuilder = mtxStringBuilder.appendSpaces(i);
            String expectedAdded = (i == 0) ? "" : " ".repeat(i);
            assertEquals(STARTING_STR + expectedAdded, mtxStringBuilder.toString());
            mtxStringBuilder = new MtxStringBuilder(STARTING_STR);
        }
    }

    @Test
    public void testAppendSpaces_negative() {
        StringIndexOutOfBoundsException e = assertThrows(
                StringIndexOutOfBoundsException.class,
                () -> mtxStringBuilder.appendSpaces(-1)
        );
        assertEquals("Cannot append string of negative size: -1", e.getMessage());
    }

    @Test
    public void testReverse() {
        assertEquals(
                new StringBuilder(STARTING_STR).reverse().toString(),
                mtxStringBuilder.reverse().toString()
        );
    }

    @Test
    public void testReverse_lengthOne() {
        mtxStringBuilder = new MtxStringBuilder("x");
        assertEquals(
                mtxStringBuilder.toString(),
                mtxStringBuilder.reverse().toString()
        );
    }

    @Test
    public void testReplaceAll() {
        assertEquals(
                STARTING_STR.replaceAll(Pattern.quote("_"), "+"),
                mtxStringBuilder.replaceAll('_', '+').toString()
        );
    }

    @Test
    public void testLength() {
        mtxStringBuilder = new MtxStringBuilder();
        for (int i = 1; i < 8; i++) {
            mtxStringBuilder.append(i);
            assertEquals(i, mtxStringBuilder.length());
        }
    }
}
