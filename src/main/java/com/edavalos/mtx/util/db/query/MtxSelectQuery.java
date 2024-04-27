package com.edavalos.mtx.util.db.query;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MtxSelectQuery extends MtxQuery {
    private String FROM;
    private final String SELECT;
    private final HashMap<String, String> INNER_JOIN;
    private final HashMap<String, String> LEFT_JOIN;
    private final HashMap<String, String> RIGHT_JOIN;
    private final HashMap<String, String> FULL_JOIN;
    private final List<String> WHERE;
    private final List<String> ORDERBY;
    private boolean complete;

    public MtxSelectQuery(String select) {
        this.SELECT = select;
        this.INNER_JOIN = new HashMap<>();
        this.LEFT_JOIN = new HashMap<>();
        this.RIGHT_JOIN = new HashMap<>();
        this.FULL_JOIN = new HashMap<>();
        this.WHERE = new LinkedList<>();
        this.ORDERBY = new LinkedList<>();
        this.complete = false;
    }

    public MtxSelectQuery() {
        this("*");
    }

    public MtxSelectQuery from(String table) {
        this.FROM = table;
        this.complete = true;
        return this;
    }

    public MtxSelectQuery leftJoin(String otherTable, String operator) {
        this.LEFT_JOIN.put(otherTable, operator);
        return this;
    }

    public MtxSelectQuery rightJoin(String otherTable, String operator) {
        this.RIGHT_JOIN.put(otherTable, operator);
        return this;
    }

    public MtxSelectQuery innerJoin(String otherTable, String operator) {
        this.INNER_JOIN.put(otherTable, operator);
        return this;
    }

    public MtxSelectQuery fullJoin(String otherTable, String operator) {
        this.FULL_JOIN.put(otherTable, operator);
        return this;
    }

}