package com.ben.dao;

import com.ben.AppOutput;
import com.ben.MetrolinkDao;
import com.ben.Stop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 2/9/2016.
 */
public class SqliteJDBCDao implements MetrolinkDao {

    public SqliteJDBCDao(AppOutput appOutput) {
        this.appOutput = appOutput;
    }

    public static final String JDBC_SQLITE_METROLINK_DB = "jdbc:sqlite:metrolink.db";
    public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    private AppOutput appOutput;
    private List<String> stopNames = null;

    private static final String SELECT_ALL_METROLINK_STOP_NAMES =
            "select distinct stop_name from stops " +
                    "where stop_name like '%METROLINK STATION%' group by stop_name;";
    private static final String SELECT_METROLINK_STOP_COUNT =
            "select count(stop_name) as count from stops where stop_name like '%METROLINK STATION%';";
    private static final String SELECT_ARRIVAL_TIMES_AT_METROLINK_STATION =
            "select arrival_time from stops s " +
                    "inner join stop_times st on s.stop_id = st.stop_id " +
                    "where stop_name = \"SHREWSBURY METROLINK STATION\" " +
                    "order by arrival_time;";


    public List<String> getAllStopNames() {
        appOutput.print("Fetching metrolink stations...");
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_ALL_METROLINK_STOP_NAMES);
            ResultSet resultSet = preparedStatement.executeQuery();
            stopNames = new ArrayList<>();
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
        // should return 36;
        return count;
    }

    public List<String> getArrivalTimes() {
        appOutput.print("Fetching arrival times at station...");
        List<String> arrivalTimes;
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_ARRIVAL_TIMES_AT_METROLINK_STATION);
            ResultSet resultSet = preparedStatement.executeQuery();
            arrivalTimes = new ArrayList<>();
            while (resultSet.next()) {
                arrivalTimes.add(resultSet.getString("arrival_time"));
            }
            return arrivalTimes;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
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
