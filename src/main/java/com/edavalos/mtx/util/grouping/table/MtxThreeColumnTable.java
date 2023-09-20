package com.edavalos.mtx.util.grouping.table;

import java.util.ArrayList;
import java.util.HashMap;

public class MtxThreeColumnTable<A, B, C> {
    protected record MtxDouble<a, b, c>(a primaryKey, b col1, c col2) {}

    private final HashMap<A, MtxDouble<A, B, C>> rowsMap;
    private final ArrayList<MtxDouble<A, B, C>> rowsList;

    public MtxThreeColumnTable() {
        this.rowsMap = new HashMap<>();
        this.rowsList = new ArrayList<>();
    }

    public void addRow(A primaryKey, B column1, C column2) {
        if (this.rowsMap.containsKey(primaryKey)) {
            throw new ExistingPrimaryKeyException(primaryKey);
        }

        MtxDouble<A, B, C> newRow = new MtxDouble<>(primaryKey, column1, column2);
        this.rowsMap.put(primaryKey, newRow);
        this.rowsList.add(newRow);
    }
}
