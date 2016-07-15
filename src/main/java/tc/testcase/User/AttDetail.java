package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;

/**
 * 关注详情
 * Created by zhaoyanji on 2016/7/11.
 */
public class AttDetail extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void attDetail(String msg,String id,String ent_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {

        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"user/attDetail");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 找到这个接口在哪调用，造数据，删除生成数据
                {"关注详情",id,ent_id,user_account,password,version,"success",0},
        };
        return data;
    }
}
