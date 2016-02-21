package com.ben.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ben on 2/20/2016.
 */
public class TimeAdapter {
    /** @param time Given in format "hh:mm:ss"
     */
    public static int stringTimeToSeconds(String time) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference = null;
        Date date;
        try {
            reference = dateFormat.parse("00:00:00");
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new IllegalStateException("Unsuccessful time parse.");
        }
        long seconds = (date.getTime() - reference.getTime()) / 1000L;
        return (int) seconds;
    }
}
