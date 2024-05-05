package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxQueryTest {

    @Nested
    class ComparisonTests {
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

    @Nested
    class ComparisonGeneratorTests {
        private static String f1 = "1";
        private static String f2 = "2";
        private MtxQuery.Comparison comparison;

        @Test
        public void testEq() {
            comparison = MtxQuery.eq(f1, f2);
            String expected = "(1 = 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testNeq() {
            comparison = MtxQuery.neq(f1, f2);
            String expected = "(1 <> 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testGt() {
            comparison = MtxQuery.gt(f1, f2);
            String expected = "(1 > 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testGte() {
            comparison = MtxQuery.gte(f1, f2);
            String expected = "(1 >= 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testLt() {
            comparison = MtxQuery.lt(f1, f2);
            String expected = "(1 < 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testLte() {
            comparison = MtxQuery.lte(f1, f2);
            String expected = "(1 <= 2)";
            assertEquals(expected, comparison.toString());
        }

        @Test
        public void testLike() {
            comparison = MtxQuery.like(f1, f2);
            String expected = "(1 LIKE 2)";
            assertEquals(expected, comparison.toString());
        }
    }

    @Nested
    class IsCompleteTests {
        private static final class SampleQuery extends MtxQuery {
            public SampleQuery(boolean isComplete) {
                super.complete = isComplete;
            }

            @Override
            public String toString() {
                return null;
            }

            public boolean isComplete() {
                return super.isComplete();
            }
        }

        private SampleQuery sampleQuery;

        @Test
        public void testIsComplete_notComplete() {
            sampleQuery = new SampleQuery(false);
            assertFalse(sampleQuery.isComplete());
        }

        @Test
        public void testIsComplete_isComplete() {
            sampleQuery = new SampleQuery(true);
            assertTrue(sampleQuery.isComplete());
        }
    }
}
