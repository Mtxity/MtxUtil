package com.edavalos.mtx.util.list.line;

import com.edavalos.mtx.util.list.MtxArrayList;
import com.edavalos.mtx.util.list.MtxList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testContains() {
        assertFalse(mtxStack.contains(3));

        mtxStack.push(3);
        assertTrue(mtxStack.contains(3));

        mtxStack.pop();
        assertFalse(mtxStack.contains(3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mtxStack.isEmpty());

        mtxStack.push(3);
        assertFalse(mtxStack.isEmpty());
    }

    @Test
    public void testGet() {
        // Empty stack → out of bounds
        assertThrows(IndexOutOfBoundsException.class, () -> mtxStack.get(0));

        // One element
        mtxStack.push(3);
        assertEquals(3, mtxStack.get(0));

        // Multiple elements (top-based indexing)
        mtxStack.push(6);
        mtxStack.push(9);
        mtxStack.push(12);

        // Stack (top→bottom): [12, 9, 6, 3]
        assertEquals(12, mtxStack.get(0));
        assertEquals(9, mtxStack.get(1));
        assertEquals(6, mtxStack.get(2));
        assertEquals(3, mtxStack.get(3));

        // Out of bounds and negative
        assertThrows(IndexOutOfBoundsException.class, () -> mtxStack.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxStack.get(-1));

        // Non-mutating
        assertEquals(4, mtxStack.size());
        assertEquals(12, mtxStack.peek());

    }

    @Nested
    class IsValidParenthesisTests {

        @Test
        public void testIsValidParenthesis_valid() {
            final String validParenthesis = "()[]{}<({test}{test2})test3>";
            assertTrue(MtxStack.isValidParenthesis(validParenthesis));
        }

        @Test
        public void testIsValidParenthesis_invalid1() {
            final String invalidParenthesis = "([)]{(}<({test}{test2})test3(>";
            assertFalse(MtxStack.isValidParenthesis(invalidParenthesis));
        }

        @Test
        public void testIsValidParenthesis_invalid2() {
            final String invalidParenthesis = "()[]{}<({test}{test2<<})test3>";
            assertFalse(MtxStack.isValidParenthesis(invalidParenthesis));
        }

        @Test
        public void testIsValidParenthesis_invalid3() {
            final String invalidParenthesis = "()[]{}[<({test}{test2<<})test3>";
            assertFalse(MtxStack.isValidParenthesis(invalidParenthesis));
        }
    }
}
