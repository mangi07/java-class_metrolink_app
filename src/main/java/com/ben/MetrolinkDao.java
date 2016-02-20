package com.ben;

import com.ben.models.StopNames;

import java.time.DayOfWeek;

import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
public interface MetrolinkDao {
    List<StopNames> getAllStopNames();

    int getStopsCount();

    List<Integer> getArrivalTimes(String stationName, DayOfWeek day);

    void setAppOutput(AppOutput appOutput);
}
