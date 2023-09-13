package cn.wangye.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    //    私有构造方法，防止其他调用
    private DBUtil() {
    }

    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/jdbc");

    //由于注册驱动，只需要执行一次，我们可以使用静态代码块,使用static代码块之后，块中的代码随着类加载的时候进行，不需要太手动调用
    static {
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //一个线程，保证永远都是获取的一个Connection对象；
    private static ThreadLocal<Connection> local = new ThreadLocal<>();
    //封装获取数据库连接对象;
    public static Connection getConnection() throws Exception {
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection conn= local.get();
        if(conn==null){
            conn  = DriverManager.getConnection(url, user, password);
            local.set(conn);
        }
        return conn;
    }

    //释放资源
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
                local.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
