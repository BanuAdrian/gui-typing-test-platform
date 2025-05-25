package models;

import exceptions.BadTimerHandlingException;

public class TestTimer {
    private long startTime;
    private long stopTime;

    public void start() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
            System.out.println("timer started");
        } else {
            throw new BadTimerHandlingException("Timer already started!");
        }
    }

    public void stop() {
        if (stopTime == 0 && startTime != 0) {
            stopTime = System.currentTimeMillis();
            System.out.println("timer stopped");
        } else {
            if (startTime == 0) {
                throw new BadTimerHandlingException("You must start the timer first!");
            } else {
                throw new BadTimerHandlingException("Timer already stopped!");
            }
        }
    }

    public long getElapsedTimeSec() {
        return (stopTime - startTime) / 1000;
    }

    public float getElapsedTimeMin() {
        return (float)getElapsedTimeSec() / 60;
    }
}
