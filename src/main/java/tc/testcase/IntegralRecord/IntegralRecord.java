package tc.testcase.IntegralRecord;

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
 * 积分消费明细
 * Created by zhaoyanji on 6/19/16.
 */
public class IntegralRecord extends ZhaoyanjiConfig{
    //TODO 还要加上购买积分和商城购物校验操作
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider =  "data")
    public void integralRecord(String msg,String global_user_id,String page,String pageSize,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("pagesize",pageSize));
        paras.add(new Parameter("page",page));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/integralRecord");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"积分消费明细",global_user_id,"1","100",user_account,password,version,"success",0},
        };
        return data;
    }
}
