package com.edavalos.mtx.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MtxTreeBiMapTest {
    private MtxTreeBiMap<Integer, String> mtxTreeBiMap;

    @BeforeEach
    public void setUp() {
        mtxTreeBiMap = new MtxTreeBiMap<>();
    }

    @Nested
    class TestForward {

        @Test
        public void testSize() {
            assertEquals(0, mtxTreeBiMap.size());

            fillWithValues();

            assertEquals(3, mtxTreeBiMap.size());
        }

        @Test
        public void testIsEmpty() {
            assertTrue(mtxTreeBiMap.isEmpty());

            fillWithValues();

            assertFalse(mtxTreeBiMap.isEmpty());
        }

        @Test
        public void testContainsKey() {
            assertFalse(mtxTreeBiMap.containsKey(null));
            assertFalse(mtxTreeBiMap.containsKey(2));

            fillWithValues();

            assertTrue(mtxTreeBiMap.containsKey(2));
        }

        @Test
        public void testContainsValue() {
            assertFalse(mtxTreeBiMap.containsValue(null));
            assertFalse(mtxTreeBiMap.containsValue("Two"));

            fillWithValues();

            assertTrue(mtxTreeBiMap.containsValue("Two"));
        }

        @Test
        public void testGet() {
            assertNull(mtxTreeBiMap.get(1));

            fillWithValues();

            assertEquals("One", mtxTreeBiMap.get(1));
        }

        @Test
        public void testGetOrDefault() {
            fillWithValues();

            assertEquals("Two", mtxTreeBiMap.getOrDefault(2, "Four"));
            assertEquals("Four", mtxTreeBiMap.getOrDefault(4, "Four"));
        }

        @Nested
        class TestPut {

            @Test
            public void testPut() {
                assertNull(mtxTreeBiMap.get(3));
                assertNull(mtxTreeBiMap.get(4));

                fillWithValues();

                assertEquals("Three", mtxTreeBiMap.put(3, "III"));
                assertNull(mtxTreeBiMap.put(4, "IV"));

                assertEquals("III", mtxTreeBiMap.get(3));
                assertEquals("IV", mtxTreeBiMap.get(4));
            }

            @Test
            public void testPut_nullKeyOrValue() {
                assertThrows(NullPointerException.class, () -> mtxTreeBiMap.put(1, null));
                assertThrows(NullPointerException.class, () -> mtxTreeBiMap.put(null, "One"));
            }

            @Test
            public void testPut_duplicateValue() {
                fillWithValues();

                assertThrows(IllegalArgumentException.class, () -> mtxTreeBiMap.put(4, "Three"));
            }
        }

        @Test
        public void testRemove() {
            assertThrows(NullPointerException.class, () -> mtxTreeBiMap.remove(3));

            fillWithValues();

            assertEquals("Three", mtxTreeBiMap.remove(3));
            assertEquals(2, mtxTreeBiMap.size());
            assertThrows(NullPointerException.class, () -> mtxTreeBiMap.remove(3));
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
                assertTrue(mtxTreeBiMap.isEmpty());

                mtxTreeBiMap.putAll(TEST_CASE_VALID);

                assertEquals(mtxTreeBiMap.keySet(), TEST_CASE_VALID.keySet());
                assertArrayEquals(mtxTreeBiMap.values().toArray(), TEST_CASE_VALID.values().toArray());
            }

            @Test
            public void testPutAll_duplicateValuesInArg() {
                assertTrue(mtxTreeBiMap.isEmpty());

                assertThrows(IllegalArgumentException.class, () -> mtxTreeBiMap.putAll(TEST_CASE_INVALID));
            }

            @Test
            public void testPutAll_duplicateValuesInMap() {
                assertTrue(mtxTreeBiMap.isEmpty());

                mtxTreeBiMap.put(4, "Three");

                assertThrows(IllegalArgumentException.class, () -> mtxTreeBiMap.putAll(TEST_CASE_VALID));
            }
        }

        @Test
        public void testClear() {
            assertTrue(mtxTreeBiMap.isEmpty());
            fillWithValues();
            assertFalse(mtxTreeBiMap.isEmpty());

            mtxTreeBiMap.clear();

            assertTrue(mtxTreeBiMap.isEmpty());
        }

        @Test
        public void testKeySet() {
            Set<Integer> keySet = new HashSet<>() {{
                add(1);
                add(2);
                add(3);
            }};

            assertEquals(Set.of(), mtxTreeBiMap.keySet());

            fillWithValues();

            assertEquals(keySet, mtxTreeBiMap.keySet());
        }

        @Test
        public void testValues() {
            Set<String> values = new HashSet<>() {{
                add("One");
                add("Two");
                add("Three");
            }};

            assertArrayEquals(Set.of().toArray(), mtxTreeBiMap.values().toArray());

            fillWithValues();

            assertArrayEquals(values.toArray(), mtxTreeBiMap.values().toArray());
        }

        @Test
        public void testEntrySet() {
            Set<Map.Entry<Integer, String >> entries = new HashSet<>() {{
                add(Map.entry(1, "One"));
                add(Map.entry(2, "Two"));
                add(Map.entry(3, "Three"));
            }};

            assertEquals(Set.of(), mtxTreeBiMap.entrySet());

            fillWithValues();

            assertEquals(entries, mtxTreeBiMap.entrySet());
        }
    }

    @Nested
    class TestBackward {

        @Test
        public void testSize() {
            assertEquals(0, mtxTreeBiMap.inverse().size());

            fillWithValues();

            assertEquals(3, mtxTreeBiMap.inverse().size());
        }

        @Test
        public void testIsEmpty() {
            assertTrue(mtxTreeBiMap.inverse().isEmpty());

            fillWithValues();

            assertFalse(mtxTreeBiMap.inverse().isEmpty());
        }

        @Test
        public void testContainsKey() {
            assertFalse(mtxTreeBiMap.inverse().containsKey(null));
            assertFalse(mtxTreeBiMap.inverse().containsKey("Two"));

            fillWithValues();

            assertTrue(mtxTreeBiMap.inverse().containsKey("Two"));
        }

        @Test
        public void testContainsValue() {
            assertFalse(mtxTreeBiMap.inverse().containsValue(null));
            assertFalse(mtxTreeBiMap.inverse().containsValue(2));

            fillWithValues();

            assertTrue(mtxTreeBiMap.inverse().containsValue(2));
        }

        @Test
        public void testGet() {
            assertNull(mtxTreeBiMap.inverse().get("One"));

            fillWithValues();

            assertEquals(1, mtxTreeBiMap.inverse().get("One"));
        }

        @Test
        public void testGetOrDefault() {
            fillWithValues();

            assertEquals(2, mtxTreeBiMap.inverse().getOrDefault("Two", 4));
            assertEquals(4, mtxTreeBiMap.inverse().getOrDefault("Four", 4));
        }

        @Test
        public void testInverse() {
            assertTrue(mtxTreeBiMap.isEmpty());
            assertTrue(mtxTreeBiMap.inverse().isEmpty());

            fillWithValues();

            assertTrue(mtxTreeBiMap.keySet().containsAll(mtxTreeBiMap.inverse().values()));
            assertTrue(mtxTreeBiMap.values().containsAll(mtxTreeBiMap.inverse().keySet()));

            assertThrows(UnsupportedOperationException.class, () -> mtxTreeBiMap.inverse().inverse());
        }

        @Test
        public void testPut() {
            assertThrows(UnsupportedOperationException.class, () -> mtxTreeBiMap.inverse().put("Four", 4));
        }

        @Test
        public void testRemove() {
            fillWithValues();
            assertThrows(UnsupportedOperationException.class, () -> mtxTreeBiMap.inverse().remove("Two"));
        }

        @Test
        public void testPutAll() {
            Map<String, Integer> TEST_CASE_SAMPLE = new HashMap<>() {{
                put("One", 1);
            }};

            assertTrue(mtxTreeBiMap.isEmpty());

            assertThrows(UnsupportedOperationException.class, () -> mtxTreeBiMap.inverse().putAll(TEST_CASE_SAMPLE));
        }

        @Test
        public void testClear() {
            assertTrue(mtxTreeBiMap.isEmpty());
            fillWithValues();
            assertFalse(mtxTreeBiMap.isEmpty());

            assertThrows(UnsupportedOperationException.class, () -> mtxTreeBiMap.inverse().clear());

            assertFalse(mtxTreeBiMap.isEmpty());
        }

        @Test
        public void testKeySet() {
            Set<String> keySet = new HashSet<>() {{
                add("One");
                add("Two");
                add("Three");
            }};

            assertEquals(Set.of(), mtxTreeBiMap.inverse().keySet());

            fillWithValues();

            assertEquals(keySet, mtxTreeBiMap.inverse().keySet());
        }

        @Test
        public void testValues() {
            Set<Integer> values = new HashSet<>() {{
                add(1);
                add(2);
                add(3);
            }};

            assertArrayEquals(Set.of().toArray(), mtxTreeBiMap.inverse().values().toArray());

            fillWithValues();

            assertTrue(mtxTreeBiMap.inverse().values().containsAll(values));
        }

        @Test
        public void testEntrySet() {
            Set<Map.Entry<String, Integer>> entries = new HashSet<>() {{
                add(Map.entry( "One", 1 ));
                add(Map.entry( "Two", 2));
                add(Map.entry( "Three", 3));
            }};

            assertEquals(Set.of(), mtxTreeBiMap.inverse().entrySet());

            fillWithValues();

            assertEquals(entries, mtxTreeBiMap.inverse().entrySet());
        }
    }

    @Test
    public void testEquals() {
        MtxTreeBiMap<Integer, String> copy = getNewCopyMap();
        fillWithValues();

        assertTrue(mtxTreeBiMap.equals(copy));
        assertTrue(copy.equals(mtxTreeBiMap));

        assertFalse(mtxTreeBiMap.equals("Sample"));
    }

    @Test
    public void testHashCode() {
        MtxTreeBiMap<Integer, String> copy = getNewCopyMap();
        fillWithValues();

        assertEquals(copy.hashCode(), mtxTreeBiMap.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "{1=One, 2=Two, 3=Three}";
        fillWithValues();

        assertEquals(expected, mtxTreeBiMap.toString());
    }

    private void fillWithValues() {
        mtxTreeBiMap.put(1, "One");
        mtxTreeBiMap.put(2, "Two");
        mtxTreeBiMap.put(3, "Three");
    }

    private MtxTreeBiMap<Integer, String> getNewCopyMap() {
        MtxTreeBiMap<Integer, String> copy = new MtxTreeBiMap<>();
        copy.put(1, "One");
        copy.put(2, "Two");
        copy.put(3, "Three");
        return copy;
    }
}
