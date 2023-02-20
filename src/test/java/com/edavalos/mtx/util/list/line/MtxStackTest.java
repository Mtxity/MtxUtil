package com.edavalos.mtx.util.list.line;

import com.edavalos.mtx.util.list.MtxArrayList;
import com.edavalos.mtx.util.list.MtxList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class MtxStackTest {
    private MtxStack<Integer> mtxStack;

    @BeforeEach
    public void setUp() {
        mtxStack = new MtxStack<>();
    }

    @Test
    public void testPush() {
        int elements = 5;
        String elementsListString = "[0, 1, 2, 3, 4]";
        for (int i = 0; i < elements; i++) {
            mtxStack.push(i);
        }

        assertEquals(elementsListString, mtxStack.toString());
    }

    @Test
    public void testPop() {
        int elements = 5;
        String elementsListString = "[4, 3, 2, 1, 0]";
        for (int i = 0; i < elements; i++) {
            mtxStack.push(i);
        }

        MtxList<Integer> popped = new MtxArrayList<>();
        for (int j = 0; j < elements; j++) {
            popped.add(mtxStack.pop());
        }

        assertEquals(elementsListString, popped.toString());
        assertNull(mtxStack.pop());
    }

    @Test
    public void testPushPop() {
        mtxStack.push(1);
        mtxStack.push(2);
        mtxStack.push(3);

        assertEquals(3, mtxStack.pop());
        assertEquals(2, mtxStack.pop());

        mtxStack.push(4);
        mtxStack.push(5);
        mtxStack.push(6);

        assertEquals(6, mtxStack.pop());
        assertEquals(5, mtxStack.pop());
        assertEquals(4, mtxStack.pop());
        assertEquals(1, mtxStack.pop());
        assertNull(mtxStack.pop());
    }

    @Test
    public void testPeek() {
        int elements = 10;
        for (int i = 0; i < elements; i++) {
            mtxStack.push(i);
        }

        for (int j = elements - 1; j >= 0; j--) {
            assertEquals(j, mtxStack.peek());
            mtxStack.pop();
        }
        assertNull(mtxStack.peek());
    }

    @Test
    public void testToString() {
        assertEquals("[]", mtxStack.toString());

        mtxStack.push(3);
        mtxStack.push(6);
        mtxStack.push(9);
        mtxStack.push(12);

        mtxStack.pop();
        mtxStack.pop();

        assertEquals("[3, 6]", mtxStack.toString());
    }

    @Test
    public void testSize() {
        assertEquals(0, mtxStack.size());

        mtxStack.push(3);
        assertEquals(1, mtxStack.size());

        mtxStack.push(6);
        mtxStack.push(9);
        mtxStack.push(12);
        assertEquals(4, mtxStack.size());

        mtxStack.pop();
        mtxStack.pop();
        assertEquals(2, mtxStack.size());

        while (mtxStack.size() > 0) {
            mtxStack.pop();
        }
        assertEquals(0, mtxStack.size());
    }
}
