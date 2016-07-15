package tc.testcase.Attention;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;

/**
 * 是否关注
 * Created by zhaoyanji on 2016/7/14.
 */
public class IsAtt extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void isAtt(String msg,String user_account,String password,String version,String user_id,String ent_id,String oppo_id,String exp_msg,int exp_code,String exp_clew_code) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("oppo_id",oppo_id));
        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "attention/isAtt");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
        String clew_code = res.getJSONObject("bizobj").getString("clew_code");
        Assert.assertEquals(clew_code,exp_clew_code,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"是否关注-未关注",user_account,toMD5(password),version,user_id,ent_id,"180017","success",0,"0"},
                {"是否关注-已关注",user_account,toMD5(password),version,user_id,ent_id,"180012","success",0,"2"},
                //TODO 还差一个相互关注和被关注
        };
        return data;
    }
}
