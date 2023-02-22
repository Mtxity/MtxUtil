package com.edavalos.mtx.util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MtxStringFilter {
    private static final char DEFAULT_CHAR_REPLACEMENT = '*';
    private static final StringFilteringPolicy DEFAULT_FILTERING_POLICY = StringFilteringPolicy.EXACT_ISOLATED;

    private final StringFilteringPolicy filteringPolicy;
    private final char charReplacement;
    private List<String> forbiddenWords;

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement, List<String> forbiddenWords) {
        this.filteringPolicy = filteringPolicy;
        this.charReplacement = charReplacement;
        this.forbiddenWords = forbiddenWords;
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement, String[] forbiddenWords) {
        this(filteringPolicy, charReplacement, new ArrayList<>(Arrays.asList(forbiddenWords)));
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement) {
        this(filteringPolicy, charReplacement, new ArrayList<>());
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, List<String> forbiddenWords) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, forbiddenWords);
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, String[] forbiddenWords) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, new ArrayList<>(Arrays.asList(forbiddenWords)));
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, new ArrayList<>());
    }

    public MtxStringFilter(char charReplacement, List<String> forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, forbiddenWords);
    }

    public MtxStringFilter(char charReplacement, String[] forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, new ArrayList<>(Arrays.asList(forbiddenWords)));
    }

    public MtxStringFilter(char charReplacement) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, new ArrayList<>());
    }

    public MtxStringFilter(List<String> forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, forbiddenWords);
    }

    public MtxStringFilter(String[] forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, new ArrayList<>(Arrays.asList(forbiddenWords)));
    }

    public MtxStringFilter() {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, new ArrayList<>());
    }

    // ---------------------- Public Methods -----------------------

    public String filter(String unfilteredString) {
        return null;
    }

    public StringFilteringPolicy getFilteringPolicy() {
        return this.filteringPolicy;
    }

    public char getCharReplacement() {
        return this.charReplacement;
    }

    public List<String> getForbiddenWords() {
        return this.forbiddenWords;
    }

    public void setForbiddenWords(List<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    public void setForbiddenWords(String[] forbiddenWords) {
        this.forbiddenWords = new ArrayList<>(Arrays.asList(forbiddenWords));
    }

    public boolean addForbiddenWord(String newForbiddenWord) {
        if (this.forbiddenWords.contains(newForbiddenWord)) {
            return false;
        } else {
            this.forbiddenWords.add(newForbiddenWord);
            return true;
        }
    }

    public boolean removeForbiddenWord(String forbiddenWord) {
        return this.forbiddenWords.remove(forbiddenWord);
    }
}
