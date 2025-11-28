package com.edavalos.mtx.util.string.search;

import java.util.Arrays;

public class MtxBoyerMooreSearch implements MtxSearch {
    private static final int ALPHABET_SIZE = 256;
    private final String text;

    public MtxBoyerMooreSearch(String text) {
        this.text = text;
    }

    @Override
    public int search(String pattern) {
        int n = this.text.length();
        int m = pattern.length();

        int[] badChar = buildBadChar(pattern);

        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == this.text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                // match found
                return shift;
            } else {
                shift += Math.max(1, j - badChar[this.text.charAt(shift + j)]);
            }
        }
        return -1;
    }

    private static int[] buildBadChar(String pattern) {
        int[] badChar = new int[ALPHABET_SIZE];
        Arrays.fill(badChar, -1);

        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
        return badChar;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
