package tc.testcase.Index;

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
 * Created by zhaoyanji on 2016/6/24.
 */
public class NewIndex extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void newIndex(String msg,String user_account,String password,String version,String mobile_uid,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        m.put("mobile_uid",mobile_uid);
        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, headers, entities);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/NewIndex");
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
                //TODO 接口测试用例还未写完整
                {"关闭特色列表接口-账号为空","","dc483e80a7a0bd9ef71d8cf973673924","100000",mobile_uid,"success",0},
                {"关闭特色列表接口-账号不存在","18668462783","dc483e80a7a0bd9ef71d8cf973673924","100000",mobile_uid,"success",0},
                {"关闭特色列表接口-账号或者密码错误","18668462782","dc483e80a7a0bd9ef71d8cf973673112924","100000",mobile_uid,"success",0},
        };
        return data;
    }
}
