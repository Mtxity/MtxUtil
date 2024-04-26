package com.edavalos.mtx.util.db.query;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MtxSelectQuery extends MtxQuery {
    private String FROM;
    private final String SELECT;
    private final HashMap<String, String> INNER_JOIN;
    private final HashMap<String, String> OUTER_JOIN;
    private final List<String> WHERE;
    private final List<String> ORDERBY;
    private boolean complete;

    public MtxSelectQuery(String select) {
        this.SELECT = select;
        this.INNER_JOIN = new HashMap<>();
        this.OUTER_JOIN = new HashMap<>();
        this.WHERE = new LinkedList<>();
        this.ORDERBY = new LinkedList<>();
        this.complete = false;
    }

    public MtxSelectQuery() {
        this("*");
    }

    public MtxSelectQuery from(String table) {
        this.FROM = table;
        return this;
    }

}