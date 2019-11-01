package tedu.bao.day21;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static String url;
    private static String username;
    private static String password;

    private static Connection connection;
    private ResultSet resultSet;
    private Statement statement;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DB.class.getResourceAsStream("/jdbc.properties"));
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            //connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getConnection() throws Exception {
        connection = DriverManager.getConnection(url, username, password);
    }

    private void getStatement() throws Exception {
        if (connection == null) {
            getConnection();
            getStatement();
        }else{
            statement = connection.createStatement();
        }
    }

    public ResultSet getResultSet(String sql) {
        try {
            if (statement == null) {
                getStatement();
                return getResultSet(sql);
            }
            return statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String[] args) throws SQLException {
        ResultSet re = new DB().getResultSet("select *,now() from user");

        ResultSetMetaData metaData = re.getMetaData();
        //System.out.println(metaData.getColumnCount());
        while (re.next()){
            for (int i = 1 ;i<=metaData.getColumnCount();i++){
                System.out.println(metaData.getColumnName(i));
                System.out.println(metaData.getColumnClassName(i)+":"+re.getObject(i));
            }
            return;
            //System.out.println();
        }
    }
}
