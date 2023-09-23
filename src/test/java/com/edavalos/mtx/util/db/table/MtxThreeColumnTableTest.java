package com.edavalos.mtx.util.db.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Nested
    public class DeleteRowTests {

        @Test
        public void testDeleteRow_primaryKeyArg() {
            assertTrue(mtxTable.deleteRow(45));
            assertEquals(List.of(SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRow(46));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRow(47));
            assertEquals(List.of(), mtxTable.getAllRows());

            for (int i = 40; i < 50; i++) {
                assertFalse(mtxTable.deleteRow(i));
            }
        }

        @Test
        public void testDeleteRow_rowArg() {
            assertTrue(mtxTable.deleteRow(SAMPLE_NEW_ROW_1));
            assertEquals(List.of(SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRow(SAMPLE_NEW_ROW_2));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRow(SAMPLE_NEW_ROW_3));
            assertEquals(List.of(), mtxTable.getAllRows());

            assertFalse(mtxTable.deleteRow(SAMPLE_NEW_ROW_1));
            assertFalse(mtxTable.deleteRow(SAMPLE_NEW_ROW_2));
            assertFalse(mtxTable.deleteRow(SAMPLE_NEW_ROW_3));
        }

        @Test
        public void testDeleteRowsMatchingSecondColumn() {
            assertTrue(mtxTable.deleteRowsMatchingSecondColumn(SAMPLE_NEW_ROW_1.second()));
            assertEquals(List.of(SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRowsMatchingSecondColumn(SAMPLE_NEW_ROW_2.second()));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRowsMatchingSecondColumn(SAMPLE_NEW_ROW_3.second()));
            assertEquals(List.of(), mtxTable.getAllRows());
        }

        @Test
        public void testDeleteRowsMatchingThirdColumn() {
            assertTrue(mtxTable.deleteRowsMatchingThirdColumn(SAMPLE_NEW_ROW_1.third()));
            assertEquals(List.of(SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRowsMatchingThirdColumn(SAMPLE_NEW_ROW_2.third()));
            assertEquals(List.of(SAMPLE_NEW_ROW_3), mtxTable.getAllRows());
            assertTrue(mtxTable.deleteRowsMatchingThirdColumn(SAMPLE_NEW_ROW_3.third()));
            assertEquals(List.of(), mtxTable.getAllRows());
        }
    }

    @Nested
    public class EditRowTests {
        private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> ALT_ROW_1 =
                new MtxThreeColumnTable.MtxTriple<>(45, "Aye", "Bouts");
        private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> ALT_ROW_2 =
                new MtxThreeColumnTable.MtxTriple<>(46, "Nay", "Yaint");
        private static final MtxThreeColumnTable.MtxTriple<Integer, String, String> ALT_ROW_3 =
                new MtxThreeColumnTable.MtxTriple<>(47, "Oi", "Sender");
        private static final List<MtxThreeColumnTable.MtxTriple<Integer, String, String>> NEW_ROWS_LIST =
                List.of(SAMPLE_NEW_ROW_1, SAMPLE_NEW_ROW_2, SAMPLE_NEW_ROW_3);

        @Test
        public void editRowValues_primaryKeyArg_changedPrimaryKey() {
            ImmutableColumnException ice = assertThrows(ImmutableColumnException.class, () ->
                    mtxTable.editRowValues(66, ALT_ROW_1)
            );

            String expectedErrorMsg = String.format(
                    ImmutableColumnException.ERROR_CANNOT_EDIT_PRIMARY_KEY,
                    66,
                    ALT_ROW_1.first()
            );

            assertEquals(expectedErrorMsg, ice.getMessage());
        }

        @Test
        public void editRowValues_primaryKeyArg_invalidPrimaryKey() {
            for (int i = 20; i < 30; i++) {
                assertFalse(mtxTable.editRowValues(i, new MtxThreeColumnTable.MtxTriple<>(
                        i, null, null
                )));
            }
        }

        @Test
        public void editRowValues_primaryKeyArg() {
            assertTrue(mtxTable.editRowValues(45, ALT_ROW_1));
            assertEquals(List.of(
                    ALT_ROW_1,
                    SAMPLE_NEW_ROW_2,
                    SAMPLE_NEW_ROW_3
            ), mtxTable.getAllRows());

            assertTrue(mtxTable.editRowValues(46, ALT_ROW_2));
            assertEquals(List.of(
                    ALT_ROW_1,
                    ALT_ROW_2,
                    SAMPLE_NEW_ROW_3
            ), mtxTable.getAllRows());

            assertTrue(mtxTable.editRowValues(47, ALT_ROW_3));
            assertEquals(List.of(
                    ALT_ROW_1,
                    ALT_ROW_2,
                    ALT_ROW_3
            ), mtxTable.getAllRows());
        }

        @Test
        public void editRowValues_rowArg_changedPrimaryKey() {
            ImmutableColumnException ice = assertThrows(ImmutableColumnException.class, () ->
                    mtxTable.editRowValues(ALT_ROW_2, ALT_ROW_1)
            );

            String expectedErrorMsg = String.format(
                    ImmutableColumnException.ERROR_CANNOT_EDIT_PRIMARY_KEY,
                    ALT_ROW_2.first(),
                    ALT_ROW_1.first()
            );

            assertEquals(expectedErrorMsg, ice.getMessage());
        }

        @Test
        public void editRowValues_rowArg_invalidPrimaryKey() {
            for (int i = 20; i < 30; i++) {
                assertFalse(mtxTable.editRowValues(
                        new MtxThreeColumnTable.MtxTriple<>(i, null, null),
                        new MtxThreeColumnTable.MtxTriple<>(i, null, null)
                ));
            }
        }

        @Test
        public void editRowValues_rowArg() {
            assertTrue(mtxTable.editRowValues(SAMPLE_NEW_ROW_1, ALT_ROW_1));
            assertEquals(List.of(
                    ALT_ROW_1,
                    SAMPLE_NEW_ROW_2,
                    SAMPLE_NEW_ROW_3
            ), mtxTable.getAllRows());

            assertTrue(mtxTable.editRowValues(SAMPLE_NEW_ROW_2, ALT_ROW_2));
            assertEquals(List.of(
                    ALT_ROW_1,
                    ALT_ROW_2,
                    SAMPLE_NEW_ROW_3
            ), mtxTable.getAllRows());

            assertTrue(mtxTable.editRowValues(SAMPLE_NEW_ROW_3, ALT_ROW_3));
            assertEquals(List.of(
                    ALT_ROW_1,
                    ALT_ROW_2,
                    ALT_ROW_3
            ), mtxTable.getAllRows());
        }
    }
}
