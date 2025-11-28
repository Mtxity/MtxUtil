package com.edavalos.mtx.util.string.search;

public class MtxNaiveSearch {
    private final String text;

    public MtxNaiveSearch(String text) {
        this.text = text;
    }

    public int search(String pattern) {
        int n = this.text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && this.text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                // match found
                return i;
            }
        }
        return -1;
    }

    public String getText() {
        return this.text;
    }
}
