package tc.testcase.Index;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 医疗机构品牌列表（隐秀、三叶）
 * Created by zhaoyanji on 2016/7/11.
 */
public class Medicals extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void customerService(String msg,String user_account,String password,String version,String expMsg,int expCode) throws Exception {

        List<Parameter> paras = new ArrayList<Parameter>();
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
                {"医疗机构品牌列表（隐秀、三叶）",user_account,password,version,"success",0},
        };
        return data;
    }
}
