package com.edavalos.mtx.util.math.collector;

import com.edavalos.mtx.util.math.collection.MtxRandomSelector;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @TODO: Fix coverage map in Intellij to verify coverage is complete
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
            // @TODO: Mockstatic ThreadLocalRandom to be out of index
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
            // @TODO: Mockstatic ThreadLocalRandom to be out of index
        }
    }

    @Nested
    class PickRandom2ArgTests {

        @Test
        public void testPickRandom_null_arg1() {
            assertTrue(MtxRandomSelector.pickRandom(null, "y").isEmpty());
        }

        @Test
        public void testPickRandom_null_arg2() {
            assertTrue(MtxRandomSelector.pickRandom("x", null).isEmpty());
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
    class PickRandom6ArgTests {

        @Test
        public void testPickRandom_null() {
            assertTrue(MtxRandomSelector.pickRandom(null, null, null, null, null, null).isEmpty());
            assertTrue(MtxRandomSelector.pickRandom("a", null, null, null, null, null).isEmpty());
            assertTrue(MtxRandomSelector.pickRandom("a", "b", null, null, null, null).isEmpty());
            assertTrue(MtxRandomSelector.pickRandom("a", "b", "c", null, null, null).isEmpty());
            assertTrue(MtxRandomSelector.pickRandom("a", "b", "c", "d", null, null).isEmpty());
            assertTrue(MtxRandomSelector.pickRandom("a", "b", "c", "d", "e", null).isEmpty());
        }

        @Test
        public void testPickRandom_validArgs() {
            String[] vals = new String[]{"a", "b", "c", "d", "e", "f"};

            for (int i = 0; i < 100; i++) {
                String randomVal = MtxRandomSelector.pickRandom(vals).getValue();
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
            // @TODO: Mockstatic ThreadLocalRandom to be out of index
        }
    }
}
