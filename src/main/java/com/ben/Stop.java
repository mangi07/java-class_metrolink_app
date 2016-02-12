package com.ben;

import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
public class Stop {
    private String stopName;
    private List<Integer> arrivalTimes; // in seconds from midnight

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setArrivalTimes(List<Integer> arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }

}
