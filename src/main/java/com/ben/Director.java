package com.ben;

import com.ben.dao.SqliteJDBCDao;
import com.ben.util.DayOfWeek;
import com.ben.util.ScreenOutput;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ben on 2/11/2016.
 */
public class Director {

    public Director() {
        output = new ScreenOutput();
        dao = new SqliteJDBCDao(new ScreenOutput());
        stops = dao.getAllStopNames();
        stop = new Stop();
    }

    private AppOutput output;
    private MetrolinkDao dao;
    private List<String> stops;
    private Stop stop;
    private int stationNumber;


    public void getStation() throws IOException {
        output.print("Please enter station number: ");
        Scanner in = new Scanner(System.in);
        stationNumber = in.nextInt();
        createStop();
    }

    private void createStop() throws IOException {
        String stationName;
        try {
            stationName = stops.get(stationNumber);
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Station number given is out of range.");
        }
        stop.setStopName(stationName);
        List<Integer> arrivals = dao.getArrivalTimes(stationName, DayOfWeek.MON);
        stop.setArrivalTimes(arrivals);
    }

}
