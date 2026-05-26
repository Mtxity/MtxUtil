package com.edavalos.mtx.util.math.collection;

import com.edavalos.mtx.util.db.var.MtxOptionalVar;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

public class MtxRandomSelectorTest {

    @Nested
    class PickRandomCollectionTests {

        @Test
        public void testPickRandom_null() {
            Collection<String> collection = null;
            assertTrue(MtxRandomSelector.pickRandom(collection).isEmpty());
        }

        @Test
        public void testPickRandom_empty() {
            assertTrue(MtxRandomSelector.pickRandom(List.of()).isEmpty());
        }

        @Test
        public void testPickRandom_isList() {
            List<String> list = List.of("x", "y", "z");
            String randomVal = MtxRandomSelector.pickRandom(list).getValue();
            assertTrue(list.contains(randomVal));
        }

        @Test
        public void testPickRandom_isNotList() {
            Collection<String> col = new HashSet<>(List.of("x", "y", "z"));
            String randomVal = MtxRandomSelector.pickRandom(col).getValue();
            assertTrue(col.contains(randomVal));
        }

        @Test
        public void testPickRandom_isNotList_outOfIndex() {
            Set<String> set = new LinkedHashSet<>(List.of("a", "b", "c"));

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(3)).thenReturn(999);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(set);

                assertTrue(result.isEmpty());
            }
        }
    }

    @Nested
    class PickRandomArrayTests {

        @Test
        public void testPickRandom_null() {
            String[] array = null;
            assertTrue(MtxRandomSelector.pickRandom(array).isEmpty());
        }

        @Test
        public void testPickRandom_empty() {
            assertTrue(MtxRandomSelector.pickRandom(new String[]{}).isEmpty());
        }

        @Test
        public void testPickRandom_isArray() {
            String[] array = List.of("x", "y", "z").toArray(new String[0]);
            String randomVal = MtxRandomSelector.pickRandom(array).getValue();
            assertTrue(Arrays.stream(array).toList().contains(randomVal));
        }

        @Test
        public void testPickRandom_outOfIndex() {
            String[] array = List.of("x", "y", "z").toArray(new String[0]);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(3)).thenReturn(999);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(array);

                assertTrue(result.isEmpty());
            }
        }
    }

    @Nested
    class PickRandom2ArgTests {

        @Test
        public void testPickRandom_nullArgs() {
            assertTrue(MtxRandomSelector.pickRandom(null, null).isEmpty());
        }

        @Test
        public void testPickRandom_validArgs() {
            String val1 = "x";
            String val2 = "y";

            for (int i = 0; i < 100; i++) {
                String randomVal = MtxRandomSelector.pickRandom(val1, val2).getValue();
                assertTrue(val1.equals(randomVal) || val2.equals(randomVal));
            }
        }
    }

    @Nested
    class PickRandom3ArgTests {

        private class TestItem {
            private final int hashCode;

            private TestItem(int hashCode) {
                this.hashCode = hashCode;
            }

            @Override
            public int hashCode() {
                return hashCode;
            }
        }

        @Test
        public void testPickRandom_nullArgs() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null).isEmpty());
        }

        @Test
        public void testPickRandom_firstArgNullSecondArgPresent() {
            TestItem item2 = new TestItem(400);
            TestItem item3 = new TestItem(400);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(1);

                TestItem result = MtxRandomSelector.pickRandom(null, item2, item3).getValue();

                assertTrue(result == item2);
            }
        }

        @Test
        public void testPickRandom_firstTwoArgsNullThirdArgPresent() {
            TestItem item3 = new TestItem(700);

            MtxOptionalVar<TestItem> result = MtxRandomSelector.pickRandom(null, null, item3);

            assertTrue(result.isEmpty());
        }

        @Test
        public void testPickRandom_returnsItem1ForFirstDigit1() {
            TestItem item1 = new TestItem(100);
            TestItem item2 = new TestItem(100);
            TestItem item3 = new TestItem(100);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item1);
            }
        }

        @Test
        public void testPickRandom_returnsItem1ForFirstDigit2() {
            TestItem item1 = new TestItem(200);
            TestItem item2 = new TestItem(200);
            TestItem item3 = new TestItem(200);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item1);
            }
        }

        @Test
        public void testPickRandom_returnsItem1ForFirstDigit3() {
            TestItem item1 = new TestItem(300);
            TestItem item2 = new TestItem(300);
            TestItem item3 = new TestItem(300);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item1);
            }
        }

        @Test
        public void testPickRandom_returnsItem2ForFirstDigit4() {
            TestItem item1 = new TestItem(400);
            TestItem item2 = new TestItem(400);
            TestItem item3 = new TestItem(400);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item2);
            }
        }

        @Test
        public void testPickRandom_returnsItem2ForFirstDigit5() {
            TestItem item1 = new TestItem(500);
            TestItem item2 = new TestItem(500);
            TestItem item3 = new TestItem(500);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item2);
            }
        }

        @Test
        public void testPickRandom_returnsItem2ForFirstDigit6() {
            TestItem item1 = new TestItem(600);
            TestItem item2 = new TestItem(600);
            TestItem item3 = new TestItem(600);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item2);
            }
        }

        @Test
        public void testPickRandom_returnsItem3() {
            TestItem item1 = new TestItem(700);
            TestItem item2 = new TestItem(700);
            TestItem item3 = new TestItem(700);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item3);
            }
        }

        @Test
        public void testPickRandom_negativeHashCodeReturnsItem1() {
            TestItem item1 = new TestItem(-100);
            TestItem item2 = new TestItem(-100);
            TestItem item3 = new TestItem(-100);

            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(0);

                TestItem result = MtxRandomSelector.pickRandom(item1, item2, item3).getValue();

                assertTrue(result == item1);
            }
        }

        @Test
        public void testPickRandom_returnsEmptyWhenDerivedValueIsEmpty() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2)).thenReturn(1);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", null, null);

                assertTrue(result.isEmpty());
            }
        }
    }

    @Nested
    class PickRandom4ArgTests {

        @Test
        public void testPickRandom_nullArgs() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null).isEmpty());
        }

        @Test
        public void testPickRandom_validArgs() {
            String val1 = "x";
            String val2 = "y";
            String val3 = "a";
            String val4 = "b";

            for (int i = 0; i < 100; i++) {
                String randomVal = MtxRandomSelector.pickRandom(val1, val2, val3, val4).getValue();
                assertTrue(
                        val1.equals(randomVal)
                     || val2.equals(randomVal)
                     || val3.equals(randomVal)
                     || val4.equals(randomVal)
                );
            }
        }
    }

    @Nested
    class PickRandom5ArgTests {

        @Test
        public void testPickRandom_nullArgs() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null, null).isEmpty());
        }

        @Test
        public void testPickRandom_returnsItem1() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(0);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue("a".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem2() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(1);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue("b".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem3() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(2);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue("c".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem4() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(3);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue("d".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem5() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(4);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue("e".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_validArgs() {
            String[] vals = new String[]{"a", "b", "c", "d", "e"};

            for (int i = 0; i < 100; i++) {
                String randomVal = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e").getValue();
                assertTrue(
                        vals[0].equals(randomVal)
                                || vals[1].equals(randomVal)
                                || vals[2].equals(randomVal)
                                || vals[3].equals(randomVal)
                                || vals[4].equals(randomVal)
                );
            }
        }

        @Test
        public void testPickRandom_outOfIndex() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(999);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e");

                assertTrue(result.isEmpty());
            }
        }

        @Test
        public void testPickRandom_secondArgOnlyPresent() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(1);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, "b", null, null, null);

                assertTrue("b".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_thirdArgOnlyPresent() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(2);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, "c", null, null);

                assertTrue("c".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_fourthArgOnlyPresent() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(3);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, null, "d", null);

                assertTrue("d".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_fifthArgOnlyPresent() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(5)).thenReturn(4);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, null, null, "e");

                assertTrue("e".equals(result.getValue()));
            }
        }
    }

    @Nested
    class PickRandom6ArgTests {

        @Test
        public void testPickRandom_null() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null, null, null).isEmpty());
        }

        @Test
        public void testPickRandom_secondArgPresentForNullCheckCoverage() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(1);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, "b", null, null, null, null);

                assertTrue("b".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_thirdArgPresentForNullCheckCoverage() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(2);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, "c", null, null, null);

                assertTrue("c".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_fourthArgPresentForNullCheckCoverage() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(3);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, null, "d", null, null);

                assertTrue("d".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_fifthArgPresentForNullCheckCoverage() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(4);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, null, null, "e", null);

                assertTrue("e".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_sixthArgPresentForNullCheckCoverage() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(5);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom(null, null, null, null, null, "f");

                assertTrue("f".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem1() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(0);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("a".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem2() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(1);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("b".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem3() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(2);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("c".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem4() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(3);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("d".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem5() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(4);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("e".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem6() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(5);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue("f".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_validArgs() {
            String[] vals = new String[]{"a", "b", "c", "d", "e", "f"};

            for (int i = 0; i < 100; i++) {
                String randomVal = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f").getValue();
                assertTrue(
                        vals[0].equals(randomVal)
                     || vals[1].equals(randomVal)
                     || vals[2].equals(randomVal)
                     || vals[3].equals(randomVal)
                     || vals[4].equals(randomVal)
                     || vals[5].equals(randomVal)
                );
            }
        }

        @Test
        public void testPickRandom_outOfIndex() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(6)).thenReturn(999);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f");

                assertTrue(result.isEmpty());
            }
        }
    }

    @Nested
    class PickRandom8ArgTests {

        @Test
        public void testPickRandom_nullArgs() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null, null, null, null, null).isEmpty());
        }

        @Test
        public void testPickRandom_returnsItem2() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2))
                        .thenReturn(1, 0, 0, 0, 0, 0, 0);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f", "g", "h");

                assertTrue("b".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_partialNullArgs() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2))
                        .thenReturn(0, 0, 0, 0, 0, 0, 0);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", null, "c", null, "e", null, "g", null);

                assertTrue("a".equals(result.getValue()));
            }
        }

        @Test
        public void testPickRandom_returnsItem1() {
            try (MockedStatic<MtxRandomSelector> mockedStatic = mockStatic(MtxRandomSelector.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(() -> MtxRandomSelector.nextRandomInt(2))
                        .thenReturn(0, 0, 0, 0, 0, 0, 0);

                MtxOptionalVar<String> result = MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", "f", "g", "h");

                assertTrue("a".equals(result.getValue()));
            }
        }
    }
}
