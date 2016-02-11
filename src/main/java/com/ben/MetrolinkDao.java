package com.ben;

import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
public interface MetrolinkDao {
    List<String> getAllStopNames();

    int getStopsCount();
}
