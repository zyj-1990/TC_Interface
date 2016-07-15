package tc.testcase.My;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.SqlApi;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的客服
 * Created by zhaoyanji on 2016/7/8.
 */
public class CustomerService extends ZhaoyanjiConfig{
    List<Parameter> cdn = new ArrayList<Parameter>();
    String name = "杭口城西分院";

    @Test(dataProvider = "data")
    public void customerService(String msg,String name,String global_user_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        cdn.add(new Parameter("name",name));

        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"my/customerService");

        if(expCode == 555){
            Assert.assertEquals(StringUtils.replaceBlank(res.getString("wrong")),StringUtils.replaceBlank(expMsg),msg);
        }else{
            CheckResult.checkResult(res,expCode,expMsg,msg);
        }
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"我的客服",name,global_user_id,user_account,password,version,"success",0},
        };
        return data;
    }
}
