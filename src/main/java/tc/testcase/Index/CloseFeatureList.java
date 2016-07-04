package tc.testcase.Index;

import net.sf.json.JSON;
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
 * 临时关闭功能列表
 * Created by zhaoyanji on 6/21/16.
 */
public class CloseFeatureList extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void closeFeatureList(String msg,String user_account,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("version",version);
        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, headers, entities);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/closeFeatureList");

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
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO　接口文档显示什么都不传，实际传的参数都是没用的，返回的全是正确
                {"临时关闭功能列表",user_account,version,"success",0},
                {"临时关闭功能列表-手机号码为空","",version,"success",0},
                {"临时关闭功能列表-手机号码不存在","13516810199",version,"success",0},
                {"临时关闭功能列表-手机号码格式不对","135168101",version,"success",0},
                {"临时关闭功能列表-手机号码内容非数字","ASDFGHJKLIO",version,"success",0},
        };
        return data;
    }
}
