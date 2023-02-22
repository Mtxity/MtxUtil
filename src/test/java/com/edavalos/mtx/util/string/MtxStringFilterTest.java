package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxStringFilterTest {
    private MtxStringFilter mtxStringFilter;

    @Nested
    class FilterTests_exactStrict {
        @BeforeEach
        public void setUp() {
            String[] forbiddenWords = {"ass"};
            mtxStringFilter = new MtxStringFilter(StringFilteringPolicy.EXACT_STRICT, forbiddenWords);
        }

        @Test
        public void testFilter_noForbiddenWords() {
            String sampleString = "you failed";
            String expectedCensor = "you failed";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_yesForbiddenWords() {
            String sampleString = "you passed";
            String expectedCensor = "you p***ed";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_multipleForbiddenWords() {
            String sampleString = "you passed you ass";
            String expectedCensor = "you p***ed you ***";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }
    }

    @Nested
    class FilterTests_exactIsolated {
        @BeforeEach
        public void setUp() {
            String[] forbiddenWords = {"ass"};
            mtxStringFilter = new MtxStringFilter(StringFilteringPolicy.EXACT_ISOLATED, forbiddenWords);
        }

        @Test
        public void testFilter_noForbiddenWords() {
            String sampleString = "you failed you human";
            String expectedCensor = "you failed you human";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_yesForbiddenWords() {
            String sampleString = "you passed you ass";
            String expectedCensor = "you passed you ***";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_multipleForbiddenWords() {
            String sampleString = "you passed you ass in ass";
            String expectedCensor = "you passed you *** in ***";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }
    }

    @Nested
    class FilterTests_partialIsolated {
        @BeforeEach
        public void setUp() {
            String[] forbiddenWords = {"ass", "hoe"};
            mtxStringFilter = new MtxStringFilter(StringFilteringPolicy.PARTIAL_ISOLATED, forbiddenWords);
        }

        @Test
        public void testFilter_noForbiddenWords() {
            String sampleString = "you failed you human";
            String expectedCensor = "you failed you human";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_yesForbiddenWords() {
            String sampleString = "you passed you as!s";
            String expectedCensor = "you passed you ****";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }

        @Test
        public void testFilter_multipleForbiddenWords() {
            String sampleString = "you passed you a.ss h(oe";
            String expectedCensor = "you passed you **** ****";

            assertEquals(expectedCensor, mtxStringFilter.filter(sampleString));
        }
    }
}
