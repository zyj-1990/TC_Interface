package tc.testcase.Gchat;

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
 * 解散群组
 * Created by zhaoyanji on 2016/6/23.
 *
 */
public class GroupDismiss extends ZhaoyanjiConfig{

    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void groupDismiss(String msg,String ent_id,String user_account,String g_id,String user_id,String password,String version,String exp_msg,int exp_code) throws Exception {
        Map m = new HashMap();
        m.put("ent_id",ent_id);
        m.put("user_account",user_account);
        m.put("g_id",g_id);
        m.put("user_id",user_id);
        m.put("password",password);
        m.put("version",version);

        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, null, entities);
        JSONObject res = HttpRequest.sendRequest(httpRequest, host, "gchat/groupDismiss");
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
        return data;
    }
}
