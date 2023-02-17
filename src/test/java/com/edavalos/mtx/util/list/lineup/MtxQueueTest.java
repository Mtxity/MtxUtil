package com.edavalos.mtx.util.list.lineup;

import com.edavalos.mtx.util.list.MtxArrayList;
import com.edavalos.mtx.util.list.MtxList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class MtxQueueTest {
    private MtxQueue<Integer> mtxQueue;

    @BeforeEach
    public void setUp() {
        mtxQueue = new MtxQueue<>();
    }

    @Test
    public void testEnqueue() {
        int elements = 5;
        String elementsListString = "[0, 1, 2, 3, 4]";
        for (int i = 0; i < elements; i++) {
            mtxQueue.enqueue(i);
        }

        assertEquals(elementsListString, mtxQueue.toString());
    }

    @Test
    public void testDequeue() {
        int elements = 5;
        String elementsListString = "[0, 1, 2, 3, 4]";
        for (int i = 0; i < elements; i++) {
            mtxQueue.enqueue(i);
        }

        MtxList<Integer> dequeues = new MtxArrayList<>();
        for (int j = 0; j < elements; j++) {
            dequeues.add(mtxQueue.dequeue());
        }

        assertEquals(elementsListString, dequeues.toString());
        assertNull(mtxQueue.dequeue());
    }

    @Test
    public void testEnqueueDequeue() {
        mtxQueue.enqueue(1);
        mtxQueue.enqueue(2);
        mtxQueue.enqueue(3);

        assertEquals(1, mtxQueue.dequeue());
        assertEquals(2, mtxQueue.dequeue());

        mtxQueue.enqueue(4);
        mtxQueue.enqueue(5);
        mtxQueue.enqueue(6);

        assertEquals(3, mtxQueue.dequeue());
        assertEquals(4, mtxQueue.dequeue());
    }

    @Test
    public void testToString() {
        assertEquals("[]", mtxQueue.toString());

        mtxQueue.enqueue(3);
        mtxQueue.enqueue(6);
        mtxQueue.enqueue(9);
        mtxQueue.enqueue(12);

        mtxQueue.dequeue();
        mtxQueue.dequeue();

        assertEquals("[9, 12]", mtxQueue.toString());
    }

    @Test
    public void testSize() {
        assertEquals(0, mtxQueue.size());

        mtxQueue.enqueue(3);
        assertEquals(1, mtxQueue.size());

        mtxQueue.enqueue(6);
        mtxQueue.enqueue(9);
        mtxQueue.enqueue(12);
        assertEquals(4, mtxQueue.size());

        mtxQueue.dequeue();
        mtxQueue.dequeue();
        assertEquals(2, mtxQueue.size());

        while (mtxQueue.size() > 0) {
            mtxQueue.dequeue();
        }
        assertEquals(0, mtxQueue.size());
    }
}
