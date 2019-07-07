package com.app.learning;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Constant {

    private Constant() {

    }

    public static final int SINGLE_FLOOR_TRAVEL_TIME_MILLI = 1000;
    public static final int DOOR_OPEN_TIME_MILLI = 1000;
    public static final int DOOR_CLOSE_TIME_MILLI = 1000;
    public static final int DELAY_CHECKING_USER_INPUT = 2000;
    public static final int DELAY_PROCESSING_ELEVATOR_REQUEST = 5000;
    public static final int DELAY_SERVING_ELEVATOR_REQUEST = 0;
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

}
