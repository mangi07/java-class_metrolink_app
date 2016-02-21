import com.ben.util.TimeAdapter;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ben on 2/20/2016.
 */
public class TestTimeAdapter {
    @Test
    public void shouldConvertToFiftyNineSeconds() {
        int seconds = TimeAdapter.stringTimeToSeconds("00:00:59");
        assertEquals(59, seconds);
    }

    @Test
    public void shouldConvertToSixtySeconds() {
        int seconds = TimeAdapter.stringTimeToSeconds("00:01:00");
        assertEquals(60, seconds);
    }

    @Test
    public void shouldConvertToSixtyTwoSeconds() {
        int seconds = TimeAdapter.stringTimeToSeconds("00:01:02");
        assertEquals(62, seconds);
    }

    @Test
    public void shouldConvertTo3722Seconds() {
        int seconds = TimeAdapter.stringTimeToSeconds("01:02:02");
        assertEquals(3722, seconds);
    }


}
