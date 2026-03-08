package com.edavalos.mtx.util.list.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
