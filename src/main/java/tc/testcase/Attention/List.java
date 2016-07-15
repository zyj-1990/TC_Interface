package tc.testcase.Attention;

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

/**
 * Created by zhaoyanji on 2016/7/14.
 */
public class List extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void list(String msg,String user_account,String password,String version,String user_id,String ent_id,int page,int pageSize,String exp_msg,int exp_code) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pageSize",pageSize));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "attention/list");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"用户所属企业列表",user_account,toMD5(password),version,user_id,ent_id,1,50,"success",0},
        };
        return data;
    }
}
