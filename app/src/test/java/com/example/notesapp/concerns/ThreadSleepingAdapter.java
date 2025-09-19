package com.example.notesapp.concerns;

public class ThreadSleepingAdapter {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
