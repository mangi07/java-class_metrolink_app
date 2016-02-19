package com.ben.dao;

import com.ben.AppOutput;
import com.ben.MetrolinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;

/**
 * Created by ben on 2/9/2016.
 */

@Component(value = "jdbc")
public class SqliteJDBCDao implements MetrolinkDao {

    public static final String JDBC_SQLITE_METROLINK_DB = "jdbc:sqlite:metrolink.db";
    public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    @Autowired
    @Qualifier("screen")
    private AppOutput appOutput;

    private static final String SELECT_ALL_METROLINK_STOP_NAMES =
            "select distinct stop_name from stops " +
                    "where stop_name like '%METROLINK STATION%' group by stop_name;";
    private static final String SELECT_METROLINK_STOP_COUNT =
            "select count(stop_name) as count from stops where stop_name like '%METROLINK STATION%';";
    private static final String SELECT_ARRIVAL_TIMES_AT_METROLINK_STATION =
            "select strftime('%s',arrival_time) - strftime('%s','00:00:00') as arrivals " +
                    "from metrolink_stops " +
                    "where stop_name = ? " +
                    "and service_id =  ? " +
                    "order by arrivals;";


    public List<String> getAllStopNames() {
        appOutput.print("Fetching metrolink stations...");
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_ALL_METROLINK_STOP_NAMES);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> stopNames = new ArrayList<>();
            while (resultSet.next()) {
                stopNames.add(resultSet.getString("stop_name"));
            }
            return stopNames;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
        }
    }

    public int getStopsCount() {
        appOutput.print("Fetching metrolink stations count...");
        int count;
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_METROLINK_STOP_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            count = resultSet.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
        }
        // should return 36
        return count;
    }

    public List<Integer> getArrivalTimes(String stationName, DayOfWeek day) {
        appOutput.print("Fetching arrival times at station...");
        List<Integer> arrivalTimes;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_ARRIVAL_TIMES_AT_METROLINK_STATION);
            preparedStatement.setString(1, stationName);
            preparedStatement.setString(2, getServiceIDFromDay(day));
            ResultSet resultSet = preparedStatement.executeQuery();
            arrivalTimes = new ArrayList<Integer>();
            while (resultSet.next()) {
                String arrivalString = resultSet.getString("arrivals");
                if (arrivalString != null) {
                    Integer arrivalInt = Integer.parseInt(arrivalString);
                    arrivalTimes.add(arrivalInt);
                }
            }
            return arrivalTimes;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
        }
    }

    private String getServiceIDFromDay(DayOfWeek day) {
        switch (day) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                return "1_merged_2038871";
            case SATURDAY:
                return "2_merged_2038873";
            case SUNDAY:
                return "3_merged_2038872";
            default:
                throw new IllegalStateException("Unrecognized case: " + day);
        }
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(ORG_SQLITE_JDBC);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find class for loading the database", e);
        }

        return DriverManager.getConnection(JDBC_SQLITE_METROLINK_DB);
    }


    public void setAppOutput(AppOutput appOutput) {
        this.appOutput = appOutput;
    }
}
