package com.edavalos.mtx.util.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This object will take strings with templates (written as '${template}) and replace the templates with values assigned
 * by its internal {@link Map}. Either give it a Map during instantiation or add values later with <code>addSubstitution()
 * </code> and then call <code>substitute()</code> to apply the substitutions to a string with templates.
 * <p></p>
 * Note that if an unmodifiable Map is given to this MtxStringSubstituter, then <code>addSubstitution()</code> will always
 * throw an error.
 */
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

    /**
     * Add a template and value to replace it with.
     * @param key String template excluding the template borders ('${', '}').
     * @param replacement String to replace the template with.
     * @throws UnsupportedOperationException If the {@link Map} given to this in the constructor is unmodifiable.
     */
    public void addSubstitution(String key, String replacement) {
        try {
            this.substitutions.put(key, replacement);
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            throw new UnsupportedOperationException(UNSUPPORTED_MAP_ADDITIONS);
        }
    }

    /**
     * Replace all templates (wrapped in '${', '}') with the values provided in this MtxStringSubstituter's internal
     * {@link Map}.
     * @param inputWithTemplates String with templates to modify
     * @return Input string with all templates replaced with the values specified in this MtxStringSubstituter's Map.
     */
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
