package tc.testcase.Address;

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
 * Created by zhaoyanji on 2016/6/24.
 * 获取通讯录下所有的对应员工的详细数据
 */
public class Lists extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void list(String msg,String user_account,String password,String version,String ent_id,String user_id,String page,String pagesize,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pagesize",pagesize));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "address/list");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"获取通讯录下所有的对应员工的详细数据","13516810150","dc483e80a7a0bd9ef71d8cf973673924","100000","72720","180011","1","50","success",0},
        };
        return data;
    }
}
