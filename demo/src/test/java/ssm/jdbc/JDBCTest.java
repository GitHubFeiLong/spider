package ssm.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JDBCTest {
    private static final Logger log = LoggerFactory.getLogger(JDBCTest.class);
    public static void main(String[] args) {
        try {
//            String url = "jdbc:mysql://localhost:3306/spider?useUnicode=true&characterEncoding=utf8";
            String url = "jdbc:mysql://localhost:3306/spider?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String passwd = "123456";
            // 注册驱动
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = null;
            Statement statement = null;
            // 获取连接对象
            try {
                connection = DriverManager.getConnection(url,username,passwd);
                statement = connection.createStatement();
                // 创建sql执行对象
                String sql = "select id,name from user";
                //执行sql语句
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    User u = new User(id,name);
                    log.info(u.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                // 关闭资源
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
