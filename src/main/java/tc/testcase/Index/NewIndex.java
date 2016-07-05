package tc.testcase.Index;

import net.sf.json.JSONArray;
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
 * 首页获取医院信息
 * Created by zhaoyanji on 2016/6/24.
 */
public class NewIndex extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        CommonApi.getCommonValueFromSql();
    }

    @Test(dataProvider = "data")
    public void newIndex(String msg,String user_account,String password,String version,String mobile_uid,String expMsg,int expCode) throws Exception {
        List<Parameter> conditions = new ArrayList<Parameter>();

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

        Map key = new HashMap();
        key.put("ent_id",null);

        if(msg == "首页医院信息查询"){
            JSONArray jsonArr = res.getJSONObject("bizobj").getJSONArray("ent_list");
            CommonApi.setJsonArrToSql(jsonArr,hospitalTable,key,conditions);
        }
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 不管输入什么，输出都是正确，接口没做判断
                {"首页医院信息查询",user_account,password,version,mobile_uid,"success",0},
//                {"首页医院信息查询－手机号码为空","",password,version,mobile_uid,"success",0},
//                {"首页医院信息查询－手机号码格式不对","1866846278356",password,version,mobile_uid,"success",0},
//                {"首页医院信息查询－手机号码内容不对","!@#$%^&*()_",password,version,mobile_uid,"success",0},
//                {"首页医院信息查询－手机号码不存在","18668462799",password,version,mobile_uid,"success",0},
//                {"首页医院信息查询-密码为空",user_account,"",version,mobile_uid,"success",0},
//                {"首页医院信息查询-密码错误",user_account,"dc483e80a7a0bd9ef71d8cf9736739245656",version,mobile_uid,"success",0},
//                {"首页医院信息查询-mobile_uid为空",user_account,password,version,"","success",0},
//                {"首页医院信息查询-mobile_uid不存在",user_account,password,version,"1056666","success",0},
//                {"首页医院信息查询-mobile_uid为非数字",user_account,password,version,"@#$%^&*(","success",0},
        };
        return data;
    }
}
