package com.ben;

import com.ben.models.StopArrival;
import com.ben.models.StopName;
import com.ben.util.TimeAdapter;
import com.ben.util.TimeCalc;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Created by ben on 2/11/2016.
 */

@Component
public class Director implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private ApplicationContext context;

    @Autowired
    private AppOutput output;
    @Autowired
    @Qualifier("jdbc")
    private MetrolinkDao dao;
    private List<StopName> stops;

    private void initStops() {
        if (stops == null) {
            stops = dao.getAllStopNames();
        }
    }

    public void showStops() {
        initStops();
        for (int i = 0; i < stops.size(); ++i) {
            output.print("Stop Number " + i + ": " + stops.get(i).getStopName());
        }
        output.print("Please select your stop by number listed above:");
    }

    public int getStopNumber() throws IOException {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private DayOfWeek getDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek();
    }

    public void showNextArrival(int stopNumber) throws IOException {
        initStops();
        TimeCalc timeCalc = (TimeCalc) context.getBean("timeCalc");

        String currentTime = timeCalc.getCurrentTime();
        output.print("The current time is " + currentTime);

        List<Integer> arrivalSeconds = getArrivalSeconds(stopNumber);
        int now = timeCalc.getSeconds();
        int nearestArrival = timeCalc.findNearestTime(arrivalSeconds, now);
        if (nearestArrival == -1) {
            output.print("You missed the last train of the day!");
        }
        int minutes = timeCalc.getMinutesFromCurrentTime(nearestArrival);
        if (minutes == 0) {
            output.print("The next train should be arriving right now!");
        } else {
            output.print("The next train is arriving in " + minutes + " minutes.");
        }
    }

    private List<Integer> getArrivalSeconds(int stopNumber) throws IOException {
        String stationName;
        try {
            stationName = stops.get(stopNumber).getStopName();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Station number given is out of range.");
        }
        List<StopArrival> arrivals = dao.getArrivalTimes(stationName, getDayOfWeek());

        List<Integer> arrivalSeconds = null;
        for (StopArrival arrivalTime : arrivals) {
            Integer seconds = TimeAdapter.stringTimeToSeconds(
                    arrivalTime.getArrivalTime());
            arrivalSeconds.add(seconds);
        }
        if(arrivalSeconds == null){
            throw new IllegalStateException("arrivalSeconds was not filled.");
        }
        return arrivalSeconds;
    }


}
