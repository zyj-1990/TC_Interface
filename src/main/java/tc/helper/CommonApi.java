package tc.helper;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.mysql.fabric.xmlrpc.base.Param;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.helper.StringUtil;
import tc.utils.Parameter;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class CommonApi {
    public static String get_ErrorMsg(JSONObject json){
        String err_msg = json.getJSONObject("status").getString("error_msg");
        return err_msg;
    }

    public static int get_ErrorCode(JSONObject json){
        int error_code = json.getJSONObject("status").getInt("error_code");
        return error_code;
    }

    public static Long get_UnixTime(){
        return System.currentTimeMillis();
    }

    public static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32位: " + buf.toString());// 32位的加密
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Parameter> setHeaders(String key1,String value1,String key2,String value2){
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter(key1,value1));
        headers.add(new Parameter(key2,value2));
        return headers;
    }

    /**
     *
     * @param key
     * @param value
     * @param type:如果是Accept,代表动态设置Accept，反之一样
     * @return
     */
    public static List<Parameter> setHeaders(String key,String value,String type){
        List<Parameter> headers = new ArrayList<Parameter>();
        if(type == "Accept"){
            headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));
            headers.add(new Parameter(key, value));
        }else{
            headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
            headers.add(new Parameter(key, value));
        }
        return headers;
    }

    public static List<Parameter> setHeaders(){
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        return headers;
    }

    public static void setParasToSql(JSONObject obj,String tablename,String key,List<Parameter> conditions){
        //根据传入的JsonObject分解参数
        JSONArray jsonArr = obj.getJSONArray("bizobj");
        //用户数据获取
        JSONObject user_info = jsonArr.getJSONObject(0);
        //用户加入企业，获取所加企业相关字段信息
        JSONArray ent_info = jsonArr.getJSONArray(1);
        //要传入几个key，作为判断的记录是否存在的条件
        //分解的参数是key，value形式
        Map userMap = setValuesToMap(user_info);


        //如果一个人属于多企业，那么就创建多条数据库记录存储，所有字段全部存一遍,用用户的id作为唯一标识
        for(int i= 0; i < ent_info.size(); i++){
            userMap = setValuesToMap(userMap,ent_info.getJSONObject(i));

        }

        //执行sql，传入初始查询条件，先执行select判断是否存在数据库，再决定是update，还是add
        if(SqlApi.isRecordInSql(tablename,key,conditions)){
            SqlApi.sql_update(tablename,userMap,conditions);
        }else{
            SqlApi.sql_insert(tablename,userMap);
        }
        //上面那步用spring，mybatis来操作，动态拼接数据库sql
    }

    public static Map<String,String> setValuesToMap(JSONObject jsonObject){
        Map<String,String> temp = new HashMap<String, String>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = jsonObject.getString(key);
            if (!value.isEmpty()) {
                temp.put(key,value);
            }
        }
        return temp;
    }

    public static Map<String,String> setValuesToMap(Map map,JSONObject jsonObject){
        Map<String,String> temp = new HashMap<String, String>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = jsonObject.getString(key);
            if (!value.isEmpty()) {
                temp.put(key,value);
            }
        }
        map.putAll(temp);
        return map;
    }

    /**
     * 判断值是否为空，不为空就放进List中
     * @param conditions
     * @param tablename
     * @param key
     * @return
     */
    public static List<Parameter> addConditions( List<Parameter> conditions,String tablename,String key){
        try{
            ResultSet result = SqlApi.sql_select(tablename,key);
            boolean bo = result.next();
            String temp =  bo? result.getString(key):null;
            if(temp != null){
                conditions.add(new Parameter(key,temp));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return conditions;
    }
}
