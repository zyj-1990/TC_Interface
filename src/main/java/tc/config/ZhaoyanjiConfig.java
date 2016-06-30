package tc.config;

import tc.helper.CommonApi;
import tc.utils.Parameter;

import java.util.List;

/**
 * Created by zhaoyanji on 6/18/16.
 */
public class ZhaoyanjiConfig extends CommonApi{
    public static List<Parameter> headers = null;

    public static String user_account = "13516810150";
//    public static String user_account = "18668462782";
    public static String password = "";
    public static String md5Password = "";
    public static String global_user_id = "";
    public static String user_id = "";
    public static String mobile_uid = "";
    public static String version = "100000";
    public static String name = "";
    public static String mobile = "";
    public static String wecha_id = "";


    //通用url相关配置
    public static String host = "";
    public static String OnLineHost = "mobileapi.eetop.com/";
    public static String SanYeHost = "shop.eetop.com/";
    public static String common_path = "";
    public static int port = 0;

    static{
        if(user_account == "13516810150"){
            password = "a123456";
            md5Password = toMD5(password);
            global_user_id = "4739fcb7-0dca-f6a2-fc50-6b286b61e7b6";
            user_id = "180011";
            mobile_uid = "105664";
            name = "帅比名";
            mobile = "13516810150";
            wecha_id = "";
            host = "mobileapi.eetopintest.com/";
        }else if(user_account == "18668462782"){
            password = "a123456";
            md5Password = toMD5(password);
            global_user_id = "2f999599-e39f-9d61-e03e-b81ba834b8bd";
            wecha_id = "";
            host = "mobileapi.eetop.com/";
        }

        headers = setHeaders();
    }


}
