package com.edavalos.mtx.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MtxHashBiMapTest {
    private MtxHashBiMap<Integer, String> mtxHashBiMap;

    @BeforeEach
    public void setUp() {
        mtxHashBiMap = new MtxHashBiMap<>();
    }

    @Nested
    class TestForward {

        @Test
        public void testSize() {
            assertEquals(0, mtxHashBiMap.size());

            fillWithValues();

            assertEquals(3, mtxHashBiMap.size());
        }

        @Test
        public void testIsEmpty() {
            assertTrue(mtxHashBiMap.isEmpty());

            fillWithValues();

            assertFalse(mtxHashBiMap.isEmpty());
        }

        @Test
        public void testContainsKey() {
            assertFalse(mtxHashBiMap.containsKey(null));
            assertFalse(mtxHashBiMap.containsKey(2));

            fillWithValues();

            assertTrue(mtxHashBiMap.containsKey(2));
        }

        @Test
        public void testContainsValue() {
            assertFalse(mtxHashBiMap.containsValue(null));
            assertFalse(mtxHashBiMap.containsValue("Two"));

            fillWithValues();

            assertTrue(mtxHashBiMap.containsValue("Two"));
        }

        @Test
        public void testGet() {
            assertNull(mtxHashBiMap.get(1));

            fillWithValues();

            assertEquals("One", mtxHashBiMap.get(1));
        }

        @Test
        public void testGetOrDefault() {
            fillWithValues();

            assertEquals("Two", mtxHashBiMap.getOrDefault(2, "Four"));
            assertEquals("Four", mtxHashBiMap.getOrDefault(4, "Four"));
        }

        @Nested
        class TestPut {

            @Test
            public void testPut() {
                assertNull(mtxHashBiMap.get(3));
                assertNull(mtxHashBiMap.get(4));

                fillWithValues();

                assertEquals("Three", mtxHashBiMap.put(3, "III"));
                assertNull(mtxHashBiMap.put(4, "IV"));

                assertEquals("III", mtxHashBiMap.get(3));
                assertEquals("IV", mtxHashBiMap.get(4));
            }

            @Test
            public void testPut_nullKeyOrValue() {
                assertThrows(NullPointerException.class, () -> mtxHashBiMap.put(1, null));
                assertThrows(NullPointerException.class, () -> mtxHashBiMap.put(null, "One"));
            }

            @Test
            public void testPut_duplicateValue() {
                fillWithValues();

                assertThrows(IllegalArgumentException.class, () -> mtxHashBiMap.put(4, "Three"));
            }
        }

        @Test
        public void testRemove() {
            assertNull(mtxHashBiMap.remove(3));

            fillWithValues();

            assertEquals("Three", mtxHashBiMap.remove(3));
            assertEquals(2, mtxHashBiMap.size());
            assertNull(mtxHashBiMap.remove(3));
        }

        @Nested
        class TestPutAll {
            private static final Map<Integer, String> TEST_CASE_VALID = new HashMap<>() {{
                put(1, "One");
                put(2, "Two");
                put(3, "Three");
            }};
            private static final Map<Integer, String> TEST_CASE_INVALID = new HashMap<>() {{
                put(1, "One");
                put(2, "Two");
                put(3, "Three");
                put(4, "Three");
            }};

            @Test
            public void testPutAll() {
                assertTrue(mtxHashBiMap.isEmpty());

                mtxHashBiMap.putAll(TEST_CASE_VALID);

                assertEquals(mtxHashBiMap.keySet(), TEST_CASE_VALID.keySet());
                assertArrayEquals(mtxHashBiMap.values().toArray(), TEST_CASE_VALID.values().toArray());
            }

            @Test
            public void testPutAll_duplicateValuesInArg() {
                assertTrue(mtxHashBiMap.isEmpty());

                assertThrows(IllegalArgumentException.class, () -> mtxHashBiMap.putAll(TEST_CASE_INVALID));
            }

            @Test
            public void testPutAll_duplicateValuesInMap() {
                assertTrue(mtxHashBiMap.isEmpty());

                mtxHashBiMap.put(4, "Three");

                assertThrows(IllegalArgumentException.class, () -> mtxHashBiMap.putAll(TEST_CASE_VALID));
            }
        }

        @Test
        public void testClear() {
            assertTrue(mtxHashBiMap.isEmpty());
            fillWithValues();
            assertFalse(mtxHashBiMap.isEmpty());

            mtxHashBiMap.clear();

            assertTrue(mtxHashBiMap.isEmpty());
        }

        @Test
        public void testKeySet() {
            Set<Integer> keySet = new HashSet<>() {{
                add(1);
                add(2);
                add(3);
            }};

            assertEquals(Set.of(), mtxHashBiMap.keySet());

            fillWithValues();

            assertEquals(keySet, mtxHashBiMap.keySet());
        }

        @Test
        public void testValues() {
            Set<String> values = new HashSet<>() {{
                add("One");
                add("Two");
                add("Three");
            }};

            assertArrayEquals(Set.of().toArray(), mtxHashBiMap.values().toArray());

            fillWithValues();

            assertArrayEquals(values.toArray(), mtxHashBiMap.values().toArray());
        }

        @Test
        public void testEntrySet() {
            Set<Map.Entry<Integer, String >> entries = new HashSet<>() {{
                add(Map.entry(1, "One"));
                add(Map.entry(2, "Two"));
                add(Map.entry(3, "Three"));
            }};

            assertEquals(Set.of(), mtxHashBiMap.entrySet());

            fillWithValues();

            assertEquals(entries, mtxHashBiMap.entrySet());
        }
    }

    @Nested
    class TestBackward {

        @Test
        public void testSize() {
            assertEquals(0, mtxHashBiMap.inverse().size());

            fillWithValues();

            assertEquals(3, mtxHashBiMap.inverse().size());
        }

        @Test
        public void testIsEmpty() {
            assertTrue(mtxHashBiMap.inverse().isEmpty());

            fillWithValues();

            assertFalse(mtxHashBiMap.inverse().isEmpty());
        }

        @Test
        public void testContainsKey() {
            assertFalse(mtxHashBiMap.inverse().containsKey(null));
            assertFalse(mtxHashBiMap.inverse().containsKey("Two"));

            fillWithValues();

            assertTrue(mtxHashBiMap.inverse().containsKey("Two"));
        }

        @Test
        public void testContainsValue() {
            assertFalse(mtxHashBiMap.inverse().containsValue(null));
            assertFalse(mtxHashBiMap.inverse().containsValue(2));

            fillWithValues();

            assertTrue(mtxHashBiMap.inverse().containsValue(2));
        }

        @Test
        public void testGet() {
            assertNull(mtxHashBiMap.inverse().get("One"));

            fillWithValues();

            assertEquals(1, mtxHashBiMap.inverse().get("One"));
        }

        @Test
        public void testGetOrDefault() {
            fillWithValues();

            assertEquals(2, mtxHashBiMap.inverse().getOrDefault("Two", 4));
            assertEquals(4, mtxHashBiMap.inverse().getOrDefault("Four", 4));
        }

        @Test
        public void testInverse() {
            assertTrue(mtxHashBiMap.isEmpty());
            assertTrue(mtxHashBiMap.inverse().isEmpty());

            fillWithValues();

            assertArrayEquals(mtxHashBiMap.keySet().toArray(), mtxHashBiMap.inverse().values().toArray());
            assertArrayEquals(mtxHashBiMap.values().toArray(), mtxHashBiMap.inverse().keySet().toArray());

            assertThrows(UnsupportedOperationException.class, () -> mtxHashBiMap.inverse().inverse());
        }

        @Test
        public void testPut() {
            assertThrows(UnsupportedOperationException.class, () -> mtxHashBiMap.inverse().put("Four", 4));
        }

        @Test
        public void testRemove() {
            fillWithValues();
            assertThrows(UnsupportedOperationException.class, () -> mtxHashBiMap.inverse().remove("Two"));
        }

        @Test
        public void testPutAll() {
            Map<String, Integer> TEST_CASE_SAMPLE = new HashMap<>() {{
                put("One", 1);
            }};

            assertTrue(mtxHashBiMap.isEmpty());

            assertThrows(UnsupportedOperationException.class, () -> mtxHashBiMap.inverse().putAll(TEST_CASE_SAMPLE));
        }

        @Test
        public void testClear() {
            assertTrue(mtxHashBiMap.isEmpty());
            fillWithValues();
            assertFalse(mtxHashBiMap.isEmpty());

            assertThrows(UnsupportedOperationException.class, () -> mtxHashBiMap.inverse().clear());

            assertFalse(mtxHashBiMap.isEmpty());
        }

        @Test
        public void testKeySet() {
            Set<String> keySet = new HashSet<>() {{
                add("One");
                add("Two");
                add("Three");
            }};

            assertEquals(Set.of(), mtxHashBiMap.inverse().keySet());

            fillWithValues();

            assertEquals(keySet, mtxHashBiMap.inverse().keySet());
        }

        @Test
        public void testValues() {
            Set<Integer> values = new HashSet<>() {{
                add(1);
                add(2);
                add(3);
            }};

            assertArrayEquals(Set.of().toArray(), mtxHashBiMap.inverse().values().toArray());

            fillWithValues();

            assertArrayEquals(values.toArray(), mtxHashBiMap.inverse().values().toArray());
        }

        @Test
        public void testEntrySet() {
            Set<Map.Entry<String, Integer>> entries = new HashSet<>() {{
                add(Map.entry( "One", 1 ));
                add(Map.entry( "Two", 2));
                add(Map.entry( "Three", 3));
            }};

            assertEquals(Set.of(), mtxHashBiMap.inverse().entrySet());

            fillWithValues();

            assertEquals(entries, mtxHashBiMap.inverse().entrySet());
        }
    }

    private void fillWithValues() {
        mtxHashBiMap.put(1, "One");
        mtxHashBiMap.put(2, "Two");
        mtxHashBiMap.put(3, "Three");
    }
}
