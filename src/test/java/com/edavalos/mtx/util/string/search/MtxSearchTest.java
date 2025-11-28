package com.edavalos.mtx.util.string.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxSearchTest {
    private static final String SAMPLE_TEXT = "This is a sample text to search in.";

    private MtxSearch mtxNativeSearch;
    private MtxSearch mtxBoyerMooreSearch;
    private MtxSearch mtxKnuthMorrisPrattSearch;
    private MtxSearch mtxRabinKarpSearch;

    @BeforeEach
    public void setUp() {
        mtxNativeSearch = new MtxNaiveSearch(SAMPLE_TEXT);
        mtxBoyerMooreSearch = new MtxBoyerMooreSearch(SAMPLE_TEXT);
        mtxKnuthMorrisPrattSearch = new MtxKmpSearch(SAMPLE_TEXT);
        mtxRabinKarpSearch = new MtxRabinKarpSearch(SAMPLE_TEXT);
    }

    @Test
    public void testGetText() {
        assertEquals(SAMPLE_TEXT, mtxNativeSearch.getText());
        assertEquals(SAMPLE_TEXT, mtxBoyerMooreSearch.getText());
        assertEquals(SAMPLE_TEXT, mtxKnuthMorrisPrattSearch.getText());
        assertEquals(SAMPLE_TEXT, mtxRabinKarpSearch.getText());
    }

    @Nested
    class SearchTests {
        private static final String SAMPLE_PATTERN_MIDDLE = "sample";
        private static final String SAMPLE_PATTERN_BEGINNING = "This";
        private static final String SAMPLE_PATTERN_END = "in.";
        private static final String SAMPLE_PATTERN_NO_MATCH = "xyz";
        private static final String SAMPLE_PATTERN_EMPTY = "";

        @Test
        public void testSearch_middle() {
            assertEquals(10, mtxNativeSearch.search(SAMPLE_PATTERN_MIDDLE));
            assertEquals(10, mtxBoyerMooreSearch.search(SAMPLE_PATTERN_MIDDLE));
            assertEquals(10, mtxKnuthMorrisPrattSearch.search(SAMPLE_PATTERN_MIDDLE));
            assertEquals(10, mtxRabinKarpSearch.search(SAMPLE_PATTERN_MIDDLE));
        }

        @Test
        public void testSearch_beginning() {
            assertEquals(0, mtxNativeSearch.search(SAMPLE_PATTERN_BEGINNING));
            assertEquals(0, mtxBoyerMooreSearch.search(SAMPLE_PATTERN_BEGINNING));
            assertEquals(0, mtxKnuthMorrisPrattSearch.search(SAMPLE_PATTERN_BEGINNING));
            assertEquals(0, mtxRabinKarpSearch.search(SAMPLE_PATTERN_BEGINNING));
        }

        @Test
        public void testSearch_end() {
            assertEquals(32, mtxNativeSearch.search(SAMPLE_PATTERN_END));
            assertEquals(32, mtxBoyerMooreSearch.search(SAMPLE_PATTERN_END));
            assertEquals(32, mtxKnuthMorrisPrattSearch.search(SAMPLE_PATTERN_END));
            assertEquals(32, mtxRabinKarpSearch.search(SAMPLE_PATTERN_END));
        }

        @Test
        public void testSearch_noMatch() {
            assertEquals(-1, mtxNativeSearch.search(SAMPLE_PATTERN_NO_MATCH));
            assertEquals(-1, mtxBoyerMooreSearch.search(SAMPLE_PATTERN_NO_MATCH));
            assertEquals(-1, mtxKnuthMorrisPrattSearch.search(SAMPLE_PATTERN_NO_MATCH));
            assertEquals(-1, mtxRabinKarpSearch.search(SAMPLE_PATTERN_NO_MATCH));
        }

        @Test
        public void testSearch_emptyPattern() {
            assertEquals(-1, mtxNativeSearch.search(SAMPLE_PATTERN_EMPTY));
            assertEquals(-1, mtxBoyerMooreSearch.search(SAMPLE_PATTERN_EMPTY));
            assertEquals(-1, mtxKnuthMorrisPrattSearch.search(SAMPLE_PATTERN_EMPTY));
            assertEquals(-1, mtxRabinKarpSearch.search(SAMPLE_PATTERN_EMPTY));
        }

        @Test
        public void testSearch_nullPattern() {
            assertEquals(-1, mtxNativeSearch.search(null));
            assertEquals(-1, mtxBoyerMooreSearch.search(null));
            assertEquals(-1, mtxKnuthMorrisPrattSearch.search(null));
            assertEquals(-1, mtxRabinKarpSearch.search(null));
        }

        @Test
        public void testSearch_patternLongerThanText() {
            String tooLong = SAMPLE_TEXT + "x";
            assertEquals(-1, mtxNativeSearch.search(tooLong));
            assertEquals(-1, mtxBoyerMooreSearch.search(tooLong));
            assertEquals(-1, mtxKnuthMorrisPrattSearch.search(tooLong));
            assertEquals(-1, mtxRabinKarpSearch.search(tooLong));
        }

        @Test
        public void testSearch_patternEqualsEntireText() {
            assertEquals(0, mtxNativeSearch.search(SAMPLE_TEXT));
            assertEquals(0, mtxBoyerMooreSearch.search(SAMPLE_TEXT));
            assertEquals(0, mtxKnuthMorrisPrattSearch.search(SAMPLE_TEXT));
            assertEquals(0, mtxRabinKarpSearch.search(SAMPLE_TEXT));
        }
    }

    @Nested
    class AlgorithmBranchCoverage {

        @Test
        public void testKmp_fallbackBranch_jNotZero() {
            // Triggers KMP mismatch with j != 0 (uses LPS fallback), then finds a match at index 2
            MtxSearch kmp = new MtxKmpSearch("aaaaab");
            assertEquals(2, kmp.search("aaab"));
        }

        @Test
        public void testBoyerMoore_shiftEqualsOneBranch() {
            // Forces a mismatch where the bad character appears just before j in the pattern,
            // yielding a shift of exactly 1 via Math.max(1, j - badChar[ch])
            MtxSearch bm = new MtxBoyerMooreSearch("abb");
            assertEquals(-1, bm.search("aba"));
        }

        @Test
        public void testRabinKarp_hashCollisionButNoMatch() {
            // Construct a known hash collision for m=2 under BASE=256, MOD=101:
            // pattern "AA" and text window "B\u001F" produce the same hash modulo 101,
            // so pHash == tHash but regionMatches is false.
            MtxSearch rk = new MtxRabinKarpSearch("B\u001F");
            assertEquals(-1, rk.search("AA"));
        }
    }
}