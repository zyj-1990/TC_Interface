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
import java.lang.Object;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
public class SqlApi {
    static BoneCP connectionPool = null;
    static Connection connection = null;
    static BoneCPConfig config = new BoneCPConfig();

    static {
        try {
            //加载JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
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
     * 初始化数据库配置等相关信息
     */
    public SqlApi(){
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
    public static void sql_insert(String tableName, Map<String,String> map){
        String keysString = getInsertSql(map,"key");
        String valuesString = getInsertSql(map,"value");
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                System.out.println("keysString" + keysString);
                System.out.println("valuesString" + valuesString);
                int rs = stmt.executeUpdate(" INSERT INTO " + tableName + ""+ keysString + "  values  " + valuesString + "");
//              if(rs != null){
//              }
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
    public static void sql_update(String tableName, Map<String,String> map, List<Parameter> cdn){
        String condition = conditionListToString(cdn);


        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                for (Map.Entry<String,String> entry : map.entrySet()) {
                    Statement stmt = connection.createStatement();
                    int rs = stmt.executeUpdate(" UPDATE " + tableName + " SET " + entry.getKey() + " = " + "\'" +  entry.getValue() + "\'" +  " WHERE " + condition + "");
//                    if(rs != null){
//                    }
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
//      TODO 代码应该无用
    public static void sql_update(String tableName, Map<String,String> map){
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                for (Map.Entry<String,String> entry : map.entrySet()) {
                    Statement stmt = connection.createStatement();
                    int rs = stmt.executeUpdate(" UPDATE " + tableName + " SET " + entry.getKey() + " = " + "\'" +  entry.getValue() + "\'" + "");
//                    if(rs != null){
//                    }
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
     * @return
     */
    public static ResultSet sql_select(String tableName,String key){
        //设置数据库连接池
        try {
            //设置连接池配置信息
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection
            if (connection != null){
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(" SELECT "+ key +  " FROM "+ tableName);
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
     * 查询数据库
     * @param tableName
     * @param key
     * @param cdn
     * @return
     */
    public static ResultSet sql_select(String tableName,String key,List<Parameter> cdn){
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
                ResultSet rs = stmt.executeQuery(" SELECT "+ key +  " FROM "+ tableName + " WHERE + " + condition + "");
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
     *
     */
    public static String sql_select_data(String tableName,String key,List<Parameter> cdn)throws Exception{
        ResultSet rs = sql_select(tableName,key,cdn);
        rs.next();
        return rs.getString(key);
    }

    /**
     * 判断要查询的纪录是否在数据库中
     * @param tableName
     * @param map
     * @param cdn
     * @return
     */
    public static boolean isRecordInSql(String tableName,Map<String,Object> map,List<Parameter> cdn){
        ResultSet result = null;
        String key = "";
        for(Object set : map.keySet()){
            key = key + set.toString() + ",";
        }
        key = key.substring(0,key.length()-1);
        try {
            if(cdn.size() > 0){
                result= sql_select(tableName,key,cdn);
            }else{
                result= sql_select(tableName,key);
            }
            if (result.next()) {
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
    public static String conditionListToString(List<Parameter> cdns){
        String cdnString = null;
        cdnString = cdns.get(0).getName() + " = " + "\'" +cdns.get(0).getValue() + "\'";
        for(int i = 1; i< cdns.size(); i++){
            cdnString = cdnString + " and " + cdns.get(i).getName() + " = " + "\'" +cdns.get(i).getValue() + "\'";
        }
        return cdnString;
    }

    /**
     * 获取插入的数据库记录
     * @param map
     * @param type
     * @return
     */
    public static String getInsertSql(Map<String,String> map, String type){
        String temp = "";

        if(type.equals("key")){
//            temp = "no,";
            temp = "";
            for(String key :map.keySet()){
                temp = temp + key.toString() + ",";
            }
            temp = temp.substring(0,temp.length()-1);
            temp = "("+ temp + ")";
            return temp;
        }else if(type.equals("value")){
//            temp = getPriKey(tablename,"count(1) as no") + ",";
            temp = "";
            for(String value:map.values()){
                temp = temp + "\'" + value.toString() + "\'" + ",";
            }
            temp = temp.substring(0,temp.length()-1);
            temp = "("+ temp + ")";
            return temp;
        }else{
            return null;
        }
    }
}
