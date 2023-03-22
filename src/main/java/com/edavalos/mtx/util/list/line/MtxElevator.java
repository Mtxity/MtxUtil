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

    public enum MtxElevatorDirection{
        UP,
        DOWN,
        STILL
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

    private final int floors;
    private List<Integer> thisRideUp;
    private List<Integer> nextRideUp;
    private List<Integer> thisRideDown;
    private List<Integer> nextRideDown;
    // @TODO: Make this an enum
    private MtxElevatorDirection direction;
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
        this.direction = MtxElevatorDirection.STILL;
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
        if (this.direction == MtxElevatorDirection.STILL) {
            if (floorDirection == 1) {
                this.direction = MtxElevatorDirection.UP;
            } else {
                this.direction = MtxElevatorDirection.DOWN;
            }
        }

        // Elevator going up
        if (this.direction == MtxElevatorDirection.UP) {
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
        if (this.direction == MtxElevatorDirection.STILL) {
            if (!this.thisRideUp.isEmpty()) {
                this.direction = MtxElevatorDirection.UP;
            } else if (!this.thisRideDown.isEmpty()) {
                this.direction = MtxElevatorDirection.DOWN;
            }
            // If there is no queue, do nothing
            else {
                return false;
            }
        }

        // Elevator going up
        if (this.direction == MtxElevatorDirection.UP) {
            if (!this.thisRideUp.isEmpty()) {
                Collections.sort(this.thisRideUp);
                this.currentFloor = this.thisRideUp.remove(0);
                moved = true;
            }

            // Last floor in thisRideUp
            if (this.thisRideUp.isEmpty()) {
                this.thisRideUp = this.nextRideUp;
                // Only go down if there's a queue going down
                this.direction = this.thisRideDown.isEmpty() ? MtxElevatorDirection.STILL : MtxElevatorDirection.DOWN;
            }
        }
        // Elevator going down
        else if (this.direction == MtxElevatorDirection.DOWN) {
            if (!this.thisRideDown.isEmpty()) {
                Collections.sort(this.thisRideDown);
                this.currentFloor = this.thisRideDown.remove(this.thisRideDown.size() - 1);
                moved = true;
            }

            // Last floor in thisRideDown
            if (this.thisRideDown.isEmpty()) {
                this.thisRideDown = this.nextRideDown;
                // Only go up if there's a queue going up
                this.direction = this.thisRideUp.isEmpty() ? MtxElevatorDirection.STILL : MtxElevatorDirection.UP;
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

    public MtxElevatorDirection getDirection() {
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

    public void setDirection(MtxElevatorDirection direction) {
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
        if (this.direction == MtxElevatorDirection.UP || this.direction == MtxElevatorDirection.STILL) {
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
