package com.ben;

import com.ben.models.StopArrival;
import com.ben.models.StopName;

import java.time.DayOfWeek;

import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
public interface MetrolinkDao {
    List<StopName> getAllStopNames();

    int getStopsCount();

    List<StopArrival> getArrivalTimes(String stationName, DayOfWeek day);

    void setAppOutput(AppOutput appOutput);
}
