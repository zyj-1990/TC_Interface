package tc.testcase.Login;

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
 * 统计登陆数据-配合商家版的要求添的接口
 * Created by zhaoyanji on 2016/6/27.
 */
public class UpdateLoginStatics extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void updateLoginStatics(String msg,String user_account,String version,String user_id,String exp_msg,int exp_code) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("user_id",user_id));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "login/updateLoginStatics",null,null);
        System.out.println(res);
        if(res == null && exp_msg == "failed"){
        }else{
            String err_msg = CommonApi.get_ErrorMsg(res);
            int err_code = CommonApi.get_ErrorCode(res);
            Assert.assertEquals(err_msg,exp_msg,msg);
            Assert.assertEquals(err_code,exp_code,msg);
        }
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 没有做字段值错误判断？那怎么知道谁是谁？
                {"统计登陆数据",user_account,version,user_id,"success",0},
                {"统计登陆数据-账号为空","",version,user_id,"success",0},
                {"统计登陆数据-账号不存在","1351681015",version,user_id,"success",0},
                {"统计登陆数据-账号非手机号(格式错误)","!@#$%^&*()WSDEFGHH",version,user_id,"success",0},
                {"统计登陆数据-user_id输入为空",user_account,version,"","failed",0},
                {"统计登陆数据-user_id输入错误",user_account,version,"jfsdjkfskfkkdjks","success",0},
        };
        return data;
    }
}
