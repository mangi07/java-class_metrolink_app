import com.ben.MetrolinkDao;
import com.ben.models.StopName;
import com.ben.util.ScreenOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by ben on 2/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/application-context.xml"})
public class TestSqliteJDBCDao {

    private ApplicationContext context;
    @Autowired
    @Qualifier("jdbc")
    MetrolinkDao dao;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
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
        List<StopName> stops = dao.getAllStopNames();
        int stopsCount = dao.getStopsCount();
        assertEquals(stops.size(), stopsCount);
        for (StopName stop : stops) {
            if (!stop.getStopName().contains("METROLINK STATION"))
                fail();
        }
    }

    @Test
    public void shouldGetArrivalTimesAtStation() {
        dao.getArrivalTimes("UMSL NORTH METROLINK STATION", DayOfWeek.FRIDAY);
    }


}
