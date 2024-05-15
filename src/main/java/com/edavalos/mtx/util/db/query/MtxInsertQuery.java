package com.edavalos.mtx.util.db.query;

import java.util.LinkedList;
import java.util.List;

public class MtxInsertQuery extends MtxQuery {
    private final String INSERT;
    private final boolean insertIntoAllColumns;
    private final String[] INTO;
    private final int numberOfColumns;
    private final List<String[]> VALUES;

    public MtxInsertQuery(String table, int numberOfColumns) {
        this.INSERT = table;
        this.insertIntoAllColumns = true;
        this.INTO = null;
        this.numberOfColumns = numberOfColumns;
        this.VALUES = new LinkedList<>();
    }

    public MtxInsertQuery(String table, String... columns) {
        this.INSERT = table;
        this.insertIntoAllColumns = false;
        this.INTO = columns;
        this.numberOfColumns = columns.length;
        this.VALUES = new LinkedList<>();
    }

    public MtxInsertQuery values(String... values) {
        if (values.length != this.numberOfColumns) {
            throw new ArrayIndexOutOfBoundsException(
                    "MtxInsertQuery expected " + this.numberOfColumns + " values, got: " + values.length
            );
        }
        this.VALUES.add(values);
        super.complete = true;
        return this;
    }

    @Override
    public String toString() {
        if (!super.isComplete()) {
            throw new IllegalStateException("MtxInsertQuery is missing elements required to convert it to a String!");
        }

        StringBuilder qb = new StringBuilder();
        qb.append("INSERT INTO ").append(this.INSERT);
        if (!this.insertIntoAllColumns && this.INTO != null) {
            qb.append(" (").append(String.join(", ", this.INTO)).append(")");
        }

        qb.append(" VALUES ");
        int rowCount = this.VALUES.size();
        String[] rows = new String[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rows[i] = "('" + String.join("', '", this.VALUES.get(i)) + "')";
        }

        return qb.append(String.join(", ", rows)).append(";").toString();
    }
}
