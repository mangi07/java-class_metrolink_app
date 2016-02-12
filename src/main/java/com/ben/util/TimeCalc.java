package com.ben.util;

import com.ben.AppOutput;

import java.time.LocalTime;
import java.util.List;

/**
 * Deals with time in current day expressed as seconds from midnight,
 * Created by ben on 2/11/2016.
 */
public class TimeCalc {

    public TimeCalc() {
        output = new ScreenOutput();
    }

    AppOutput output;

    public int getMinutesFromCurrentTime(Integer seconds) {

        int nowSeconds = LocalTime.now().toSecondOfDay();
        int secondsDiff = seconds - nowSeconds;
        return secondsDiff / 60;

    }

    /**
     * Modifiied from http://algs4.cs.princeton.edu/11model/BinarySearch.java.html
     *
     * @return Tries to find now in arrivals.  If not found, returns neearest time forward.
     * If now is greater than any of the arrivals, returns the greatest arrival time.
     */
    public int findNearestTime(List<Integer> arrivals, int now) {

        int lo = 0;
        int hi = arrivals.size() - 1;
        int mid = lo + (hi - lo) / 2;
        int arrival = arrivals.get(mid);
        while (lo <= hi) {
            // now is in a[lo..hi] or not present.
            mid = lo + (hi - lo) / 2;
            arrival = arrivals.get(mid);
            if (now < arrival) hi = mid - 1;
            else if (now > arrival) lo = mid + 1;
            else return arrival;
        }
        return arrival;
    }

    public String getCurrentTime() {
        return java.time.LocalDateTime.now().toString();
    }

}
