package com.edavalos.mtx.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MtxCountMapTest {
    private MtxCountMap mtxCountMap;
    private LinkedHashMap<String, Integer> expectedSortedMap;

    @BeforeEach
    public void setUp() {
        mtxCountMap = new MtxCountMap();
        mtxCountMap.put("A", 3);
        mtxCountMap.put("B", 1);
        mtxCountMap.put("C", -4);
        mtxCountMap.put("D", 7);
        mtxCountMap.put("E", 0);

        expectedSortedMap = new LinkedHashMap<>();
        expectedSortedMap.put("D", 7);
        expectedSortedMap.put("A", 3);
        expectedSortedMap.put("B", 1);
        expectedSortedMap.put("E", 0);
        expectedSortedMap.put("C", -4);
    }

    @Test
    public void testGetMapSortedByCount() {
        LinkedHashMap<String, Integer> sortedMap = mtxCountMap.getMapSortedByCount();
        assertEquals(mapToJson(expectedSortedMap), mapToJson(sortedMap));
    }

    @Test
    public void testGetMostCommonKey_empty() {
        assertNull(new MtxCountMap().getMostCommonKey());
    }

    @Test
    public void testGetMostCommonKey_full() {
        assertEquals("D", mtxCountMap.getMostCommonKey());
    }

    @Test
    public void testToJson_empty() {
        assertEquals("{}", new MtxCountMap().toJson());
    }

    @Test
    public void testToJson_full() {
        assertEquals("{\"D\":7,\"A\":3,\"B\":1,\"E\":0,\"C\":-4}", mtxCountMap.toJson());
    }

    private String mapToJson(Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder("{");
        if (map.isEmpty()) {
            sb.append("}");
            return sb.toString();
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":").append(entry.getValue()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.append("}").toString();
    }
}
