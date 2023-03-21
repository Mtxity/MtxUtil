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
    private List<Integer> thisRideUp;
    private List<Integer> nextRideUp;
    private List<Integer> thisRideDown;
    private List<Integer> nextRideDown;
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

        // If elevator is standing still and has a queue, go in that direction
        if (this.direction == 0) {
            if (!this.thisRideUp.isEmpty()) {
                this.direction = 1;
            } else if (!this.thisRideDown.isEmpty()) {
                this.direction = -1;
            }
        }

        int floorDirection = (startingFloor < destinationFloor) ? 1 : -1;

        // Elevator going up
        if (this.direction == 1) {
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

    public boolean moveToNextFloor() {
        boolean moved = false;

        // If elevator is standing still and has a queue, go in that direction
        if (this.direction == 0) {
            if (!this.thisRideUp.isEmpty()) {
                this.direction = 1;
            } else if (!this.thisRideDown.isEmpty()) {
                this.direction = -1;
            }
            // If there is no queue, do nothing
            else {
                return false;
            }
        }

        // Elevator going up
        if (this.direction == 1) {
            if (!this.thisRideUp.isEmpty()) {
                this.currentFloor = this.thisRideUp.remove(0);
                moved = true;
            }

            // Last floor in thisRideUp
            if (this.thisRideUp.isEmpty()) {
                this.thisRideUp = this.nextRideUp;
                // Only go down if there's a queue going down
                this.direction = this.thisRideDown.isEmpty() ? 0 : -1;
            }
        }
        // Elevator going down
        else if (this.direction == -1) {
            if (!this.thisRideDown.isEmpty()) {
                this.currentFloor = this.thisRideDown.remove(0);
                moved = true;
            }

            // Last floor in thisRideDown
            if (this.thisRideDown.isEmpty()) {
                this.thisRideDown = this.nextRideDown;
                // Only go up if there's a queue going up
                this.direction = this.thisRideUp.isEmpty() ? 0 : 1;
            }
        }

        return moved;
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
