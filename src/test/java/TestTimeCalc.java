import com.ben.util.TimeCalc;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by ben on 2/11/2016.
 */
public class TestTimeCalc {


    TimeCalc timeCalc;
    ApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");

    @Before
    public void setUp() {
        timeCalc = (TimeCalc) context.getBean("timeCalc");
    }


    @Test
    public void shouldGetTimeDiffInMinutes() {
        int seconds = 24 * 60 * 60;
        int minutesUntilTomorrow = timeCalc.getMinutesFromCurrentTime(seconds);
        System.out.println("minutes until tomorrow: " + minutesUntilTomorrow);
    }

    @Test
    public void shouldShowCurrentTime() {
        String now = timeCalc.getCurrentTime();
        System.out.println("Current Time: " + now);
    }

    @Test
    public void findNearestTimeShouldReturnOne() {
        List<Integer> arrivals = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 10));
        int found = timeCalc.findNearestTime(arrivals, 0);
        assertEquals(1, found);
        found = timeCalc.findNearestTime(arrivals, 1);
        assertEquals(1, found);
    }

    @Test
    public void findNearestTimeShouldReturnFive() {
        List<Integer> arrivals = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 10));
        int found = timeCalc.findNearestTime(arrivals, 4);
        assertEquals(5, found);
    }

    @Test
    public void findNearestTimeShouldReturnTen() {
        List<Integer> arrivals = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 10));
        int found = timeCalc.findNearestTime(arrivals, 6);
        assertEquals(10, found);

        found = timeCalc.findNearestTime(arrivals, 8);
        assertEquals(10, found);
    }

    @Test
    public void findNearestTimeShouldReturnNegativeOne() {
        List<Integer> arrivals = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 10));
        int found = timeCalc.findNearestTime(arrivals, 11);
        assertEquals(-1, found);
    }


}
