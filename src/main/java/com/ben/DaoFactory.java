package com.ben;

import com.ben.dao.DaoType;
import com.ben.dao.SqliteJDBCDao;

/**
 * Created by ben on 2/13/2016.
 */
public class DaoFactory {
    public static MetrolinkDao createDao(
            DaoType daoType, AppOutput output) {
        switch (daoType){
            case SQLITE_JDBC_DAO:
                return new SqliteJDBCDao(output);
            default:
                throw new IllegalStateException("Failed to create dao.");
        }
    }
}
