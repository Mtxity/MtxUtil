package com.edavalos.mtx.util.string.search;

// Knuth–Morris–Pratt
public class MtxKmpSearch {
    private final String text;

    public MtxKmpSearch(String text) {
        this.text = text;
    }

    public int search(String pattern) {
        int n = this.text.length();
        int m = pattern.length();

        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;

        while (i < n) {
            if (this.text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    // match found
                    return i - j;
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    private static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    public String getText() {
        return this.text;
    }
}
