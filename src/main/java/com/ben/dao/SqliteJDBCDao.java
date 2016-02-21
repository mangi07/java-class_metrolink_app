package com.ben.dao;

import com.ben.AppOutput;
import com.ben.MetrolinkDao;
import com.ben.models.StopArrival;
import com.ben.models.StopName;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.DayOfWeek;

import org.hibernate.SessionFactory;

/**
 * Created by ben on 2/9/2016.
 */
@Repository(value = "jdbc")
public class SqliteJDBCDao implements MetrolinkDao {

    @Autowired
    @Qualifier("screen")
    private AppOutput appOutput;
    @Autowired
    private SessionFactory sessionFactoryBean;


   public List<StopName> getAllStopNames() {
        sessionFactoryBean.getCurrentSession().beginTransaction();
        Criteria criteria = sessionFactoryBean.getCurrentSession().createCriteria(StopName.class);
        criteria.add(Restrictions.like("stopName", "%METROLINK STATION%"));
        List list = criteria.list();
        sessionFactoryBean.getCurrentSession().getTransaction().commit();
        return list;

    }

    public int getStopsCount() {
        appOutput.print("Fetching metrolink stations count...");

        sessionFactoryBean.getCurrentSession().beginTransaction();
        Criteria criteria = sessionFactoryBean.getCurrentSession().createCriteria(StopName.class);
        criteria.add(Restrictions.like("stopName", "%METROLINK STATION%"));
        Number count = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
        List list = criteria.list();
        sessionFactoryBean.getCurrentSession().getTransaction().commit();
        return count.intValue();

    }

    public List<StopArrival> getArrivalTimes(String stationName, DayOfWeek day) {
        appOutput.print("Fetching arrival times at station...");

        sessionFactoryBean.getCurrentSession().beginTransaction();
        Criteria criteria = sessionFactoryBean.getCurrentSession().createCriteria(StopArrival.class);
        criteria.add(Restrictions.eq("stopName", stationName));
        criteria.add(Restrictions.eq("serviceId", getServiceIDFromDay(day)));
        List list = criteria.list();
        sessionFactoryBean.getCurrentSession().getTransaction().commit();
        return list;
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

    public void setAppOutput(AppOutput appOutput) {
        this.appOutput = appOutput;
    }
}
