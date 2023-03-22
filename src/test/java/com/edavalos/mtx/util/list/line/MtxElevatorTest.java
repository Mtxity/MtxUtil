package com.edavalos.mtx.util.list.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxElevatorTest {
    private static final int FLOORS = 10;
    private static final List<Integer> UPWARD_FLOOR_QUEUE = new ArrayList<>();
    private static final List<Integer> DOWNWARD_FLOOR_QUEUE = new ArrayList<>(){{
        add(2);
        add(5);
        add(8);
        add(9);
    }};

    private MtxElevator<List<String>> mtxElevator;

    @BeforeEach
    public void setUp() {
        List<Integer> upwardsFloors = new ArrayList<>(){{
            add(3);
            add(6);
            add(9);
        }};
        List<Integer> downwardsFloors = new ArrayList<>(){{
            add(2);
            add(5);
            add(8);
        }};
        mtxElevator = new MtxElevator<>(FLOORS);

        for (int floorUp : upwardsFloors) {
            mtxElevator.queueFloor(1, floorUp);
            mtxElevator.queueFloor(floorUp, floorUp);
        }
        for (int i = 0; i < upwardsFloors.size() + 1; i++) {
            mtxElevator.moveToNextFloor();
        }
        for (int floorDown : downwardsFloors) {
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
    public void testQueueFloor_firstStopBelow_goingUp() {
        mtxElevator.moveToNextFloor();
        mtxElevator.moveToNextFloor();
        mtxElevator.setDirection(1);
        mtxElevator.queueFloor(5, 10);
        assertArrayEquals(new Integer[]{2,5}, mtxElevator.getDownwardsFloorQueue().toArray());
    }

    @Test
    public void testQueueFloor_firstStopAbove_goingDown() {
        mtxElevator.moveToNextFloor();
        mtxElevator.moveToNextFloor();
        mtxElevator.queueFloor(9, 1);
        assertArrayEquals(new Integer[]{2,5,1}, mtxElevator.getDownwardsFloorQueue().toArray());
    }

    @Test
    public void testQueueFloor_oppositeDirection() {
        mtxElevator.moveToNextFloor();
        mtxElevator.moveToNextFloor();
        mtxElevator.setDirection(1);
        mtxElevator.queueFloor(10, 4);
        assertArrayEquals(new Integer[]{2,4,5}, mtxElevator.getDownwardsFloorQueue().toArray());
    }

    @Test
    public void testQueueFloor_negativeFloors() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(1, -1));
    }

    @Test
    public void testQueueFloor_floorTooHigh() {
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(FLOORS + 1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> mtxElevator.queueFloor(1, FLOORS + 1));
    }

    @Test
    public void testMoveToNextFloor() {
        assertArrayEquals(new Integer[]{2,5,8,9}, mtxElevator.getDownwardsFloorQueue().toArray());
        assertTrue(mtxElevator.moveToNextFloor());

        assertArrayEquals(new Integer[]{2,5,8}, mtxElevator.getDownwardsFloorQueue().toArray());
        assertTrue(mtxElevator.moveToNextFloor());

        assertArrayEquals(new Integer[]{2,5}, mtxElevator.getDownwardsFloorQueue().toArray());
        assertTrue(mtxElevator.moveToNextFloor());

        assertArrayEquals(new Integer[]{2}, mtxElevator.getDownwardsFloorQueue().toArray());
        assertTrue(mtxElevator.moveToNextFloor());

        assertArrayEquals(new Integer[]{}, mtxElevator.getDownwardsFloorQueue().toArray());
        assertFalse(mtxElevator.moveToNextFloor());
    }

    @Test
    public void testMoveToNextFloor_standingStill() {
        mtxElevator.setDirection(0);
        mtxElevator.moveToNextFloor();
        assertEquals(-1, mtxElevator.getDirection());

        mtxElevator.queueFloor(9,10);
        mtxElevator.setDirection(0);
        mtxElevator.moveToNextFloor();
        assertEquals(1, mtxElevator.getDirection());
    }

    @Test
    public void testQueueSize() {
        int expectedQueueSize = UPWARD_FLOOR_QUEUE.size() + DOWNWARD_FLOOR_QUEUE.size();
        assertEquals(expectedQueueSize, mtxElevator.queueSize());
    }

    @Test
    public void testInteractWithContents() {
        List<String> riders = new ArrayList<>(){{
            add("Person 1");
            add("Person 2");
            add("Person 3");
        }};
        mtxElevator.setContents(riders);
        assertEquals(3, mtxElevator.getContents().size());

        mtxElevator.interactWithContents(contents -> {
            contents.set(0, "Person x");
            contents.add("Person 4");
            return contents;
        });
        List<String> newRiders = new ArrayList<>(){{
            add("Person x");
            add("Person 2");
            add("Person 3");
            add("Person 4");
        }};

        assertEquals(4, mtxElevator.getContents().size());
        assertArrayEquals(newRiders.toArray(), mtxElevator.getContents().toArray());
    }

    @Test
    public void testSetDirection() {
        assertEquals(-1, mtxElevator.getDirection());
        mtxElevator.setDirection(1);
        assertEquals(1, mtxElevator.getDirection());
    }

    @Test
    public void testGetFloors() {
        assertEquals(FLOORS, mtxElevator.getFloors());
    }

    @Test
    public void testGetCurrentFloor() {
        assertEquals(9, mtxElevator.getCurrentFloor());
    }

}
