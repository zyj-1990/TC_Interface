package tc.testcase.Gchat;

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
 * 获取用户所有群组
 * Created by zhaoyanji on 2016/6/23.
 */
public class GetUserGroup extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void getUserGroup(String msg,String ent_id,String user_account,String pagesize,String user_id,String password,String page,String search_type,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("pagesize",pagesize));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("search_type",search_type));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "gchat/getUserGroup");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"获取用户所有的群组动态列表数据","72720","13516810150","20","180011","dc483e80a7a0bd9ef71d8cf973673924","1","0","100000","success",0},
        };
        return data;
    }
}
