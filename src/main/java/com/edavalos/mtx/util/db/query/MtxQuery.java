package com.edavalos.mtx.util.db.query;

public abstract class MtxQuery {
    enum ComparisonOperator {
        EQUAL_TO, NOT_EQUAL_TO, GREATER_THAN, LESS_THAN,
        GREATER_THAN_OR_EQUAL_TO, LESS_THAN_OR_EQUAL_TO,
        LIKE, BETWEEN, AND, NOT, OR
    }

    @Override
    public abstract String toString();
}
