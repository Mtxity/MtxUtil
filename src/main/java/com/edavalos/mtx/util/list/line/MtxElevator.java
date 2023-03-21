package com.edavalos.mtx.util.list.line;

import java.util.ArrayList;
import java.util.Collections;
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
    private final List<Integer> floorQueue;
    private final Queue<Integer> currentDirectionEnRoute;
    private final Queue<Integer> oppositeDirectionEnRoute;
    private final Queue<Integer> currentDirectionBacklog;
    private final Queue<Integer> oppositeDirectionBacklog;
    // @TODO: Make this an enum
    private int direction;
    private int currentFloor;
    private C contents;

    public MtxElevator(int floors) {
        if (floors < 0) {
            throw new IndexOutOfBoundsException("Negative numbers are not allowed: " + floors);
        }

        this.floors = floors;
        this.floorQueue = new ArrayList<>();
        this.currentDirectionEnRoute = new LinkedList<>();
        this.oppositeDirectionEnRoute = new LinkedList<>();
        this.currentDirectionBacklog = new LinkedList<>();
        this.oppositeDirectionBacklog = new LinkedList<>();
        this.direction = 0;
        this.currentFloor = 0;
        this.contents = null;
    }

    public void queueFloor(int startingFloor, int destinationFloor) {
        if (startingFloor < 0 || destinationFloor < 0) {
            throw new IndexOutOfBoundsException("Negative numbers are not allowed: " +
                                                startingFloor + ", " + destinationFloor);
        }

        if (startingFloor == destinationFloor) {
            // Already at destination
            return;
        }

        int floorDirection = (startingFloor < destinationFloor) ? 1 : -1;

        // Going up
        if (this.direction == 1) {
            // Going current direction
            if (floorDirection == 1) {
                // En route
                if (this.currentFloor < destinationFloor) {
                    this.currentDirectionEnRoute.add(destinationFloor);
                }
                // Backtracking
                else {
                    this.currentDirectionBacklog.add(destinationFloor);
                }
            }
            // Going opposite direction
            else {
                // En route

            }
        }
    }

    public void moveToNextFloor() {
//        // Already there
//        if (this.upwardsFloorQueue.contains(this.currentFloor)) {
//            this.upwardsFloorQueue.remove(this.currentFloor);
//        }
//        if (this.downwardsFloorQueue.contains(this.currentFloor)) {
//            this.downwardsFloorQueue.remove(this.currentFloor);
//        }
//
//        // Going up
//        if (this.direction > 0) {
//            Collections.sort(this.upwardsFloorQueue);
//            for (int i = 0; i < this.upwardsFloorQueue.size(); i++) {
//                // If floor is lower than current, ignore
//                if (this.upwardsFloorQueue.get(i) < this.currentFloor) {
//                    continue;
//                }
//                // Move up to next floor and remove it from queue
//                this.currentFloor = this.upwardsFloorQueue.remove(i);
//                break;
//            }
//            // If no floors higher than current are found, switch direction
//            if (this.upwardsFloorQueue.isEmpty()) {
//                if (this.downwardsFloorQueue.isEmpty()) {
//                    this.direction = 0;
//                } else {
//                    this.direction = -1;
//                }
//            }
//            return;
//        }
//
//        // Going down
//        if (this.direction < 0) {
//            Collections.sort(this.downwardsFloorQueue);
//            // If all floors are higher than current, switch directions
//            if (this.downwardsFloorQueue.get(0) > this.currentFloor) {
//                this.direction = 1;
//            }
//            // Move down to next floor and remove it from queue
//            this.currentFloor = this.downwardsFloorQueue.remove(this.downwardsFloorQueue.size() - 1);
//
//            // If no floors lower than current are found, switch direction
//            if (this.downwardsFloorQueue.isEmpty()) {
//                if (this.upwardsFloorQueue.isEmpty()) {
//                    this.direction = 0;
//                } else {
//                    this.direction = 1;
//                    this.currentFloor = this.upwardsFloorQueue.get(0);
//                }
//            }
//            return;
//        }
//
//        // Standing still
//        if (!this.upwardsFloorQueue.isEmpty()) {
//            this.direction = 1;
//            this.moveToNextFloor();
//        } else if (!this.downwardsFloorQueue.isEmpty()) {
//            this.direction = -1;
//            this.moveToNextFloor();
//        }
//        // Going up
//        if (this.direction == 1) {
//            Collections.sort(this.upwardsFloorQueue);
//            for (int i = 0; i < this.upwardsFloorQueue.size(); i++) {
//                if (this.upwardsFloorQueue.get(i) >= this.currentFloor) {
//                    this.currentFloor = this.upwardsFloorQueue.remove(i);
//                    return;
//                }
//            }
//        }
//        // Going down
//        else if (this.direction == -1) {
//            Collections.sort(this.downwardsFloorQueue);
//            Collections.reverse(this.downwardsFloorQueue);
//            for (int i = this.downwardsFloorQueue.size() - 1; i <= 0; i--) {
//                if (this.downwardsFloorQueue.get(i) <= this.currentFloor) {
//                    this.currentFloor = this.downwardsFloorQueue.remove(i);
//                    return;
//                }
//            }
//        }
//        // Standing still
//        else {
//            if (!this.upwardsFloorQueue.isEmpty()) {
//                this.direction = 1;
//                this.moveToNextFloor();
//            } else if (!this.downwardsFloorQueue.isEmpty()) {
//                this.direction = -1;
//                this.moveToNextFloor();
//            }
//        }

    }

    public C interactWithContents(MtxElevatorContentsManager<C> contentsManager) {
        return contentsManager.modifyContents(this.contents);
    }

    public int queueSize() {
        return this.upwardsFloorQueue.size() + this.downwardsFloorQueue.size();
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
        return this.upwardsFloorQueue;
    }

    protected List<Integer> getDownwardsFloorQueue() {
        return this.downwardsFloorQueue;
    }

    @Override
    public String toString() {
        return "";
    }
}
