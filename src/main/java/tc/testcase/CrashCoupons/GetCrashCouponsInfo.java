package tc.testcase.CrashCoupons;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.SqlApi;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 获得卡卷信息
 * Created by zhaoyanji on 2016/7/8.
 */
public class GetCrashCouponsInfo extends ZhaoyanjiConfig{
    //TODO 后期再自动获取现金券id，并自动领取现金券
    String cardId = "";

    @BeforeClass
    public void beforeClass(){

    }

    @Test(dataProvider = "data")
    public void getCrashCouponsInfo(String msg,String global_user_id,String cardId,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("id",cardId));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"Index/GetCrashCouponsInfo",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"获得卡卷信息",global_user_id,"R7PziBoPHLlXfWtm4QI9FA==",user_account,password,version,"success",0},
        };
        return data;
    }
}
