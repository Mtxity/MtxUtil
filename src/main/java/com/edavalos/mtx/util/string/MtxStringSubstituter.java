package com.edavalos.mtx.util.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MtxStringSubstituter {
    protected static final Pattern KEYWORD_WRAPPER = Pattern.compile("\\$\\{([^}]+)}");
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
        Matcher matcher = KEYWORD_WRAPPER.matcher(inputWithTemplates);
        String result = inputWithTemplates;
        while (matcher.find()) {
            String match = matcher.group(1);
            String sub = this.substitutions.get(match);
            if (sub != null) {
                result.replace("${" + match + "}", sub);
            }
        }
        return result;
    }
}
