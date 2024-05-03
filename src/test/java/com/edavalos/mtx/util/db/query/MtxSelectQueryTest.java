package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.edavalos.mtx.util.db.query.MtxQuery.eq;
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
}
