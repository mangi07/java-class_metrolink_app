import com.ben.DaoFactory;
import com.ben.dao.DaoType;
import com.ben.util.ScreenOutput;
import org.junit.Test;

/**
 * Created by ben on 2/13/2016.
 */
public class TestDaoFactory {
    @Test
    public void shouldCreateSqliteJDBCDao() {
        DaoFactory.createDao(
                DaoType.SQLITE_JDBC_DAO, ScreenOutput.getInstance());
    }
}
