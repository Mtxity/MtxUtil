package com.edavalos.mtx.util.db.query;

public abstract class MtxQuery {
    static class Comparison {
        private final String leftVal;
        private final ComparisonOperator operator;
        private final String rightVal;

        Comparison(String leftVal, ComparisonOperator operator, String rightVal) {
            this.leftVal = leftVal;
            this.rightVal = rightVal;
            this.operator = operator;
        }

        @Override
        public String toString() {
            return "(" + this.leftVal + " " + this.operator.getKeyword() + " " + this.rightVal + ")";
        }
    }

    enum ComparisonOperator {
        EQUAL_TO("="), NOT_EQUAL_TO("<>"), GREATER_THAN(">"), LESS_THAN("<"),
        GREATER_THAN_OR_EQUAL_TO(">="), LESS_THAN_OR_EQUAL_TO("<="),
        LIKE, BETWEEN, AND, NOT, OR;

        private final String keyword;

        ComparisonOperator(String keyword) {
            this.keyword = keyword;
        }

        ComparisonOperator() {
            this.keyword = this.name().replaceAll("_", " ");
        }

        public String getKeyword() {
            return this.keyword;
        }
    }

    @Override
    public abstract String toString();
}
