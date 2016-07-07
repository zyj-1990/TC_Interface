package tc.testcase.Index;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class Person extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void person(String msg,String user_account,String password,String version,String global_user_id,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));

        Http httpRequest = new Http("post", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/person");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 不管输入什么，输出都是正确，接口没做判断
                {"获取用户个人信息",user_account,toMD5(password),version,global_user_id,"success",0},
        };
        return data;
    }
}
