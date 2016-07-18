package tc.testcase.Gchat;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看叽歪群的所有用户信息
 * Created by zhaoyanji on 2016/6/24.
 */
public class GetUserInfoByGGId extends ZhaoyanjiConfig{
    static Long g_id = CommonApi.get_UnixTime();
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void getUserInfoByGGId(String msg,String ent_id,String user_account,String user_id,String password,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "gchat/getUserInfoByGGId");
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
                {"查看叽歪群的所有用户信息",ent_id,user_account,user_account,toMD5(password),version,"success",0},
        };
        return data;
    }
}
