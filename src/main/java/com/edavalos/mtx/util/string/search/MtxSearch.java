package com.edavalos.mtx.util.string.search;

/**
 * Strategy interface for searching a pattern within a text.
 * <p>
 * Implementations define how the search is performed (e.g., naive, KMP, Boyer–Moore,
 * case sensitivity, locale rules, etc.). The search is performed against the text
 * returned by {@link #getText()} and should return the starting index of the first
 * match, or -1 if no match is found.
 */
public interface MtxSearch {

    /**
     * Searches the {@linkplain #getText() text} for the first occurrence of the given pattern.
     * <ul>
     *   <li>The returned index is zero-based and refers to the start of the match.</li>
     *   <li>Returns -1 if the pattern cannot be found.</li>
     * </ul>
     *
     * @param pattern the sequence to search for; must not be null
     * @return the zero-based index of the first occurrence, or -1 if not found
     * @throws NullPointerException if {@code pattern} is null (implementations are expected to reject null)
     * @apiNote Implementations should document whether the search is case-sensitive and how empty patterns are handled.
     * The recommended behavior for an empty pattern is to return {@code 0}.
     */
    int search(String pattern);

    /**
     * Returns whether the {@linkplain #getText() text} contains the given pattern at least once.
     * <p>
     * This is a convenience wrapper around {@link #search(String)} and is equivalent to:
     * {@code search(pattern) != -1}.
     *
     * @param pattern the sequence to look for
     * @return {@code true} if the pattern occurs at least once; {@code false} otherwise
     * @throws RuntimeException any exception thrown by {@link #search(String)} is propagated
     */
    default boolean contains(String pattern) {
        return this.search(pattern) != -1;
    }

    /**
     * Returns the text that this search operates on.
     *
     * @return the text to be searched; may be empty but should not be null
     * @implNote If an implementation allows the underlying text reference to change over time,
     * results of successive searches may vary accordingly.
     */
    String getText();
}
