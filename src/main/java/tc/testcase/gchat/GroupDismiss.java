package tc.testcase.Gchat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.helper.SqlApi;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解散群组
 * Created by zhaoyanji on 2016/6/23.
 *
 */
public class GroupDismiss extends ZhaoyanjiConfig{
    //生成一个g_id
    static Long g_id = CommonApi.get_UnixTime();
    List<String> expNickname = new ArrayList<String>();

    @BeforeClass
    public void beforeClass() throws Exception{
        //创建一个叽歪群
        CommonOperation.create("空","1","1",g_id,"1","啦啦啦");
    }

    @Test
    public void CheckIfGroupCreated() throws Exception {
        List<Parameter> conditions = new ArrayList<Parameter>();
        conditions.add(new Parameter("user_id",user_id));
        JSONObject res = CommonOperation.getUserInfoByGGId(g_id);
        JSONArray jsonArr = res.getJSONArray("bizobj");
        Assert.assertEquals(jsonArr.getJSONObject(0).getString("nick_name"), SqlApi.sql_select_data(loginTable,"name",conditions),"创建群组失败");
    }

    @Test(dataProvider = "data",dependsOnMethods = "CheckIfGroupCreated")
    public void groupDismiss(String msg,String ent_id,String user_account,Long g_id,String user_id,String password,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "gchat/groupDismiss");
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
                {"",ent_id,user_account,g_id,user_id,toMD5(password),version,"success",0},
        };
        return data;
    }
}
