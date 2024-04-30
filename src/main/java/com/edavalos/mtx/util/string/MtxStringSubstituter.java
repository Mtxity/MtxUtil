package com.edavalos.mtx.util.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MtxStringSubstituter {
    protected static final String MATCH_BOUND_LEFT_BORDER = "${";
    protected static final String MATCH_BOUND_RIGHT_BORDER = "}";
    protected static final String UNSUPPORTED_MAP_ADDITIONS = "This MtxStringSubstituter was initialized with a map " +
                                                              "that does not allow modifications!";

    private final Map<String, String> substitutions;

    public MtxStringSubstituter(Map<String, String> substitutions) {
        this.substitutions = substitutions;
    }

    public MtxStringSubstituter() {
        this.substitutions = new HashMap<>();
    }

    public void addSubstitution(String key, String replacement) {
        try {
            this.substitutions.put(key, replacement);
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            throw new UnsupportedOperationException(UNSUPPORTED_MAP_ADDITIONS);
        }
    }

    public String substitute(String inputWithTemplates) {
        for (Map.Entry<String, String> sub : this.substitutions.entrySet()) {
            inputWithTemplates = inputWithTemplates.replaceAll(Pattern.quote(wrapWord(sub.getKey())), sub.getValue());
        }
        return inputWithTemplates;
    }

    private static String wrapWord(String input) {
        return MATCH_BOUND_LEFT_BORDER + input + MATCH_BOUND_RIGHT_BORDER;
    }
}
