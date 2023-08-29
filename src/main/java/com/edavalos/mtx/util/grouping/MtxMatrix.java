package com.edavalos.mtx.util.grouping;

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
}
