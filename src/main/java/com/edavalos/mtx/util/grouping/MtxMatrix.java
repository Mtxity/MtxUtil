package com.edavalos.mtx.util.grouping;

import com.edavalos.mtx.util.string.MtxStringUtil;

import java.util.regex.Pattern;

public class MtxMatrix {
    private final int rows;
    private final int cols;
    private final int[][] matrix;

    public MtxMatrix(int rows, int columns) {
        this.matrix = new int[rows][columns];
        this.rows = rows;
        this.cols = columns;
    }

    public MtxMatrix(int[]... rows) {
        int columns = 0;
        for (int[] row : rows) {
            if (row.length > columns) {
                columns = row.length;
            }
        }
        for (int i = 0; i < rows.length; i++) {
            int[] newRow = new int[columns];
            for (int j = 0; j < rows[i].length; j++) {
                newRow[j] = rows[i][j];
            }
            rows[i] = newRow;
        }

        this.matrix = rows;
        this.rows = rows.length;
        this.cols = columns;
    }

    @Override
    public String toString() {
        if (this.rows == 0) {
            return "[ ]";
        }

        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < this.rows; i++) {
            int intLen;
            for (int j = 0; j < this.cols; j++) {
                intLen = String.valueOf(this.matrix[i][j]).length();
                if (intLen > maxLen) {
                    maxLen = intLen;
                }
            }
            intLen = String.valueOf(this.matrix[i][this.cols - 1]).length();
            if (intLen > maxEnd) {
                maxEnd = intLen;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            sb.append("  [");
            for (int j = 0; j < this.cols - 1; j++) {
                sb.append(MtxStringUtil.rightPad(this.matrix[i][j] + ", ", maxLen + 2));
            }
            sb.append(MtxStringUtil.rightPad(String.valueOf(this.matrix[i][this.cols - 1]), maxEnd));
            sb.append("],\n");
        }
        sb.append("eod");

        return sb.toString()
                 .replaceFirst(Pattern.quote("  ["), "[ [")
                 .replace("],\neod", "] ]");
    }
}
