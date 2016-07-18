package tc.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.helper.CommonApi;

/**
 * Created by zhaoyanji on 2016/7/6.
 */
public class CheckResult {
    public static void checkResult(JSONObject res,int expCode,String expMsg,String msg){
        System.out.println(res);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        Assert.assertEquals(err_msg, expMsg, msg);
        Assert.assertEquals(err_code, expCode, msg);
    }

    public static boolean checkIfInJsonArray(String key, Object value, JSONArray jsonArr){
        for(int i = 0; i < jsonArr.size(); i++){
            if(value.equals(jsonArr.getJSONObject(i).getString(key))){
                return true;
            }
        }
        return false;
    }
}
