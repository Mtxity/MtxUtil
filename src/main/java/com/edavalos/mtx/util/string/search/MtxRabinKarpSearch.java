package com.edavalos.mtx.util.string.search;

public class MtxRabinKarpSearch implements MtxSearch {
    private static final int BASE = 256;
    private static final int MOD = 101;
    private final String text;

    public MtxRabinKarpSearch(String text) {
        this.text = text;
    }

    @Override
    public int search(String pattern) {
        int n = this.text.length();
        int m = pattern.length();

        int pHash = 0;
        int tHash = 0, h = 1;

        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            pHash = (BASE * pHash + pattern.charAt(i)) % MOD;
            tHash = (BASE * tHash + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (pHash == tHash) {
                if (this.text.regionMatches(i, pattern, 0, m)) {
                    return i;
                }
            }

            if (i < n - m) {
                tHash = (BASE * (tHash - this.text.charAt(i) * h) + this.text.charAt(i + 1 + m - 1)) % MOD;
                if (tHash < 0) {
                    tHash += MOD;
                }
            }
        }
        return -1;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
