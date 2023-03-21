package com.edavalos.mtx.util.list.line;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class MtxElevator<C> {
    @FunctionalInterface
    public interface MtxElevatorContentsManager<T> {
        T modifyContents(T contents);
    }

    // Direction key:
    //  1 : going up
    //  0 : standing still
    // -1 : going down

    // Typical order of operations for an elevator:
    // 1. queue floors (can happen whenever)
    // 2. move to next floor
    // 3. interact with contents
    // 4. go to step 2.

    private static final int SWITCH_DIRECTIONS_MARKER = -1;

    private final int floors;
    private final Queue<Integer> thisRideUp;
    private final Queue<Integer> nextRideUp;
    private final Queue<Integer> thisRideDown;
    private final Queue<Integer> nextRideDown;
    // @TODO: Make this an enum
    private int direction;
    private int currentFloor;
    private C contents;

    public MtxElevator(int floors) {
        if (floors < 0) {
            throw new IndexOutOfBoundsException("Negative numbers are not allowed: " + floors);
        }

        this.floors = floors;
        this.thisRideUp = new LinkedList<>();
        this.nextRideUp = new LinkedList<>();
        this.thisRideDown = new LinkedList<>();
        this.nextRideDown = new LinkedList<>();
        this.direction = 0;
        this.currentFloor = 0;
        this.contents = null;
    }

    public void queueFloor(int startingFloor, int destinationFloor) {
        if (startingFloor < 0 || destinationFloor < 0) {
            throw new IndexOutOfBoundsException("Negative numbers are not allowed: " +
                                                startingFloor + ", " + destinationFloor);
        }

        if (startingFloor > this.floors || destinationFloor > this.floors) {
            throw new IndexOutOfBoundsException("Highest floor is " + this.floors + ": " +
                                                startingFloor + ", " + destinationFloor);
        }

        if (startingFloor == destinationFloor) {
            // Already at destination
            return;
        }

        int floorDirection = (startingFloor < destinationFloor) ? 1 : -1;

        // Elevator going up (or standing still)
        if (this.direction == 1 || this.direction == 0) {
            // Going up
            if (floorDirection == 1) {
                // Both stops above
                if (destinationFloor > this.currentFloor && startingFloor >= this.currentFloor) {
                    this.thisRideUp.add(startingFloor);
                    this.thisRideUp.add(destinationFloor);
                }
                // First stop below
                else {
                    this.nextRideUp.add(startingFloor);
                    this.nextRideUp.add(destinationFloor);
                }
            }
            // Going down
            else {
                this.thisRideDown.add(startingFloor);
                this.thisRideDown.add(destinationFloor);
            }

            // If elevator is standing still, go up
            if (this.direction == 0) {
                this.direction = 1;
            }
        }
        // Elevator going down
        else {
            // Going down
            if (floorDirection == -1) {
                // Both stops below
                if (destinationFloor < this.currentFloor && startingFloor <= this.currentFloor) {
                    this.thisRideDown.add(startingFloor);
                    this.thisRideDown.add(destinationFloor);
                }
                // First stop below
                else {
                    this.nextRideDown.add(startingFloor);
                    this.nextRideDown.add(destinationFloor);
                }
            }
            // Going up
            else {
                this.thisRideUp.add(startingFloor);
                this.thisRideUp.add(destinationFloor);
            }
        }
    }

    public void moveToNextFloor() {

    }

    public C interactWithContents(MtxElevatorContentsManager<C> contentsManager) {
        return contentsManager.modifyContents(this.contents);
    }

    public int queueSize() {
        return this.thisRideUp.size() +
               this.nextRideUp.size() +
               this.thisRideDown.size() +
               this.nextRideDown.size();
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public int getDirection() {
        return this.direction;
    }

    public int getFloors() {
        return this.floors;
    }

    public C getContents() {
        return this.contents;
    }

    public void setContents(C contents) {
        this.contents = contents;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    protected List<Integer> getUpwardsFloorQueue() {
        List<Integer> upwardsQueue = new ArrayList<>();
        upwardsQueue.addAll(this.thisRideUp);
        upwardsQueue.addAll(this.nextRideUp);
        return upwardsQueue;
    }

    protected List<Integer> getDownwardsFloorQueue() {
        List<Integer> downwardsQueue = new ArrayList<>();
        downwardsQueue.addAll(this.thisRideDown);
        downwardsQueue.addAll(this.nextRideDown);
        return downwardsQueue;
    }

    @Override
    public String toString() {
        return "";
    }
}
