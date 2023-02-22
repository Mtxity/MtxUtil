package com.edavalos.mtx.util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class MtxStringFilter {
    private static final char DEFAULT_CHAR_REPLACEMENT = '*';
    private static final StringFilteringPolicy DEFAULT_FILTERING_POLICY = StringFilteringPolicy.EXACT_ISOLATED;

    private final StringFilteringPolicy filteringPolicy;
    private final char charReplacement;
    private Set<String> forbiddenWords;

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement, Set<String> forbiddenWords) {
        this.filteringPolicy = filteringPolicy;
        this.charReplacement = charReplacement;
        this.forbiddenWords = new HashSet<>(forbiddenWords);
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement, String[] forbiddenWords) {
        this(filteringPolicy, charReplacement, new HashSet<>(List.of(forbiddenWords)));
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, char charReplacement) {
        this(filteringPolicy, charReplacement, new HashSet<>());
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, Set<String> forbiddenWords) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, forbiddenWords);
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy, String[] forbiddenWords) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, new HashSet<>(List.of(forbiddenWords)));
    }

    public MtxStringFilter(StringFilteringPolicy filteringPolicy) {
        this(filteringPolicy, DEFAULT_CHAR_REPLACEMENT, new HashSet<>());
    }

    public MtxStringFilter(char charReplacement, Set<String> forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, forbiddenWords);
    }

    public MtxStringFilter(char charReplacement, String[] forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, new HashSet<>(List.of(forbiddenWords)));
    }

    public MtxStringFilter(char charReplacement) {
        this(DEFAULT_FILTERING_POLICY, charReplacement, new HashSet<>());
    }

    public MtxStringFilter(Set<String> forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, forbiddenWords);
    }

    public MtxStringFilter(String[] forbiddenWords) {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, new HashSet<>(List.of(forbiddenWords)));
    }

    public MtxStringFilter() {
        this(DEFAULT_FILTERING_POLICY, DEFAULT_CHAR_REPLACEMENT, new HashSet<>());
    }

    // ---------------------- Public Methods -----------------------

    public String filter(String unfilteredString) {
        return switch (this.filteringPolicy) {
            case EXACT_STRICT -> {
                for (String forbiddenWord : this.forbiddenWords) {
                    unfilteredString = unfilteredString.replaceAll(Pattern.quote(forbiddenWord), buildCensor(forbiddenWord.length()));
                }
                yield unfilteredString;
            }
            case EXACT_ISOLATED -> {
                String[] words = unfilteredString.split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (String forbiddenWord : this.forbiddenWords) {
                        if (words[i].equalsIgnoreCase(forbiddenWord)) {
                            words[i] = buildCensor(forbiddenWord.length());
                        }
                    }
                }
                yield this.join(words);
            }
            case PARTIAL_ISOLATED -> {
                String[] words = unfilteredString.split(" ");
                for (int i = 0; i < words.length; i++) {
                    String literal = words[i].replaceAll("[^a-zA-Z0-9]", "");
                    for (String forbiddenWord : this.forbiddenWords) {
                        if (literal.equalsIgnoreCase(forbiddenWord)) {
                            words[i] = buildCensor(words[i].length());
                        }
                    }
                }
                yield this.join(words);
            }
        };
    }

    public StringFilteringPolicy getFilteringPolicy() {
        return this.filteringPolicy;
    }

    public char getCharReplacement() {
        return this.charReplacement;
    }

    public Set<String> getForbiddenWords() {
        return this.forbiddenWords;
    }

    public void setForbiddenWords(Set<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    public void setForbiddenWords(String[] forbiddenWords) {
        this.forbiddenWords = new HashSet<>(new ArrayList<>(Arrays.asList(forbiddenWords)));
    }

    public boolean addForbiddenWord(String newForbiddenWord) {
        return this.forbiddenWords.add(newForbiddenWord);
    }

    public boolean removeForbiddenWord(String forbiddenWord) {
        return this.forbiddenWords.remove(forbiddenWord);
    }

    // ------------------ Private Helper Methods -------------------

    private String buildCensor(int length) {
        StringBuilder censor = new StringBuilder();
        for (int i = 0; i < length; i++) {
            censor.append(this.charReplacement);
        }
        return censor.toString();
    }

    private String join(String[] stringArray) {
        StringBuilder strings = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            strings.append(stringArray[i] + " ");
        }
        return strings.toString().strip();
    }
}
