package com.research.MessagingPattern.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mcalvo on 17/10/15.
 */
public class Statistics {

    private long start_time;
    private long end_time;
    private AtomicLong totalElements;
    private AtomicLong actualCount;

    public Statistics(long start_time, long totalElements){
        this.start_time = start_time;
        this.totalElements = new AtomicLong(totalElements);
        this.actualCount = new AtomicLong(0);
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getTotalElements() {
        return totalElements.get();
    }

    public void setTotalElements(long totalElements) {
        this.totalElements.set(totalElements);
    }

    public AtomicLong getActualCount() {
        return actualCount;
    }

    public void setActualCount(AtomicLong actualCount) {
        this.actualCount = actualCount;
    }

    public double totalTimeSeconds(){

        long totalTimeMiliseconds = end_time - start_time;

        double totalTimeSeconds = (double)totalTimeMiliseconds / 1000.0;

        return totalTimeSeconds;
    }
}
