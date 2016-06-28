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
 * Created by zhaoyanji on 6/24/16.
 * 编辑个人信息
 */
public class EditPersonal extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void editPersonal(String msg,String id,String user_name,String nickname,Integer sex,String user_account,String password,String version,String expMsg,int expCode) throws Exception {

        Map m = new HashMap();
        m.put("id",id);
        if(user_name != null){
            m.put("user_name",user_name);
        }
        if(nickname != null){
            m.put("nickname",nickname);
        }
        if(sex != null){
            m.put("sex",sex);
        }
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, headers, entities);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "user/editPersonal");

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
                {"编辑用户名字","236607","hha",null,null,user_account,password,"100000","success",0},
        };
        return data;
    }
}
