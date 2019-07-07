package com.app.learning;

import java.io.File;
import java.util.Comparator;
import java.util.Queue;

import static com.app.learning.Constant.*;


public class Elevator implements Runnable {
    private int currentFloor = 0;
    private Door door = Door.CLOSE;
    private Status status = Status.STOPPED;
    private Direction direction = Direction.UP;
    private int maxFloors;
    private String liftNumber;
    private volatile int timer = 0;
    private static final int QUEUE_CAPACITY = 10;
    private ElevatorEventCallback elevatorEventCallback;
    private File outputFile = null;

    public Elevator(int maxFloors, String liftNumber, ElevatorEventCallback elevatorEventCallback) {
        this.maxFloors = maxFloors;
        this.elevatorEventCallback = elevatorEventCallback;
        this.liftNumber = liftNumber;
        outputFile = new File("output_" + liftNumber);
    }

    public Elevator(int maxFloors, String liftNumber) {
        this.maxFloors = maxFloors;
        this.liftNumber = liftNumber;
    }

    private Queue<Integer> upQueue = new NoDuplicatesPriorityBlockingQueue<>(QUEUE_CAPACITY, Integer::compareTo);
    private Queue<Integer> downQueue = new NoDuplicatesPriorityBlockingQueue<>(QUEUE_CAPACITY, Comparator.reverseOrder());
    private Queue<Integer> currentQueue = upQueue;

    public void call(ElevatorRequest elevatorRequest) {
        final int destinationFloor = elevatorRequest.getDestinationFloor();
        final int sourceFloor = elevatorRequest.getSourceFloor();
        final Direction userDirection = elevatorRequest.getDirection();
        if (destinationFloor > maxFloors || destinationFloor < 0) {
            throw new IllegalArgumentException("Requested floor can not greater then max floor or negative");
        }

        switch (userDirection) {
            case UP:
                upQueue.add(sourceFloor);
                upQueue.add(destinationFloor);
                direction = Direction.UP;
                currentQueue = upQueue;
                break;
            case DOWN:
                downQueue.add(sourceFloor);
                downQueue.add(destinationFloor);
                direction = Direction.DOWN;
                currentQueue = downQueue;
                break;
        }
    }


    @Override
    public void run() {
        while (true) {
            while (!currentQueue.isEmpty()) {
                Integer destinationFloor = currentQueue.poll();
                goToFloor(destinationFloor);
            }
            status = Status.STOPPED;
            if (elevatorEventCallback != null) {
                elevatorEventCallback.queueProcessed();
            }
            sleep(DELAY_SERVING_ELEVATOR_REQUEST);
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goToFloor(int destinationFloor) {
        if (currentFloor != destinationFloor) {
            status = Status.MOVING;
            while (currentFloor < destinationFloor) {
                currentFloor++;
                direction = Direction.UP;
                timer++;
                displayStatus();
            }
            while (currentFloor > destinationFloor) {
                currentFloor--;
                direction = Direction.DOWN;
                timer++;
                displayStatus();
            }
            if (elevatorEventCallback != null) {
                elevatorEventCallback.crossedFloor();
            }
            sleep(SINGLE_FLOOR_TRAVEL_TIME_MILLI);
        }
        openDoor();
        closeDoor();
    }


    private void openDoor() {
        door = Door.OPEN;
        timer++;
        displayStatus();
        sleep(DOOR_OPEN_TIME_MILLI);
    }

    private void closeDoor() {
        door = Door.CLOSE;
        timer++;
        displayStatus();
        sleep(DOOR_CLOSE_TIME_MILLI);
    }

    private void displayStatus() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("T=%d\nLIFT %s --> %d(%s) with direction %s", timer, liftNumber, currentFloor, door.toString(), direction.toString());
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isTravellingTowardFloor(int floorNumber) {
        return (floorNumber > currentFloor && direction == Direction.UP) || (floorNumber < currentFloor && direction == Direction.DOWN);
    }
}
