package com.edavalos.mtx.util.db.query;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MtxSelectQuery extends MtxQuery {
    public enum Order {
        ASC, DESC
    }

    private String FROM;
    private final String SELECT;
    private final HashMap<String, Comparison> INNER_JOIN;
    private final HashMap<String, Comparison> LEFT_JOIN;
    private final HashMap<String, Comparison> RIGHT_JOIN;
    private final HashMap<String, Comparison> FULL_JOIN;
    private final List<Comparison> WHERE;
    private final List<String> ORDERBY_ASC;
    private final List<String> ORDERBY_DESC;

    public MtxSelectQuery(String select) {
        this.SELECT = select;
        this.INNER_JOIN = new HashMap<>();
        this.LEFT_JOIN = new HashMap<>();
        this.RIGHT_JOIN = new HashMap<>();
        this.FULL_JOIN = new HashMap<>();
        this.WHERE = new LinkedList<>();
        this.ORDERBY_ASC = new LinkedList<>();
        this.ORDERBY_DESC = new LinkedList<>();
    }

    public MtxSelectQuery() {
        this("*");
    }

    public MtxSelectQuery from(String table) {
        this.FROM = table;
        super.complete = true;
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
        // OR cannot be first or last
        if (!this.WHERE.isEmpty() && this.WHERE.getLast().isOr()) {
            this.WHERE.add(new Comparison(null, ComparisonOperator.OR, null));
        }
        return this;
    }

    public MtxSelectQuery orderBy(String column, Order order) {
        if (Order.DESC == order) {
            this.ORDERBY_DESC.add(column);
        } else {
            this.ORDERBY_ASC.add(column);
        }
        return this;
    }

    @Override
    public String toString() {
        if (!super.isComplete()) {
            throw new IllegalStateException("MtxSelectQuery is missing elements required to convert it to a String!");
        }

        StringBuilder qb = new StringBuilder();
        qb.append("SELECT ").append(this.SELECT).append(" FROM ").append(this.FROM);
        if (!this.WHERE.isEmpty()) {
            qb.append(" WHERE (");
            for (int i = 0; i < this.WHERE.size(); i++) {
                Comparison comparison = this.WHERE.get(i);
                if (comparison.isOr()) {
                    qb.append(") OR (");
                } else {
                    qb.append(comparison.toString());
                    if (i != this.WHERE.size() - 1) {
                        qb.append(" AND ");
                    }
                }
            }
            qb.append(")");
        }

        return qb.toString();
    }
}