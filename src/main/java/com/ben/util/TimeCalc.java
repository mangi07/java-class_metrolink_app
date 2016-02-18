package com.ben.util;

import java.time.LocalTime;
import java.util.List;

/**
 * Deals with time in current day expressed as seconds from midnight,
 * Created by ben on 2/11/2016.
 */
public class TimeCalc {

    public int getMinutesFromCurrentTime(Integer seconds) {

        int nowSeconds = getSeconds();
        int secondsDiff = seconds - nowSeconds;
        return secondsDiff / 60;

    }

    /**
     * Modified from http://algs4.cs.princeton.edu/11model/BinarySearch.java.html
     *
     * @param arrivals An ordered list of arrival times given in seconds from midnight.
     * @param now      The current local time given in seconds from midnight.
     * @return Tries to find now in arrivals.  If not found, returns nearest time forward.
     * If now is greater than any of the arrivals, returns -1.
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
            else if (now == arrival) return arrival;
        }
        if (arrival < now && mid < arrivals.size()-1) {
            arrival = arrivals.get(mid+1);
        } else if (now > arrivals.get(arrivals.size()-1)) {
            arrival = -1;
        }
        return arrival;
    }

    public String getCurrentTime() {
        return java.time.LocalDateTime.now().toString();
    }

    public int getSeconds() {
       return LocalTime.now().toSecondOfDay();
    }

}
