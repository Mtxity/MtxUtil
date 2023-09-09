package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MtxVersionedVarTest {
    private MtxVersionedVar<String> mtxVersionedVar;

    @BeforeEach
    public void setUp() {
        mtxVersionedVar = new MtxVersionedVar<>();
    }

    @Test
    public void testGetValue() {
        assertNull(mtxVersionedVar.getValue());

        mtxVersionedVar = new MtxVersionedVar<>("x");
        assertEquals("x", mtxVersionedVar.getValue());
    }

    @Test
    public void testSetValue() {
        assertNull(mtxVersionedVar.getValue());

        for (int i = 0; i < 20; i++) {
            mtxVersionedVar.setValue(String.valueOf(i));
            assertEquals(String.valueOf(i), mtxVersionedVar.getValue());
        }

        assertNotNull(mtxVersionedVar.getValue());
    }

    @Test
    public void testGetValueAtVersion() {
        Stack<String> values = new Stack<>();
        values.push("Unit");
        values.push("Pair");
        values.push("Triplet");
        values.push("Quartet");
        values.push("Quintet");
        values.push("Sextet");
        values.push("Septet");
        values.push("Octet");
        values.push("Ennead");
        values.push("Decade");

        for (int i = 0; i < 10; i++) {
            mtxVersionedVar.setValue(values.get(i));
        }

        for (int j = 0; j < 10; j++) {
            assertEquals(values.pop(), mtxVersionedVar.getValueAtVersion(j));
        }
    }

    @Test
    public void testGetValueAtVersion_negativeArg() {
        IndexOutOfBoundsException ioobe = assertThrows(
                IndexOutOfBoundsException.class,
                () -> mtxVersionedVar.getValueAtVersion(-5)
        );

        assertEquals("Index out of range: " + (-5), ioobe.getMessage());
    }

    @Test
    public void testGetValueAtVersion_negativeVersion() {
        mtxVersionedVar.setValue(null);
        mtxVersionedVar.setValue(null);
        mtxVersionedVar.setValue(null);
        
        assertNull(mtxVersionedVar.getValueAtVersion(5));
    }
}
