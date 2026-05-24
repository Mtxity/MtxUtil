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
            assertTrue(MtxRandomSelector.pickRandom(new String[] {}).isEmpty());
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
    class PickRandom6ArgTests {

        @Test
        public void testPickRandom_null() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null, null, null).isEmpty());
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
}
