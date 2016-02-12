package com.ben.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Deals with time formatted as "hh:mm:ss", indicated as hms,
 * Created by ben on 2/11/2016.
 */
public class TimeCalc {

    public long showMinutesFromCurrentTime(String hms) {

        String currentTime = getCurrentTime();
        int currentSeconds = getSeconds(currentTime);
        int futureSeconds = getSeconds(hms);


        return 0;

    }

    public void findNearestTimeToCurrent(List<String> hms) {

    }

    private String getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(currentDate);
    }

    private int getSeconds(String hms) {
        String[] timeParts = hms.split(":");

        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

        int timeInSeconds = hours * (60*60) + minutes * 60 + seconds;

        return  timeInSeconds;
    }
}
