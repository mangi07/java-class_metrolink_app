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

    private static final String SELECT_ALL_METROLINK_STOP_NAMES =
            "select distinct stop_name from stops " +
                    "where stop_name like '%METROLINK STATION%' group by stop_name;";
    private static final String SELECT_METROLINK_STOP_COUNT =
            "select count(stop_name) from stops where stop_name like '%METROLINK STATION%';";
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
            List<String> stops = new ArrayList<>();
            while (resultSet.next()) {
                stops.add(resultSet.getString("stop_name"));
            }
            return stops;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stops");
        }
    }

    public int getStopsCount() {
        return 36;
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
