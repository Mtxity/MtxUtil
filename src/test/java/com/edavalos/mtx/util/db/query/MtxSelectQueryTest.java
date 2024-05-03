package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.edavalos.mtx.util.db.query.MtxQuery.eq;
import static com.edavalos.mtx.util.db.query.MtxQuery.gte;
import static com.edavalos.mtx.util.db.query.MtxQuery.like;
import static com.edavalos.mtx.util.db.query.MtxQuery.lt;
import static com.edavalos.mtx.util.db.query.MtxQuery.lte;
import static com.edavalos.mtx.util.db.query.MtxQuery.neq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class MtxSelectQueryTest {
    private MtxSelectQuery mtxSelectQuery;

    @BeforeEach
    public void setUp() {
        mtxSelectQuery = new MtxSelectQuery();
    }

    @Test
    public void testQuery_plain() {
        String expected = "SELECT * FROM table;";
        String actual = mtxSelectQuery.from("table").toString();
        assertEquals(expected, actual);
    }

    @Nested
    class JoinTests {

        @Test
        public void testQuery_leftJoin() {
            String expected = "SELECT * FROM table LEFT JOIN otherTable ON (t = u);";
            String actual = mtxSelectQuery
                            .from("table")
                            .leftJoin("otherTable", eq("t", "u"))
                            .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_rightJoin() {
            String expected = "SELECT * FROM table RIGHT JOIN otherTable ON (t = u);";
            String actual = mtxSelectQuery
                            .from("table")
                            .rightJoin("otherTable", eq("t", "u"))
                            .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_innerJoin() {
            String expected = "SELECT * FROM table INNER JOIN otherTable ON (t = u);";
            String actual = mtxSelectQuery
                            .from("table")
                            .innerJoin("otherTable", eq("t", "u"))
                            .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_fullJoin() {
            String expected = "SELECT * FROM table FULL JOIN otherTable ON (t = u);";
            String actual = mtxSelectQuery
                            .from("table")
                            .fullJoin("otherTable", eq("t", "u"))
                            .toString();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class WhereTests {

        @Test
        public void testQuery_where_one() {
            String expected = "SELECT * FROM table WHERE ((t = u));";
            String actual = mtxSelectQuery
                    .from("table")
                    .where(eq("t", "u"))
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_where_multiple() {
            String expected = "SELECT * FROM table WHERE ((t = u) AND (v <> w) AND (x LIKE y));";
            String actual = mtxSelectQuery
                    .from("table")
                    .where(eq("t", "u"))
                    .where(neq("v", "w"))
                    .where(like("x", "y"))
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_where_withOr_atMiddle() {
            String expected = "SELECT * FROM table WHERE ((t = u) AND (v <> w)) OR ((x LIKE y));";
            String actual = mtxSelectQuery
                    .from("table")
                    .where(eq("t", "u"))
                    .where(neq("v", "w"))
                    .or()
                    .where(like("x", "y"))
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_where_withOr_atStart() {
            String expected = "SELECT * FROM table WHERE ((t = u) AND (v <> w) AND (x LIKE y));";
            String actual = mtxSelectQuery
                    .from("table")
                    .or() // OR statement should be ignored due to being first
                    .where(eq("t", "u"))
                    .where(neq("v", "w"))
                    .where(like("x", "y"))
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testQuery_where_withOr_atEnd() {
            String expected = "SELECT * FROM table WHERE ((t = u) AND (v <> w) AND (x LIKE y));";
            String actual = mtxSelectQuery
                    .from("table")
                    .where(eq("t", "u"))
                    .where(neq("v", "w"))
                    .where(like("x", "y"))
                    .or() // OR statement should be ignored due to being last
                    .toString();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class TestOrderBy {

        @Test
        public void testOrderBy_onlyAsc() {
            String expected = "SELECT * FROM table ORDER BY time ASC;";
            String actual = mtxSelectQuery
                    .from("table")
                    .orderBy("time", MtxSelectQuery.Order.ASC)
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testOrderBy_onlyDesc() {
            String expected = "SELECT * FROM table ORDER BY time DESC;";
            String actual = mtxSelectQuery
                    .from("table")
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testOrderBy_bothAscAndDesc() {
            String expected = "SELECT * FROM table ORDER BY date ASC, time DESC;";
            String actual = mtxSelectQuery
                    .from("table")
                    .orderBy("date", MtxSelectQuery.Order.ASC)
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testOrderBy_bothAscAndDesc_multipleValues() {
            String expected = "SELECT * FROM table ORDER BY year, date ASC, time, place DESC;";
            String actual = mtxSelectQuery
                    .from("table")
                    .orderBy("year", MtxSelectQuery.Order.ASC)
                    .orderBy("date", MtxSelectQuery.Order.ASC)
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .orderBy("place", MtxSelectQuery.Order.DESC)
                    .toString();
            assertEquals(expected, actual);
        }

        @Test
        public void testOrderBy_bothAscAndDesc_multipleValues_randomOrder() {
            String expected = "SELECT * FROM table ORDER BY year, date ASC, time, place DESC;";
            String actual = mtxSelectQuery
                    .from("table")
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .orderBy("year", MtxSelectQuery.Order.ASC)
                    .orderBy("place", MtxSelectQuery.Order.DESC)
                    .orderBy("date", MtxSelectQuery.Order.ASC)
                    .toString();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class TestToString {

        @Test
        public void testToString_completeQuery() {
            String expected = "SELECT * " +
                    "FROM table " +
                    "INNER JOIN other2 ON (c < d) " +
                    "LEFT JOIN other1 ON (a >= b) " +
                    "WHERE ((w <= x)) OR ((y LIKE z)) " +
                    "ORDER BY year ASC, time DESC;";
            MtxSelectQuery query = mtxSelectQuery
                    .from("table")
                    .leftJoin("other1", gte("a", "b"))
                    .innerJoin("other2", lt("c", "d"))
                    .where(lte("w", "x"))
                    .or()
                    .where(like("y", "z"))
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .orderBy("year", MtxSelectQuery.Order.ASC);
            assertEquals(expected, query.toString());
        }

        @Test
        public void testToString_incompleteQuery_standalone() {
            String expectedErrorMsg = "MtxSelectQuery is missing elements required to convert it to a String!";
            MtxSelectQuery query = new MtxSelectQuery("randomElement");
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> query.toString()
            );
            String actualErrorMsg = exception.getMessage();
            assertEquals(expectedErrorMsg, actualErrorMsg);
        }

        @Test
        public void testToString_incompleteQuery_withExtraClauses() {
            String expectedErrorMsg = "MtxSelectQuery is missing elements required to convert it to a String!";
            MtxSelectQuery query = mtxSelectQuery
                    .leftJoin("other1", gte("a", "b"))
                    .innerJoin("other2", lt("c", "d"))
                    .where(lte("w", "x"))
                    .or()
                    .where(like("y", "z"))
                    .orderBy("time", MtxSelectQuery.Order.DESC)
                    .orderBy("year", MtxSelectQuery.Order.ASC);
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> query.toString()
            );
            String actualErrorMsg = exception.getMessage();
            assertEquals(expectedErrorMsg, actualErrorMsg);
        }
    }
}
