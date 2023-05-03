package com.edavalos.mtx.util.db;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class MtxIdGeneratorTest {
    private MtxIdGenerator mtxIdGenerator;

    @Nested
    public class TestConstructor {
        private final String EXPECTED_FORMAT_AS_STRING = "NNALN";
        private final MtxIdGenerator.MtxCharType[] EXPECTED_FORMAT_AS_ENUM_ARRAY = new MtxIdGenerator.MtxCharType[] {
                MtxIdGenerator.MtxCharType.UINT,
                MtxIdGenerator.MtxCharType.UINT,
                MtxIdGenerator.MtxCharType.ANY,
                MtxIdGenerator.MtxCharType.CHAR,
                MtxIdGenerator.MtxCharType.UINT
        };

        @Test
        public void testConstructor_fromString() {
            mtxIdGenerator = new MtxIdGenerator(EXPECTED_FORMAT_AS_STRING);
            assertArrayEquals(EXPECTED_FORMAT_AS_ENUM_ARRAY, mtxIdGenerator.getFormatAsArray());
        }

        @Test
        public void testConstructor_fromArray() {
            mtxIdGenerator = new MtxIdGenerator(EXPECTED_FORMAT_AS_ENUM_ARRAY);
            assertEquals(EXPECTED_FORMAT_AS_STRING, mtxIdGenerator.getFormatAsString());
        }

        @Nested
        public class TestConstructorError {
            private final String STRING_FORMAT_TOO_LONG = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN";
            private final String STRING_FORMAT_UNKNOWN_CHAR = "NNNNNNNNNNNNNNNXNNNNNNNNNNNNNNN";
            private final MtxIdGenerator.MtxCharType[] ENUM_FORMAT_TOO_LONG = new MtxIdGenerator.MtxCharType[] {
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT,
                    MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT, MtxIdGenerator.MtxCharType.UINT
            };
            private final MtxIdGenerator.MtxCharType[] ENUM_FORMAT_TOO_SHORT = new MtxIdGenerator.MtxCharType[] {};

            @Test
            public void testConstructor_fromString_formatTooLong() {
                assertThrows(IllegalArgumentException.class, () -> new MtxIdGenerator(STRING_FORMAT_TOO_LONG));
            }

            @Test
            public void testConstructor_fromString_formatUnknownChar() {
                assertThrows(IllegalArgumentException.class, () -> new MtxIdGenerator(STRING_FORMAT_UNKNOWN_CHAR));
            }

            @Test
            public void testConstructor_fromEnum_formatTooLong() {
                assertThrows(IllegalArgumentException.class, () -> new MtxIdGenerator(ENUM_FORMAT_TOO_LONG));
            }

            @Test
            public void testConstructor_fromEnum_formatTooShort() {
                assertThrows(IllegalArgumentException.class, () -> new MtxIdGenerator(ENUM_FORMAT_TOO_SHORT));
            }
        }
    }

    @Test
    public void testMaxIds() {
        String test1 = "NLN";
        int max1 = 2600;
        String test2 = "NALN";
        int max2 = 93600;

        mtxIdGenerator = new MtxIdGenerator(test1);
        assertEquals(max1, mtxIdGenerator.getMaxIds());

        mtxIdGenerator = new MtxIdGenerator(test2);
        assertEquals(max2, mtxIdGenerator.getMaxIds());
    }

    @Nested
    public class TestGetFormat {
        private final String EXPECTED_FORMAT_AS_STRING = "N";
        private final MtxIdGenerator.MtxCharType[] EXPECTED_FORMAT_AS_ENUM_ARRAY = new MtxIdGenerator.MtxCharType[] {
                MtxIdGenerator.MtxCharType.UINT,
        };

        @Test
        public void testGetFormatAsArray() {
            mtxIdGenerator = new MtxIdGenerator(EXPECTED_FORMAT_AS_STRING);
            assertArrayEquals(EXPECTED_FORMAT_AS_ENUM_ARRAY, mtxIdGenerator.getFormatAsArray());
        }

        @Test
        public void testGetFormatAsString() {
            mtxIdGenerator = new MtxIdGenerator(EXPECTED_FORMAT_AS_ENUM_ARRAY);
            assertEquals(EXPECTED_FORMAT_AS_STRING, mtxIdGenerator.getFormatAsString());
        }
    }
}
