package models;

public class TestTimer {
    private long startTime;
    private long stopTime;

    public void start() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
    }

    public void stop() {
        if (stopTime == 0 && startTime != 0) {
            stopTime = System.currentTimeMillis();
        }
    }

    public long getElapsedTimeSec() {
        return (stopTime - startTime) / 1000;
    }

    public float getElapsedTimeMin() {
        return (float)getElapsedTimeSec() / 60;
    }
}
