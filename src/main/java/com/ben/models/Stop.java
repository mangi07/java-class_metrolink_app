package com.ben.models;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
// MODIFY WITH HIBERNATE ANNOTATIONS
@Component
public class Stop {
    private String stopName;
    private List<Integer> arrivalTimes; // in seconds from midnight

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setArrivalTimes(List<Integer> arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }

    public String getStopName() {
        return this.stopName;
    }

    public List<Integer> getArrivalTimes() {
        return this.arrivalTimes;
    }
}
