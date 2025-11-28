package com.edavalos.mtx.util.string.search;

public class MtxNaiveSearch implements MtxSearch {
    private final String text;

    public MtxNaiveSearch(String text) {
        this.text = text;
    }

    @Override
    public int search(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return -1;
        }

        int n = this.text.length();
        int m = pattern.length();
        if (m > n) {
            return -1;
        }

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

    @Override
    public String getText() {
        return this.text;
    }
}
