package curse;

/**
 * Created by Xoxii on 12-Mar-17.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by grigo on 3/4/17.
 */
public class DbUtils {
    private Properties jdbcProps;

    public DbUtils(Properties props){
        jdbcProps=props;
    }
    private  Connection instance=null;
    private Connection getNewConnection(){
        String driver=jdbcProps.getProperty("tasks.jdbc.driver");
        String url=jdbcProps.getProperty("tasks.jdbc.url");
        Connection con=null;
        try {
            Class.forName(driver);
            con= DriverManager.getConnection(url);

        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}
