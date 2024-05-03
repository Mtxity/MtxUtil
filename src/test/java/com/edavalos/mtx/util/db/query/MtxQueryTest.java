package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxQueryTest {

    @Nested
    class TestComparison {
        private MtxQuery.Comparison comparison;

        @Test
        public void testToString() {
            String sampleLeft = "leftTable";
            String sampleRight = "rightTable";

            for (MtxQuery.ComparisonOperator compOp : MtxQuery.ComparisonOperator.values()) {
                comparison = new MtxQuery.Comparison(sampleLeft, compOp, sampleRight);
                String expected = "(" + sampleLeft + " " + compOp.getKeyword() + " " + sampleRight + ")";
                String actual = comparison.toString();

                assertEquals(expected, actual);
            }
        }

        @Test
        public void testIsOr() {
            comparison = new MtxQuery.Comparison("table1", MtxQuery.ComparisonOperator.AND, "table2");
            assertFalse(comparison.isOr());

            comparison = new MtxQuery.Comparison(null, MtxQuery.ComparisonOperator.AND, null);
            assertFalse(comparison.isOr());

            comparison = new MtxQuery.Comparison(null, MtxQuery.ComparisonOperator.OR, null);
            assertTrue(comparison.isOr());
        }
    }
}
