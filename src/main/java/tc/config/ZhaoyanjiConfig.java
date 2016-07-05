package tc.config;

import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.helper.SqlApi;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 6/18/16.
 */
public class ZhaoyanjiConfig extends CommonApi{
    public static List<Parameter> headers = null;

    public static String user_account = "13516810152";
//    public static String user_account = "18668462782";
    public static String password = "";
    public static String md5Password = "";
    public static String global_user_id = "";
    public static String user_id = "";
    public static String mobile_uid = "";
    public static String version = "100000";
    public static String name = "";
    public static String mobile = "";
    public static String nick_name = "";
    public static String wecha_id = "";
    //
    public static String ent_id = "";


    //通用url相关配置
    public static String host = "";
    public static String OnLineHost = "mobileapi.eetop.com/";
    public static String SanYeHost = "shop.eetop.com/";
    public static String common_path = "";
    public static int port = 0;


    //数据库的表名
    public static String loginTable = "eetopin.eetopin_IT_login";
    public static String hospitalTable = "eetopin.eetopin_IT_hospital";

    static{
        try {
            if(user_account == "13516810152"){
                password = "a123456";
                md5Password = toMD5(password);
//                wecha_id = "";
                host = "mobileapi.eetopintest.com/";
            }else if(user_account == "18668462782"){
                password = "a123456";
                md5Password = toMD5(password);
//                wecha_id = "";
                host = "mobileapi.eetop.com/";
            }
            CommonApi.getCommonValueFromSql();
        }catch(Exception e){
            e.printStackTrace();
        }


        headers = setHeaders();
    }


}
