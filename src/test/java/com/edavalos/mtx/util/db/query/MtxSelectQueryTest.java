package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
