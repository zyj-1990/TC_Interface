package tc.testcase.gchat;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyanji on 2016/6/23.
 * 获取组织通讯列表
 */
public class GetUserGroup extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void getUserGroup(String msg,String ent_id,String user_account,String pagesize,String user_id,String password,String page,String search_type,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("pagesize",pagesize));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("search_type",search_type));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "gchat/getUserGroup");
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,exp_msg,msg);
        Assert.assertEquals(err_code,exp_code,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {},
                {},
        };
        return null;
    }
}
