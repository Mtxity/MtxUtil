package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.edavalos.mtx.util.db.query.MtxQuery.eq;
import static com.edavalos.mtx.util.db.query.MtxQuery.like;
import static com.edavalos.mtx.util.db.query.MtxQuery.neq;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
