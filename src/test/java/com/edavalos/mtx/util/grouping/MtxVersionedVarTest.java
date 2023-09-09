package com.edavalos.mtx.util.grouping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
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

        assertNull(mtxVersionedVar.setValue(String.valueOf(-1)));
        for (int i = 0; i < 20; i++) {
            assertEquals(String.valueOf(i - 1), mtxVersionedVar.setValue(String.valueOf(i)));
            assertEquals(String.valueOf(i), mtxVersionedVar.getValue());
        }

        assertNotNull(mtxVersionedVar.getValue());
    }

    @Test
    public void testSetValue_null() {
        String errorMsg = assertThrows(
                NullPointerException.class,
                () -> mtxVersionedVar.setValue(null)
        ).getMessage();

        assertEquals(MtxVersionedVar.ERROR_NULL, errorMsg);
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
        mtxVersionedVar.setValue("one");
        mtxVersionedVar.setValue("two");
        mtxVersionedVar.setValue("three");

        assertNotNull(mtxVersionedVar.getValueAtVersion(2));
        assertNull(mtxVersionedVar.getValueAtVersion(5));
    }

    @Test
    public void testTotalVersions() {
        mtxVersionedVar.setValue("one");
        mtxVersionedVar.setValue("two");
        mtxVersionedVar.setValue("three");

        assertEquals(3, mtxVersionedVar.totalVersions());

        for (int i = 0; i < 17; i++) {
            mtxVersionedVar.setValue("etc.");
        }

        assertEquals(20, mtxVersionedVar.totalVersions());
    }

    @Test
    public void testGetAllVersions() {
        mtxVersionedVar.setValue("one");
        mtxVersionedVar.setValue("two");
        mtxVersionedVar.setValue("three");
        Collection<String> actualHistory1 = mtxVersionedVar.getAllVersions();
        mtxVersionedVar.setValue("four");
        mtxVersionedVar.setValue("five");
        mtxVersionedVar.setValue("six");
        Collection<String> actualHistory2 = mtxVersionedVar.getAllVersions();
        mtxVersionedVar.setValue("seven");

        String expectedHistory1 = "[one, two, three]";
        String expectedHistory2 = "[one, two, three, four, five, six]";

        assertEquals(expectedHistory1, actualHistory1.toString());
        assertEquals(expectedHistory2, actualHistory2.toString());
    }
}
