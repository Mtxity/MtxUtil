package com.edavalos.mtx.util.string.search;

import org.junit.jupiter.api.BeforeEach;
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
}
