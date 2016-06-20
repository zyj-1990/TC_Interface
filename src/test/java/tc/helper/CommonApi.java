package tc.helper;

import net.sf.json.JSONObject;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class CommonApi {
    public static String get_ErrorMsg(JSONObject json){
        String err_msg = json.getJSONObject("status").getString("error_msg");
        return err_msg;
    }

    public static String get_ErrorCode(JSONObject json){
        String error_code = json.getJSONObject("status").getString("error_code");
        return error_code;
    }

}