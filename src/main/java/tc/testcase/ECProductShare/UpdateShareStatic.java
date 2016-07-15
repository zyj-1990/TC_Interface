package tc.testcase.ECProductShare;

import net.sf.json.JSONObject;
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
 * EC商品分享回调
 * Created by zhaoyanji on 2016/7/11.
 */
public class UpdateShareStatic extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void updateShareStatic(String msg,String mobile,String from,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("from",from));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"index/medicals");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 分享来源1：朋友圈，2：朋友，3：微博
                {"EC商品分享回调",user_account,"1",user_account,password,version,"success",0},
        };
        return data;
    }
}
