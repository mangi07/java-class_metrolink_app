import com.ben.dao.SqliteJDBCDao;
import com.ben.util.ScreenOutput;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ben on 2/10/2016.
 */
public class TestSqliteJDBCDao {

    SqliteJDBCDao dao = null;

    @Before
    public void setUp () {
        dao = new SqliteJDBCDao(new ScreenOutput());
    }
    @Test
    public void shouldAccessDatabase() {
        dao.getStopsAllStops();
    }

    @Test
    public void shouldSetAppOutput() {
        dao.setAppOutput(new ScreenOutput());
    }
}
