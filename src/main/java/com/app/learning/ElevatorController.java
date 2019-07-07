package com.app.learning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

import static com.app.learning.Constant.EXECUTOR_SERVICE;
import static java.lang.Math.abs;

public class ElevatorController {
    private int numberOfLifts;
    private final int numberOfFloors;
    private List<Elevator> elevatorList;
    private Queue<ElevatorRequest> elevatorRequests = new PriorityBlockingQueue<>();
    private ElevatorEventCallback elevatorEventCallback = new QueueProcessedHandler();

    public ElevatorController(int numberOfLifts, int numberOfFloors) {
        this.numberOfLifts = numberOfLifts;
        this.numberOfFloors = numberOfFloors;
    }

    public void start() {
        elevatorList = new ArrayList<>();
        for (int i = 1; i <= numberOfLifts; i++) {
            Elevator elevator = new Elevator(numberOfFloors, String.valueOf(i), elevatorEventCallback);
            EXECUTOR_SERVICE.submit(elevator);
            elevatorList.add(elevator);
        }
    }

    public void callElevator(final ElevatorRequest elevatorRequest) {
        System.out.println(elevatorRequest.getSourceFloor() + " " + elevatorRequest.getDestinationFloor());
        boolean processed = processElevatorRequest(elevatorRequest);
        if (!processed) {
            elevatorRequests.add(elevatorRequest);
        }
    }

    private boolean processElevatorRequest(ElevatorRequest elevatorRequest) {
        boolean assigned = false;
        int requestSourceFloor = elevatorRequest.getSourceFloor();

        Comparator<Elevator> elevatorComparator = Comparator.comparingInt(elevator -> abs(requestSourceFloor - elevator.getCurrentFloor()));

        List<Elevator> sortedByDistance = elevatorList.stream().sorted(elevatorComparator).collect(Collectors.toList());

        // Otherwise choosing elevator which is closest and moving toward request floor or stopped.
        for (Elevator elevator : sortedByDistance) {
            if (
                    (elevator.isTravellingTowardFloor(requestSourceFloor)
                            && elevator.getDirection() == elevatorRequest.getDirection())
                            || elevator.getStatus() == Status.STOPPED
            ) {
                elevator.call(elevatorRequest);
                assigned = true;
                break;
            }

        }
        return assigned;
    }

    private void assignRequestToElevator() {
        if (!elevatorRequests.isEmpty()) {
            ElevatorRequest elevatorRequest = elevatorRequests.peek();
            boolean processed = processElevatorRequest(elevatorRequest);
            if (processed) {
                elevatorRequests.remove();
            }
        }
    }

    private class QueueProcessedHandler implements ElevatorEventCallback {
        @Override
        public void queueProcessed() {
            assignRequestToElevator();
        }

        @Override
        public void crossedFloor() {
            assignRequestToElevator();
        }
    }

}
