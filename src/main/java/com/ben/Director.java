package com.ben;

import com.ben.models.Stop;
import com.ben.models.StopNames;
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
    private List<StopNames> stops;
    @Autowired
    private Stop stop;


    private void initStops() {
        if (stops == null){
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

    public void createStop(int stopNumber) throws IOException {
        initStops();
        String stationName;
        try {
            stationName = stops.get(stopNumber).getStopName();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Station number given is out of range.");
        }
        stop.setStopName(stationName);
        List<Integer> arrivals = dao.getArrivalTimes(stationName, getDayOfWeek());
        stop.setArrivalTimes(arrivals);
    }

    private DayOfWeek getDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek();
    }

    public void showNextArrival() {
        TimeCalc timeCalc = (TimeCalc) context.getBean("timeCalc");

        String currentTime = timeCalc.getCurrentTime();
        output.print("The current time is " + currentTime);

        List<Integer> arrivals = stop.getArrivalTimes();

        int now = timeCalc.getSeconds();
        int nearestArrival = timeCalc.findNearestTime(arrivals, now);
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


}
