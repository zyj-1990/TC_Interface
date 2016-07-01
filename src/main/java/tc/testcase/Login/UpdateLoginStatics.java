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
    public void updateLoginStatics(String msg,String user_account,String password,String version,String user_id,String exp_msg,int exp_code) throws Exception {
        java.util.List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("user_id",user_id));

        Http httpRequest = new Http("post", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "login/updateLoginStatics");
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
                //TODO 没有做字段值错误判断？那怎么知道谁是谁？
                {"统计登陆数据",user_account,password,version,user_id,"success",0},
                {"统计登陆数据-账号为空","",password,version,user_id,"success",0},
                {"统计登陆数据-账号不存在","1351681015",password,version,user_id,"success",0},
                {"统计登陆数据-账号非手机号(格式错误)","!@#$%^&*()WSDEFGHH",password,version,user_id,"success",0},
                {"统计登陆数据-密码为空",user_account,"",version,user_id,"success",0},
                {"统计登陆数据-密码小于6位",user_account,"admin",version,user_id,"success",0},
                {"统计登陆数据-密码大于16位",user_account,"admin1234567890",version,user_id,"success",0},
                {"统计登陆数据-密码错误",user_account,"admin1234567890",version,user_id,"success",0},
                {"统计登陆数据-密码输入特殊字符",user_account,"!@#$%^&*()_+_~~",version,user_id,"success",0},
                {"统计登陆数据-user_id输入为空",user_account,password,version,"","success",0},
                {"统计登陆数据-user_id输入错误",user_account,password,version,"jfsdjkfskfkkdjks","success",0},
        };
        return data;
    }
}
