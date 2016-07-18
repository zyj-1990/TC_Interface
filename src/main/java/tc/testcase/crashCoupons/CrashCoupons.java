package tc.testcase.CrashCoupons;

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
 * 现金券列表
 * Created by zhaoyanji on 6/18/16.
 */
public class CrashCoupons extends ZhaoyanjiConfig {
    //TODO 还要有买消费券，等数据校验操作
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider =  "data")
    public void crashCoupons(String msg,String global_user_id,String status,String page,String pageSize,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("status",status));
        paras.add(new Parameter("pageSize",pageSize));
        paras.add(new Parameter("page",page));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/crashCoupons");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"现金券列表",global_user_id,"0","1","100",user_account,password,version,"success",0},
        };
        return data;
    }
}
