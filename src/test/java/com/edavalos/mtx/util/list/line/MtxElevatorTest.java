package com.edavalos.mtx.util.list.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class MtxElevatorTest {
    private static final int FLOORS = 10;
    private static final List<Integer> UPWARD_FLOOR_QUEUE = new ArrayList<>(){{
        add(3);
        add(6);
        add(9);
    }};
    private static final List<Integer> DOWNWARD_FLOOR_QUEUE = new ArrayList<>(){{
        add(2);
        add(5);
        add(8);
    }};

    private MtxElevator<List<String>> mtxElevator;

    @BeforeEach
    public void setUp() {
        mtxElevator = new MtxElevator<>(FLOORS);

        for (int floorUp : UPWARD_FLOOR_QUEUE) {
            mtxElevator.queueFloor(1, floorUp);
            mtxElevator.queueFloor(floorUp, floorUp);
        }
        for (int floorDown : DOWNWARD_FLOOR_QUEUE) {
            mtxElevator.queueFloor(9, floorDown);
        }
    }

    @Test
    public void testConstructor_negativeFloors() {
        assertThrows(IndexOutOfBoundsException.class, () -> new MtxElevator<List<String>>(-1));
    }

    @Test
    public void testQueueFloor() {
        assertArrayEquals(UPWARD_FLOOR_QUEUE.toArray(), mtxElevator.getUpwardsFloorQueue().toArray());
        assertArrayEquals(DOWNWARD_FLOOR_QUEUE.toArray(), mtxElevator.getDownwardsFloorQueue().toArray());
    }

    @Test
    public void testQueueFloor_negativeFloors() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(1, -1));
    }

    @Test
    public void testMoveToNextFloor() {
        for (int i = 0; i < 6; i++) {
            System.out.println(Arrays.toString(mtxElevator.getUpwardsFloorQueue().toArray()));
            System.out.println(Arrays.toString(mtxElevator.getDownwardsFloorQueue().toArray()));
            System.out.println();
            mtxElevator.moveToNextFloor();
        }
    }

}
