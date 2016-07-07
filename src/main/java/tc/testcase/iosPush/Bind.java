package tc.testcase.IosPush;

import net.sf.json.JSONObject;
import org.testng.Assert;
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
public class Bind extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        CommonApi.getCommonValueFromSql();
    }

    @Test(dataProvider = "data")
    public void bind(String msg,String user_account,String password,String version,String user_id,String user_token,String client_type,String global_ent_id,String global_user_id,String expMsg,int expCode) throws Exception {
        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        m.put("user_id",user_id);
        m.put("user_token",user_token);
        m.put("client_type",client_type);
        m.put("global_ent_id",global_ent_id);
        m.put("global_user_id",global_user_id);
        System.out.println(m);
        List<Parameter> paras = JsonConvert.mapToKV(m);

        Http httpRequest = new Http("post", paras, null,null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "iosPush/bind",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
        Assert.assertEquals(Integer.parseInt(res.getJSONObject("bizobj").getString("id")) > 0 ,true,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"绑定用户",user_account,toMD5(password),version,user_id,token,"per",global_ent_id,global_user_id,"success",0},
        };
        return data;
    }
}
