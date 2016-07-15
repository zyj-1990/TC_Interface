package tc.testcase.Index;

import net.sf.json.JSONObject;
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
import java.util.List;

/**
 * 获取服务器时间戳
 * Created by zhaoyanji on 2016/7/8.
 */
public class Time extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider =  "data")
    public void time(String msg,String type,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("type",type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/Time");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"获取服务器时间戳-秒级","0",user_account,password,version,"success",0},
                {"获取服务器时间戳-毫秒级","1",user_account,password,version,"success",0},
        };
        return data;
    }
}
