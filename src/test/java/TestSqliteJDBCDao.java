import com.ben.MetrolinkDao;
import com.ben.models.StopNames;
import com.ben.util.ScreenOutput;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by ben on 2/10/2016.
 */
public class TestSqliteJDBCDao {

    @Autowired
    MetrolinkDao dao;

    ApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");

    @Before
    public void setUp() {
        dao = (MetrolinkDao) context.getBean("dao");
    }

    @Test
    public void shouldAccessDatabase() {
        dao.getAllStopNames();
    }

    @Test
    public void shouldSetAppOutput() {
        dao.setAppOutput(new ScreenOutput());
    }

    @Test
    public void shouldGetAllStopNames() {
        List<StopNames> stops = dao.getAllStopNames();
        int stopsCount = dao.getStopsCount();
        assertEquals(stops.size(), stopsCount);
        for (StopNames stop : stops) {
            if (!stop.getStopName().contains("METROLINK STATION"))
                fail();
        }
    }

    @Test
    public void shouldGetArrivalTimesAtStation() {
        dao.getArrivalTimes("UMSL NORTH METROLINK STATION", DayOfWeek.FRIDAY);
    }

}
