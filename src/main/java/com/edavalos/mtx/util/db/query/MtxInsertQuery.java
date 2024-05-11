package com.edavalos.mtx.util.db.query;

import java.util.LinkedList;
import java.util.List;

public class MtxInsertQuery {
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
}
