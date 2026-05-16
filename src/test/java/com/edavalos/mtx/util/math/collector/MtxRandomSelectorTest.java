package com.edavalos.mtx.util.math.collector;

import com.edavalos.mtx.util.math.collection.MtxRandomSelector;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxRandomSelectorTest {

    @Nested
    class PickRandomTests {

        @Test
        public void testPickRandom_null() {
            assertTrue(MtxRandomSelector.pickRandom(null).isEmpty());
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
}
