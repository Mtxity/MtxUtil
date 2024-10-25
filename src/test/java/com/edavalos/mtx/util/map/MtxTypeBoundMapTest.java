package com.edavalos.mtx.util.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.invoke.WrongMethodTypeException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        mtxTypeBoundMap.put("list", List.of(1, 2, 3));
        mtxTypeBoundMap.put("map", Map.of(1, 2));
    }

    @Test
    public void testGetString() {
        assertNull(mtxTypeBoundMap.getString("unknown"));
        assertEquals("Test", mtxTypeBoundMap.getString("string"));
        testWrongMethodType("int", () -> mtxTypeBoundMap.getString("int"), "string");
    }

    @Test
    public void testGetInteger() {
        assertNull(mtxTypeBoundMap.getInteger("unknown"));
        assertEquals(32, mtxTypeBoundMap.getInteger("int"));
        testWrongMethodType("bool", () -> mtxTypeBoundMap.getInteger("bool"), "n integer");
    }

    @Test
    public void testGetBoolean() {
        assertNull(mtxTypeBoundMap.getBoolean("unknown"));
        assertTrue(mtxTypeBoundMap.getBoolean("bool"));
        testWrongMethodType("double", () -> mtxTypeBoundMap.getBoolean("double"), "boolean");
    }

    @Test
    public void testGetDouble() {
        assertNull(mtxTypeBoundMap.getDouble("unknown"));
        assertEquals(5.5, mtxTypeBoundMap.getDouble("double"));
        testWrongMethodType("long", () -> mtxTypeBoundMap.getDouble("long"), "double");
    }

    @Test
    public void testGetLong() {
        assertNull(mtxTypeBoundMap.getLong("unknown"));
        assertEquals(640L, mtxTypeBoundMap.getLong("long"));
        testWrongMethodType("char", () -> mtxTypeBoundMap.getLong("char"), "long");
    }

    @Test
    public void testGetCharacter() {
        assertNull(mtxTypeBoundMap.getCharacter("unknown"));
        assertEquals('Y', mtxTypeBoundMap.getCharacter("char"));
        testWrongMethodType("string", () -> mtxTypeBoundMap.getCharacter("string"), "character");
    }

    @Test
    public void testGetList() {
        assertNull(mtxTypeBoundMap.getList("unknown"));
        assertEquals(List.of(1, 2, 3), mtxTypeBoundMap.getList("list"));
        testWrongMethodType("string", () -> mtxTypeBoundMap.getList("string"), "list");
    }

    @Test
    public void testGetMap() {
        assertNull(mtxTypeBoundMap.getMap("unknown"));
        assertEquals(Map.of(1, 2), mtxTypeBoundMap.getMap("map"));
        testWrongMethodType("string", () -> mtxTypeBoundMap.getMap("string"), "map");
    }

    private void testWrongMethodType(String key, Executable executable, String typeName) {
        if (!typeName.startsWith("n")) {
            typeName = " " + typeName;
        }
        String expectedErrMsg = "Value for key '" + key + "' is not a" + typeName;
        WrongMethodTypeException wmte = assertThrows(WrongMethodTypeException.class, executable);

        assertEquals(expectedErrMsg, wmte.getMessage());
    }
}
