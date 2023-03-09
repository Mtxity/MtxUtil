package com.edavalos.mtx.util.string;

public final class MtxMatrixFormatter {
    private MtxMatrixFormatter() { }

    public static String format(String[][] matrix) {
        int maxLen = findMaxLengthInMatrix(matrix) + 1;

        StringBuilder s = new StringBuilder();
        for (String[] row : matrix) {
            for (String col : row) {
                s.append(padString(col, maxLen));
            }
            s.append("\n");
        }
        return s.toString().stripTrailing();
    }

    public static String formatBorder(String[][] mat) {
        int maxLen = findMaxLengthInMatrix(mat) + 1;

        StringBuilder s = new StringBuilder("+");
        for (int i = 0; i < mat[0].length; i++) {
            s.append(repeatString("-", maxLen)).append("+");
        }
        s.append("\n");

        for (String[] row : mat) {
            s.append("|");
            for (String col : row) {
                s.append(padString(col, maxLen)).append("|");
            }
            s.append("\n+");
            for (int i = 0; i < row.length; i++) {
                s.append(repeatString("-", maxLen)).append("+");
            }
            s.append("\n");
        }
        return s.toString().stripTrailing();
    }

    private static int findMaxLengthInMatrix(String[][] mat) {
        int len = 0;
        for (String[] row : mat) {
            for (String col : row) {
                len = Math.max(len, col.length());
            }
        }
        return len;
    }

    private static String padString(String s, int length) {
        return new String(new char[length - s.length()]).replace('\0', ' ') + s;
    }

    private static String repeatString(String s, int times) {
        return new String(new char[times]).replace("\0", s);
    }
}
