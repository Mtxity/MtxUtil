package com.edavalos.mtx.util.db.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxThreeColumnTableTest {
    private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> SAMPLE_NEW_ROW_1 =
            new MtxThreeColumnTable.MtxTriple<>(45, "Yes", "Sometimes");
    private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> SAMPLE_NEW_ROW_2 =
            new MtxThreeColumnTable.MtxTriple<>(46, "No", "Never");
    private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> SAMPLE_NEW_ROW_3 =
            new MtxThreeColumnTable.MtxTriple<>(47, "Maybe", "Always");
    private static final List<MtxThreeColumnTable.MtxTriple<Integer, String, String>> NEW_ROWS_LIST =
            List.of(SAMPLE_NEW_ROW_1, SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3);

    private MtxThreeColumnTable<Integer, String, String> mtxTable;

    @BeforeEach
    public void setUp() {
        mtxTable = new MtxThreeColumnTable<>();

        mtxTable.addRow(SAMPLE_NEW_ROW_1);
        mtxTable.addRow(SAMPLE_NEW_ROW_2);
        mtxTable.addRow(SAMPLE_NEW_ROW_3);
    }

    @Nested
    public class AddRowTests {
        private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> SAMPLE_NEW_ROW_4 =
                new MtxThreeColumnTable.MtxTriple<>(48, "Yes", "Rarely");

        @Test
        public void testAddRow() {
            List<MtxThreeColumnTable.MtxTriple<Integer, String, String>> rowList = new ArrayList<>(NEW_ROWS_LIST);
            assertEquals(rowList, mtxTable.getAllRows());

            mtxTable.addRow(SAMPLE_NEW_ROW_4);
            rowList.add(SAMPLE_NEW_ROW_4);

            assertEquals(rowList, mtxTable.getAllRows());
        }

        @Test
        public void testAddRow_duplicatePrimaryKey() {

            ExistingPrimaryKeyException epke = assertThrows(ExistingPrimaryKeyException.class, () ->
                    mtxTable.addRow(SAMPLE_NEW_ROW_2.first(), SAMPLE_NEW_ROW_2.second(), SAMPLE_NEW_ROW_2.third())
            );

            String expectedErrorMsg = String.format(
                    ExistingPrimaryKeyException.ERROR_PRIMARY_KEY_ALREADY_EXISTS,
                    SAMPLE_NEW_ROW_2.first()
            );

            assertEquals(expectedErrorMsg, epke.getMessage());
        }
    }

    @Nested
    public class GetRowTests {

        @Test
        public void testGetRowFromPrimaryKey() {
            assertEquals(SAMPLE_NEW_ROW_1, mtxTable.getRowFromPrimaryKey(45));
            assertEquals(SAMPLE_NEW_ROW_2, mtxTable.getRowFromPrimaryKey(46));
            assertEquals(SAMPLE_NEW_ROW_3, mtxTable.getRowFromPrimaryKey(47));
        }

        @Test
        public void testGetRowsFromMatchingSecondColumn() {
            assertEquals(List.of(SAMPLE_NEW_ROW_1), mtxTable.getRowsFromMatchingSecondColumn("Yes"));
            assertEquals(List.of(SAMPLE_NEW_ROW_2), mtxTable.getRowsFromMatchingSecondColumn("No"));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getRowsFromMatchingSecondColumn("Maybe"));
        }

        @Test
        public void testGetRowsFromMatchingThirdColumn() {
            assertEquals(List.of(SAMPLE_NEW_ROW_1), mtxTable.getRowsFromMatchingThirdColumn("Sometimes"));
            assertEquals(List.of(SAMPLE_NEW_ROW_2), mtxTable.getRowsFromMatchingThirdColumn("Never"));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getRowsFromMatchingThirdColumn("Always"));
        }

        @Test
        public void testGetAllRows() {
            assertEquals(NEW_ROWS_LIST, mtxTable.getAllRows());
        }
    }
}
