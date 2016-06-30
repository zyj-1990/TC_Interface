package tc.testcase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/** 测试bonecp数据库连接池的工具
 * @author zhoufoxcn 周公
 * @version 0.1
 * 说明：这是一个在JDBC中使用BoneCP的例子
 * 2010-11-23
 */
public class a {
    public static void main(String[] args) {
        BoneCP connectionPool = null;
        Connection connection = null;

        try {
            //加载JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {


            //设置连接池配置信息
            BoneCPConfig config = new BoneCPConfig();
            //数据库的JDBC URL
            config.setJdbcUrl("jdbc:mysql://10.1.25.117:3306/eetopin");
            //数据库用户名
            config.setUsername("fuse");
            //数据库用户密码
            config.setPassword("123456");
            //数据库连接池的最小连接数
            config.setMinConnectionsPerPartition(5);
            //数据库连接池的最大连接数
            config.setMaxConnectionsPerPartition(10);
            //
            config.setPartitionCount(1);
            //设置数据库连接池
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection

            if (connection != null){
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM eetopin.eetopin_verify_code where mobile = '18668462782';");
                int i = 0;
                while(rs.next()){
                    i++;
                    System.out.println(i);
                    System.out.println(rs.getString("mobile")+","+rs.getString("code"));
                }
            }
            //关闭数据库连接池
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}