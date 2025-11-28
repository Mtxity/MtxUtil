package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxStringWrapperTest {

    @Test
    public void testConstructor_stringArg() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("sample");
        assertEquals("sample", mtxStringWrapper.toString());
    }

    @Test
    public void testConstructor_noArg() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper();
        assertEquals("", mtxStringWrapper.toString());
    }

    @Test
    public void testUnwrap() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("sample");
        assertEquals("sample", mtxStringWrapper.unwrap());
    }

    @Test
    public void testToString() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("sample");
        assertEquals("sample", mtxStringWrapper.toString());
    }

    @Test
    public void testSet() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper();
        mtxStringWrapper.set("sample");
        assertEquals("sample", mtxStringWrapper.toString());
    }

    @Test
    public void testAdd() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("12");
        mtxStringWrapper.add("3");
        assertEquals("123", mtxStringWrapper.toString());
    }

    @Test
    public void testClear_previouslyEmpty() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper();
        assertFalse(mtxStringWrapper.clear());
        assertEquals("", mtxStringWrapper.toString());
    }

    @Test
    public void testClear_previouslyNotEmpty() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("12");
        assertTrue(mtxStringWrapper.clear());
        assertEquals("", mtxStringWrapper.toString());
    }

    @Test
    public void testIsEmpty_empty() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper();
        assertTrue(mtxStringWrapper.isEmpty());
    }

    @Test
    public void testIsEmpty_notEmpty() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("test");
        assertFalse(mtxStringWrapper.isEmpty());
    }

    @Test
    public void testRemove_didRemove() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("test");
        assertTrue(mtxStringWrapper.remove("es"));
        assertEquals("tt", mtxStringWrapper.toString());
    }

    @Test
    public void testRemove_didNotRemove() {
        MtxStringWrapper mtxStringWrapper = new MtxStringWrapper("test");
        assertFalse(mtxStringWrapper.remove("esx"));
        assertEquals("test", mtxStringWrapper.toString());
    }
}
