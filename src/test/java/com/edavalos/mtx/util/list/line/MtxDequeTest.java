package com.edavalos.mtx.util.list.line;

import com.edavalos.mtx.util.list.MtxArrayList;
import com.edavalos.mtx.util.list.MtxList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class MtxDequeTest {
    private MtxDeque<Integer> mtxDeque;

    @BeforeEach
    public void setUp() {
        mtxDeque = new MtxDeque<>();
    }

    @Test
    public void testEnqueueFront() {
        int elements = 5;
        String elementsListString = "[0, 1, 2, 3, 4]";
        for (int i = 0; i < elements; i++) {
            mtxDeque.enqueueFront(i);
        }

        assertEquals(elementsListString, mtxDeque.toString());
    }

    @Test
    public void testEnqueueBack() {
        int elements = 5;
        String elementsListString = "[4, 3, 2, 1, 0]";
        for (int i = 0; i < elements; i++) {
            mtxDeque.enqueueBack(i);
        }

        assertEquals(elementsListString, mtxDeque.toString());
    }

    @Test
    public void testDequeueFront() {
        int elements = 5;
        String elementsListString = "[0, 1, 2, 3, 4]";
        for (int i = 0; i < elements; i++) {
            mtxDeque.enqueueFront(i);
        }

        MtxList<Integer> dequeues = new MtxArrayList<>();
        for (int j = 0; j < elements; j++) {
            dequeues.add(mtxDeque.dequeueFront());
        }

        assertEquals(elementsListString, dequeues.toString());
        assertNull(mtxDeque.dequeueFront());
    }

    @Test
    public void testDequeueBack() {
        int elements = 5;
        String elementsListString = "[4, 3, 2, 1, 0]";
        for (int i = 0; i < elements; i++) {
            mtxDeque.enqueueFront(i);
        }

        LinkedList<Integer> dequeues = new LinkedList<>();
        for (int j = 0; j < elements; j++) {
            int e = mtxDeque.dequeueBack();
            dequeues.addLast(e);
        }

        assertEquals(elementsListString, dequeues.toString());
        assertNull(mtxDeque.dequeueBack());
    }

    @Test
    public void testEnqueueFrontDequeueBack() {
        mtxDeque.enqueueFront(1);
        mtxDeque.enqueueFront(2);
        mtxDeque.enqueueFront(3);

        assertEquals(3, mtxDeque.dequeueBack());
        assertEquals(2, mtxDeque.dequeueBack());

        mtxDeque.enqueueFront(4);
        mtxDeque.enqueueFront(5);
        mtxDeque.enqueueFront(6);

        assertEquals(6, mtxDeque.dequeueBack());
        assertEquals(5, mtxDeque.dequeueBack());
    }

    @Test
    public void testEnqueueBackDequeueFront() {
        mtxDeque.enqueueBack(1);
        mtxDeque.enqueueBack(2);
        mtxDeque.enqueueBack(3);

        assertEquals(3, mtxDeque.dequeueFront());
        assertEquals(2, mtxDeque.dequeueFront());

        mtxDeque.enqueueBack(4);
        mtxDeque.enqueueBack(5);
        mtxDeque.enqueueBack(6);

        assertEquals(6, mtxDeque.dequeueFront());
        assertEquals(5, mtxDeque.dequeueFront());
    }

    @Test
    public void testToString() {
        assertEquals("[]", mtxDeque.toString());

        mtxDeque.enqueueFront(3);
        mtxDeque.enqueueFront(6);
        mtxDeque.enqueueFront(9);
        mtxDeque.enqueueFront(12);

        mtxDeque.dequeueFront();
        mtxDeque.dequeueBack();

        assertEquals("[6, 9]", mtxDeque.toString());
    }

    @Test
    public void testSize() {
        assertEquals(0, mtxDeque.size());

        mtxDeque.enqueueFront(3);
        assertEquals(1, mtxDeque.size());

        mtxDeque.enqueueBack(6);
        mtxDeque.enqueueFront(9);
        mtxDeque.enqueueBack(12);
        assertEquals(4, mtxDeque.size());

        mtxDeque.dequeueFront();
        mtxDeque.dequeueBack();
        assertEquals(2, mtxDeque.size());

        while (mtxDeque.size() > 0) {
            mtxDeque.dequeueFront();
        }
        assertEquals(0, mtxDeque.size());
    }
}
