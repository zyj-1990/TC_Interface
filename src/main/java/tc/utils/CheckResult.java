package tc.utils;

import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.helper.CommonApi;

/**
 * Created by zhaoyanji on 2016/7/6.
 */
public class CheckResult {
    public static void checkResult(JSONObject res,int expCode,String expMsg,String msg){
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, expMsg, msg);
        Assert.assertEquals(err_code, expCode, msg);
    }
}
