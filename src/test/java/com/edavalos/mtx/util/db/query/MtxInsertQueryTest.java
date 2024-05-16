package com.edavalos.mtx.util.db.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxInsertQueryTest {
    private static final String SAMPLE_TABLE = "Customers";
    private static final String[] SAMPLE_COLUMNS = {"CustomerName", "ContactName", "Address", "City", "PostalCode", "Country"};

    private MtxInsertQuery mtxInsertQuery;

    @BeforeEach
    public void setUp() {
        mtxInsertQuery = new MtxInsertQuery(SAMPLE_TABLE, SAMPLE_COLUMNS.length);
    }

    @Test
    public void testConstructor_numberOfColumns() {
        mtxInsertQuery = new MtxInsertQuery(SAMPLE_TABLE, 0);
        mtxInsertQuery.complete = true;

        String expected = "INSERT INTO Customers VALUES ;";
        String actual = mtxInsertQuery.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructor_actualColumns() {
        mtxInsertQuery = new MtxInsertQuery(SAMPLE_TABLE, SAMPLE_COLUMNS);
        mtxInsertQuery.complete = true;

        String expected = "INSERT INTO Customers " +
                "(CustomerName, ContactName, Address, City, PostalCode, Country) " +
                "VALUES ;";
        String actual = mtxInsertQuery.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testValues_wrongNumberOfColumns() {
        assertDoesNotThrow(() -> mtxInsertQuery.values("1", "2", "3", "4", "5", "6"));

        String expected = "MtxInsertQuery expected 6 values, got: 3";
        String actual = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> mtxInsertQuery.values("1", "2", "3")
        ).getMessage();
        assertEquals(expected, actual);
    }

    @Nested
    class TestToString {
        private static final String[] SAMPLE_ROW_1 = {"Cardinal", "Tom B. Erichsen", "Skagen 21", "Stavanger", "4006", "Norway"};
        private static final String[] SAMPLE_ROW_2 = {"Greasy Burger", "Per Olsen", "Gateveien 15", "Sandnes", "4306", "Norway"};
        private static final String[] SAMPLE_ROW_3 = {"Tasty Tee", "Finn Egan", "Streetroad 19B", "Liverpool", "L1 0AA", "UK"};

        @Test
        public void testToString_columnsInferred_oneRow() {
            String expected = "INSERT INTO Customers " +
                    "VALUES " +
                    "('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');";
            MtxInsertQuery query = mtxInsertQuery
                    .values(SAMPLE_ROW_1);
            assertEquals(expected, query.toString());
        }

        @Test
        public void testToString_columnsInferred_multipleRows() {
            String expected = "INSERT INTO Customers " +
                    "VALUES " +
                    "('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway'), " +
                    "('Greasy Burger', 'Per Olsen', 'Gateveien 15', 'Sandnes', '4306', 'Norway'), " +
                    "('Tasty Tee', 'Finn Egan', 'Streetroad 19B', 'Liverpool', 'L1 0AA', 'UK');";
            MtxInsertQuery query = mtxInsertQuery
                    .values(SAMPLE_ROW_1)
                    .values(SAMPLE_ROW_2)
                    .values(SAMPLE_ROW_3);
            assertEquals(expected, query.toString());
        }

        @Test
        public void testToString_columnsDeclared_oneRow() {
            String expected = "INSERT INTO Customers " +
                    "(CustomerName, ContactName, Address, City, PostalCode, Country) " +
                    "VALUES " +
                    "('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');";
            MtxInsertQuery query = new MtxInsertQuery(SAMPLE_TABLE, SAMPLE_COLUMNS)
                    .values(SAMPLE_ROW_1);
            assertEquals(expected, query.toString());
        }

        @Test
        public void testToString_columnsDeclared_multipleRows() {
            String expected = "INSERT INTO Customers " +
                    "(CustomerName, ContactName, Address, City, PostalCode, Country) " +
                    "VALUES " +
                    "('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway'), " +
                    "('Greasy Burger', 'Per Olsen', 'Gateveien 15', 'Sandnes', '4306', 'Norway'), " +
                    "('Tasty Tee', 'Finn Egan', 'Streetroad 19B', 'Liverpool', 'L1 0AA', 'UK');";
            MtxInsertQuery query = new MtxInsertQuery(SAMPLE_TABLE, SAMPLE_COLUMNS)
                    .values(SAMPLE_ROW_1)
                    .values(SAMPLE_ROW_2)
                    .values(SAMPLE_ROW_3);
            assertEquals(expected, query.toString());
        }

        @Test
        public void testToString_incompleteQuery() {
            String expectedErrorMsg = "MtxInsertQuery is missing elements required to convert it to a String!";
            MtxInsertQuery query = new MtxInsertQuery(SAMPLE_TABLE, SAMPLE_COLUMNS);
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> query.toString()
            );
            String actualErrorMsg = exception.getMessage();
            assertEquals(expectedErrorMsg, actualErrorMsg);
        }
    }
}
