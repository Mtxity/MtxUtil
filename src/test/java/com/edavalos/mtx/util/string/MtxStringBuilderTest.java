package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxStringBuilderTest {
    private static final String STARTING_STR = "abcd_efg_hijk";

    private MtxStringBuilder mtxStringBuilder;

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
}
