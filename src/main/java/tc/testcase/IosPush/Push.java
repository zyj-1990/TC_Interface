package tc.testcase.IosPush;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyanji on 2016/7/7.
 */
public class Push extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        CommonApi.getCommonValueFromSql();
    }

    @Test(dataProvider = "data")
    public void push(String msg,String user_account,String password,String version,String token,String message,String expMsg,int expCode) throws Exception {
        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        m.put("token",token);
        m.put("message",message);
        System.out.println(m);
        List<Parameter> paras = JsonConvert.mapToKV(m);

        Http httpRequest = new Http("post", paras, null,null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "iosPush/push",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);

    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 怎么样才可以推送成功？？
                {"应用消息数量更新",user_account,toMD5(password),version,token,"this is the push msg","推送失败",1000},
        };
        return data;
    }
}
