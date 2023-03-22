package com.edavalos.mtx.util.list.line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

        int floorDirection = (startingFloor < destinationFloor) ? 1 : -1;

        // If elevator is standing still and has a queue, go in that direction
        if (this.direction == 0) {
            if (floorDirection == 1) {
                this.direction = 1;
            } else {
                this.direction = -1;
            }
        }

        // Elevator going up
        if (this.direction == 1) {
            // Going up
            if (floorDirection == 1) {
                // Both stops above
                if (destinationFloor > this.currentFloor && startingFloor >= this.currentFloor) {
                    this.addIfUnique(this.thisRideUp, startingFloor, true);
                    this.addIfUnique(this.thisRideUp, destinationFloor, false);
                }
                // First stop below
                else {
                    this.addIfUnique(this.nextRideUp, startingFloor, true);
                    this.addIfUnique(this.nextRideUp, destinationFloor, false);
                }
            }
            // Going down
            else {
                this.addIfUnique(this.thisRideDown, startingFloor, true);
                this.addIfUnique(this.thisRideDown, destinationFloor, false);
            }
        }
        // Elevator going down
        else {
            // Going down
            if (floorDirection == -1) {
                // Both stops below
                if (destinationFloor < this.currentFloor && startingFloor <= this.currentFloor) {
                    this.addIfUnique(this.thisRideDown, startingFloor, true);
                    this.addIfUnique(this.thisRideDown, destinationFloor, false);
                }
                // First stop above
                else {
                    this.addIfUnique(this.nextRideDown, startingFloor, true);
                    this.addIfUnique(this.nextRideDown, destinationFloor, false);
                }
            }
            // Going up
            else {
                this.addIfUnique(this.thisRideUp, startingFloor, true);
                this.addIfUnique(this.thisRideUp, destinationFloor, false);
            }
        }
        Collections.sort(this.thisRideUp);
        Collections.sort(this.thisRideDown);
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
                Collections.sort(this.thisRideUp);
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
                Collections.sort(this.thisRideDown);
                this.currentFloor = this.thisRideDown.remove(this.thisRideDown.size() - 1);
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

    private void addIfUnique(List<Integer> queue, int i, boolean isStartFloor) {
        if (!queue.contains(i) && !(isStartFloor && this.currentFloor != i)) {
            queue.add(i);
        }
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
        List<Integer> queue = new ArrayList<>();
        if (this.direction == 1 || this.direction == 0) {
            queue.addAll(this.thisRideUp);
            queue.addAll(this.thisRideDown);
            queue.addAll(this.nextRideUp);
            queue.addAll(this.nextRideDown);
        } else {
            queue.addAll(this.thisRideDown);
            queue.addAll(this.thisRideUp);
            queue.addAll(this.nextRideDown);
            queue.addAll(this.nextRideUp);
        }
        return Arrays.toString(queue.toArray());
    }
}
