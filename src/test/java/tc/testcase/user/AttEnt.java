package tc.testcase.user;

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
 * Created by zhaoyanji on 6/22/16.
 */
public class AttEnt extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void attEnt(String msg,String ent_id,String id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        m.put("id",id);
        m.put("ent_id",ent_id);
        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, headers, entities);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "user/attEnt");
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, expMsg, msg);
        Assert.assertEquals(err_code, expCode, msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"关闭特色列表接口-账号为空","","","","dc483e80a7a0bd9ef71d8cf973673924","100000","success",0},
                {"关闭特色列表接口-账号不存在","","","18668462783","dc483e80a7a0bd9ef71d8cf973673924","100000","success",0},
                {"关闭特色列表接口-账号或者密码错误","","","18668462782","dc483e80a7a0bd9ef71d8cf973673112924","100000","success",0},
        };
        return data;
    }
}
