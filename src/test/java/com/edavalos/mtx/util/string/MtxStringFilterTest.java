package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxStringFilterTest {
    private MtxStringFilter mtxStringFilter;

    @Nested
    class ConstructorTests {

        @Test
        public void testConstructor_withPolicy_withCharReplacement_withWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_STRICT;
            char expectedReplacementChar = '~';
            List<String> expectedWords = new ArrayList<>(){
                {
                    add("word1");
                    add("word2");
                }
            };

            mtxStringFilter = new MtxStringFilter(expectedPolicy, expectedReplacementChar, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords.toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_withPolicy_withCharReplacement_withWordArray() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_STRICT;
            char expectedReplacementChar = '~';
            String[] expectedWords = new String[] {"word1", "word2"};

            mtxStringFilter = new MtxStringFilter(expectedPolicy, expectedReplacementChar, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords, mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_withPolicy_withCharReplacement_noWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_STRICT;
            char expectedReplacementChar = '~';

            mtxStringFilter = new MtxStringFilter(expectedPolicy, expectedReplacementChar);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(new ArrayList<String>().toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_withPolicy_noCharReplacement_withWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_STRICT;
            char expectedReplacementChar = '*';
            List<String> expectedWords = new ArrayList<>(){
                {
                    add("word1");
                    add("word2");
                }
            };

            mtxStringFilter = new MtxStringFilter(expectedPolicy, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords.toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_withPolicy_noCharReplacement_withWordArray() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.PARTIAL_ISOLATED;
            char expectedReplacementChar = '*';
            String[] expectedWords = new String[] {"word1", "word2"};

            mtxStringFilter = new MtxStringFilter(expectedPolicy, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords, mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_withPolicy_noCharReplacement_noWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.PARTIAL_ISOLATED;
            char expectedReplacementChar = '*';

            mtxStringFilter = new MtxStringFilter(expectedPolicy);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(new ArrayList<String>().toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_withCharReplacement_withWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = '~';
            List<String> expectedWords = new ArrayList<>(){
                {
                    add("word1");
                    add("word2");
                }
            };

            mtxStringFilter = new MtxStringFilter(expectedReplacementChar, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords.toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_withCharReplacement_withWordArray() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = '~';
            String[] expectedWords = new String[] {"word1", "word2"};

            mtxStringFilter = new MtxStringFilter(expectedReplacementChar, expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords, mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_withCharReplacement_noWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = ':';

            mtxStringFilter = new MtxStringFilter(expectedReplacementChar);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(new ArrayList<>().toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_noCharReplacement_withWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = '*';
            List<String> expectedWords = new ArrayList<>(){
                {
                    add("word1");
                    add("word2");
                }
            };

            mtxStringFilter = new MtxStringFilter(expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords.toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_noCharReplacement_withWordArray() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = '*';
            String[] expectedWords = new String[] {"word1", "word2"};

            mtxStringFilter = new MtxStringFilter(expectedWords);

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(expectedWords, mtxStringFilter.getForbiddenWords().toArray());
        }

        @Test
        public void testConstructor_noPolicy_noCharReplacement_noWordList() {
            StringFilteringPolicy expectedPolicy = StringFilteringPolicy.EXACT_ISOLATED;
            char expectedReplacementChar = '*';

            mtxStringFilter = new MtxStringFilter();

            assertEquals(expectedPolicy, mtxStringFilter.getFilteringPolicy());
            assertEquals(expectedReplacementChar, mtxStringFilter.getCharReplacement());
            assertArrayEquals(new ArrayList<>().toArray(), mtxStringFilter.getForbiddenWords().toArray());
        }
    }

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
