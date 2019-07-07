package com.app.learning;

import java.util.Objects;

public class ElevatorRequest implements Comparable<ElevatorRequest> {

    private final int sourceFloor;
    private final int destinationFloor;
    private final long requestTime;

    public ElevatorRequest(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        requestTime = System.currentTimeMillis();
    }

    public int getSourceFloor() {
        return sourceFloor;
    }


    public int getDestinationFloor() {
        return destinationFloor;
    }


    public Direction getDirection() {
        return (sourceFloor < destinationFloor) ? Direction.UP : Direction.DOWN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElevatorRequest that = (ElevatorRequest) o;
        return sourceFloor == that.sourceFloor &&
                destinationFloor == that.destinationFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceFloor, destinationFloor);
    }

    @Override
    public int compareTo(ElevatorRequest o) {
        return Long.compare(requestTime, o.requestTime);
    }
}
