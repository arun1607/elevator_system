package com.app.learning;


import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ElevatorTest {

    Elevator elevator = new Elevator(10, "1");

    private ElevatorEventCallback elevatorEventCallback = mock(ElevatorEventCallback.class);

    @Before
    public void setup() {
        elevator = new Elevator(10, "1", elevatorEventCallback);
    }

    @Test
    public void serveRequest() {
        elevator.call(new ElevatorRequest(5, 1));
        elevator.call(new ElevatorRequest(3, 2));
        elevator.call(new ElevatorRequest(7, 4));
        elevator.run();
        verify(elevatorEventCallback, times(6)).crossedFloor();
    }
}