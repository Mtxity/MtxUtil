package com.edavalos.mtx.util.grouping.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MtxThreeColumnTable<A, B, C> {
    public record MtxTriple<a, b, c>(a first, b second, c third) {}

    private final HashMap<A, MtxTriple<A, B, C>> rowsMap;
    private final ArrayList<MtxTriple<A, B, C>> rowsList;

    public MtxThreeColumnTable() {
        this.rowsMap = new HashMap<>();
        this.rowsList = new ArrayList<>();
    }

    public void addRow(A primaryKey, B column1, C column2) {
        if (this.rowsMap.containsKey(primaryKey)) {
            throw new ExistingPrimaryKeyException(primaryKey);
        }

        MtxTriple<A, B, C> newRow = new MtxTriple<>(primaryKey, column1, column2);
        this.rowsMap.put(primaryKey, newRow);
        this.rowsList.add(newRow);
    }

    public MtxTriple<A, B, C> getRowFromPrimaryKey(A primaryKey) {
        return this.rowsMap.get(primaryKey);
    }

    public List<MtxTriple<A, B, C>> getRowsFromMatchingSecondColumn(B value) {
        return this.rowsList.stream().filter(row -> row.second().equals(value)).collect(Collectors.toList());
    }
}
