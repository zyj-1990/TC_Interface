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
 * 积分会员卡的列表
 * Created by zhaoyanji on 6/19/16.
 */
public class CardList extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider =  "data")
    public void cardList(String msg,String global_user_id,String name,String mobile,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("name",name));
        paras.add(new Parameter("mobile",mobile));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/integral/cardList");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"积分会员卡的列表",global_user_id,"shuaibiming","13516810150",user_account,password,version,"success",0},
        };
        return data;
    }
}
