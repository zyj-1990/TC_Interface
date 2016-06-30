package tc.helper;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import tc.utils.Parameter;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
public class SqlApi {
    BoneCP connectionPool = null;
    Connection connection = null;
    BoneCPConfig config = new BoneCPConfig();

    /**
     * 初始化数据库配置等相关信息
     */
    public void init(){
        try {
            //加载JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
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
        config.setPartitionCount(1);
    }

    /**
     * 数据库插入操作
     * @param tableName
     * @param map
     */
    public void sql_insert(String tableName, Map<String,Object> map){
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                for (Map.Entry<String,Object> entry : map.entrySet()) {
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(" INSERT INTO '" + tableName + "' ( '"+ entry.getKey() + "' ) values ( '" + entry.getValue() + "' )");
                    System.out.println(rs.getString(entry.getKey()));
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

    /**
     * 数据库更新操作
     * @param tableName
     * @param map
     * @param cdn
     */
    public void sql_update(String tableName, Map<String,Object> map, List<Parameter> cdn){
        String condition = conditionListToString(cdn);
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                for (Map.Entry<String,Object> entry : map.entrySet()) {
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(" UPDATE '"+tableName +  "' SET '"+ entry.getKey() + "' +  '=' + '" + entry.getValue() + "' WHERE + '" + condition + "'");
                    System.out.println(rs.getString(entry.getKey()));
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

    /**
     * 查询数据库
     * @param tableName
     * @param key
     * @param cdn
     * @return
     */
    public ResultSet sql_select(String tableName,String key,List<Parameter> cdn){
        String condition = conditionListToString(cdn);
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(" SELECT '"+ key +  "' FROM '"+ tableName + "' WHERE + '" + condition + "'");
                System.out.println(rs.getString(key));
                return rs;
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
        return null;
    }

    /**
     * 判断要查询的纪录是否在数据库中
     * @param tableName
     * @param key
     * @param cdn
     * @return
     */
    public boolean isRecordInSql(String tableName,String key,List<Parameter> cdn){
        ResultSet result= sql_select(tableName,key,cdn);
        try {
            if (!result.getString(key).isEmpty()) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();;
        }
        return false;
    }

    /**
     * 把list里的条件cdn全部按照一定格式拼接好字符串，再返回去
     * @param cdns
     * @return
     */
    public String conditionListToString(List<Parameter> cdns){
        String cdnString = null;
        cdnString = cdns.get(0).getName() + " = " + cdns.get(0).getValue();
        for(int i = 1; i< cdns.size(); i++){
            cdnString = cdnString + "and" + cdns.get(i).getName() + " = " + cdns.get(i).getValue() ;
        }
        System.out.println(cdnString);
        return cdnString;
    }

}
