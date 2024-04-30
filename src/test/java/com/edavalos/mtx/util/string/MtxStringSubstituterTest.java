package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxStringSubstituterTest {
    private MtxStringSubstituter mtxStringSubstituter;

    @Nested
    class SubstituteTests {
        private String sampleInput = "Replacement 1: ${x}\n" +
                                     "Replacement 2: ${y}\n" +
                                     "Replacement 3: ${z}\n" +
                                     "Replacement 4: ${z}\n" +
                                     "Replacement 5: ${y}";

        @Test
        public void testSubstitute_allReplaced() {
            mtxStringSubstituter = new MtxStringSubstituter(Map.of(
                    "x", "a",
                    "y", "b",
                    "z", "c"
            ));

            String expected = "Replacement 1: a\n" +
                              "Replacement 2: b\n" +
                              "Replacement 3: c\n" +
                              "Replacement 4: c\n" +
                              "Replacement 5: b";
            String actual = mtxStringSubstituter.substitute(sampleInput);

            assertEquals(expected, actual);
        }

        @Test
        public void testSubstitute_someReplaced() {
            mtxStringSubstituter = new MtxStringSubstituter(Map.of(
                    "x", "a",
                    "y", "b"
            ));

            String expected = "Replacement 1: a\n" +
                              "Replacement 2: b\n" +
                              "Replacement 3: ${z}\n" +
                              "Replacement 4: ${z}\n" +
                              "Replacement 5: b";
            String actual = mtxStringSubstituter.substitute(sampleInput);

            assertEquals(expected, actual);
        }

        @Test
        public void testSubstitute_noneReplaced() {
            mtxStringSubstituter = new MtxStringSubstituter(Map.of(
                    "v", "a",
                    "w", "b"
            ));

            String expected = "Replacement 1: ${x}\n" +
                              "Replacement 2: ${y}\n" +
                              "Replacement 3: ${z}\n" +
                              "Replacement 4: ${z}\n" +
                              "Replacement 5: ${y}";
            String actual = mtxStringSubstituter.substitute(sampleInput);

            assertEquals(expected, actual);
        }
    }
}
