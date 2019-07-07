package com.app.learning;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.app.learning.Constant.EXECUTOR_SERVICE;

public class Building {

    private static final Logger LOGGER = Logger.getLogger(Building.class.getName());
    private final int numberOfLifts;
    private final int numberOfFloors;
    private ElevatorController elevatorController;


    public Building(int numberOfLifts, int numberOfFloors) {
        this.numberOfLifts = numberOfLifts;
        this.numberOfFloors = numberOfFloors;
    }

    public void startLifts() {

        elevatorController = new ElevatorController(numberOfLifts, numberOfFloors);

        elevatorController.start();

        EXECUTOR_SERVICE.submit(this::acceptUserInput);
    }

    private void acceptUserInput() {
        try {
            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please provide source floor");

                final int sourceFloorNumber = sc.nextInt();
                System.out.println("Please provide destination floor");
                final int destinationFloorNumber = sc.nextInt();
                ElevatorRequest elevatorRequest = new ElevatorRequest(sourceFloorNumber, destinationFloorNumber);
                elevatorController.callElevator(elevatorRequest);
                try {
                    Thread.sleep(Constant.DELAY_CHECKING_USER_INPUT);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "Elevator request processor interrupted", e);
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.severe("Please provide valid input" + e.getLocalizedMessage());
        }
    }
}
