package com.edavalos.mtx.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxTypeBoundMapTest {
    private MtxTypeBoundMap mtxTypeBoundMap;

    @BeforeEach
    public void setUp() {
        mtxTypeBoundMap = new MtxTypeBoundMap();
        mtxTypeBoundMap.put("string", "Test");
        mtxTypeBoundMap.put("int", 32);
        mtxTypeBoundMap.put("bool", true);
        mtxTypeBoundMap.put("double", 5.5);
        mtxTypeBoundMap.put("long", 640L);
        mtxTypeBoundMap.put("char", 'Y');
    }

    @Test
    public void testGetString() {
        assertNull(mtxTypeBoundMap.getString("unknown"));
        assertEquals("Test", mtxTypeBoundMap.getString("string"));
    }

    @Test
    public void testGetInteger() {
        assertNull(mtxTypeBoundMap.getInteger("unknown"));
        assertEquals(32, mtxTypeBoundMap.getInteger("int"));
    }

    @Test
    public void testGetBoolean() {
        assertNull(mtxTypeBoundMap.getBoolean("unknown"));
        assertTrue(mtxTypeBoundMap.getBoolean("bool"));
    }

    @Test
    public void testGetDouble() {
        assertNull(mtxTypeBoundMap.getDouble("unknown"));
        assertEquals(5.5, mtxTypeBoundMap.getDouble("double"));
    }

    @Test
    public void testGetLong() {
        assertNull(mtxTypeBoundMap.getLong("unknown"));
        assertEquals(640L, mtxTypeBoundMap.getLong("long"));
    }

    @Test
    public void testGetCharacter() {
        assertNull(mtxTypeBoundMap.getCharacter("unknown"));
        assertEquals('Y', mtxTypeBoundMap.getCharacter("char"));
    }
}
