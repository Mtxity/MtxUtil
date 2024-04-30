package com.edavalos.mtx.util.db.query;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MtxSelectQuery extends MtxQuery {
    private String FROM;
    private final String SELECT;
    private final HashMap<String, Comparison> INNER_JOIN;
    private final HashMap<String, Comparison> LEFT_JOIN;
    private final HashMap<String, Comparison> RIGHT_JOIN;
    private final HashMap<String, Comparison> FULL_JOIN;
    private final List<Comparison> WHERE;
    private final List<Comparison> ORDERBY;
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

    public MtxSelectQuery leftJoin(String otherTable, Comparison condition) {
        this.LEFT_JOIN.put(otherTable, condition);
        return this;
    }

    public MtxSelectQuery rightJoin(String otherTable, Comparison condition) {
        this.RIGHT_JOIN.put(otherTable, condition);
        return this;
    }

    public MtxSelectQuery innerJoin(String otherTable, Comparison condition) {
        this.INNER_JOIN.put(otherTable, condition);
        return this;
    }

    public MtxSelectQuery fullJoin(String otherTable, Comparison condition) {
        this.FULL_JOIN.put(otherTable, condition);
        return this;
    }

    public MtxSelectQuery where(Comparison condition) {
        this.WHERE.add(condition);
        return this;
    }

    public MtxSelectQuery or() {
        this.WHERE.add(new Comparison(null, ComparisonOperator.OR, null));
        return this;
    }

}