package tedu.bao.db;

import java.sql.*;

public class DB {
    private static String username;
    private static String password;
    private static String url;

    private static Connection connection;
    private static Statement statement;
    private static boolean flag = true;

    static {
        url = "jdbc:mysql://localhost/ebook?userUnicode=true&characterEncoding=utf-8";
        username = "root";
        password = "root";
    }

    private static void getConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null)
                System.out.println("数据库链接成功!");
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            return;
        }
    }

    private static void getStatement() {
        if (flag) {
            if (connection == null) {
                getConnection();
                getStatement();
            } else {
                try {
                    statement = connection.createStatement();
                    System.out.println("statement成功!");
                } catch (SQLException e) {
                    flag = false;
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public ResultSet getResultSet(String sql) {
        try {
            if (statement == null) {
                getStatement();
                return getResultSet(sql);
            } else {
                return statement.executeQuery(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean execute(String sql) throws Exception{

            if (statement == null) {
                getStatement();
                return execute(sql);
            } else {
                return statement.executeUpdate(sql) > 0;
            }

    }
}
