package tc.testcase.user;

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
 * Created by zhaoyanji on 6/24/16.
 * 重新绑定获取验证码
 */
public class GetVerifyCode extends ZhaoyanjiConfig{

    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void getVerifyCode(String msg,String user_account,String password,String mobile,String is_mobile_bind,String version,String code_type,String exp_msg,int exp_code) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("is_mobile_bind",is_mobile_bind));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("code_type",code_type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "findPwd/getVerifyCode");
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
                {"获取验证码",user_account,password,"13516810147","1",version,"3","success",0},
                {"获取验证码-账号为空","",password,"13516810148","1",version,"3","success",0},
                {"获取验证码-账号不存在","13516910150",password,"13516810149","1",version,"3","success",0},
                {"获取验证码-账号格式错误","1351681015",password,"13516810164","1",version,"3","success",0},
                {"获取验证码-密码为空",user_account,"","13516810155","1",version,"3","success",0},
                {"获取验证码-密码不正确",user_account,"admin123","13516810165","1",version,"3","success",0},
                {"获取验证码-密码内容错误",user_account,"!#$%^&*()_+_~","13516810166","1",version,"3","success",0},
                {"获取验证码-密码小于6位",user_account,"admin","13516810153","1",version,"3","success",0},
                {"获取验证码-密码大于16位",user_account,"admin123456789023","13516810154","1",version,"3","success",0},
                {"获取验证码-绑定手机号格式错误",user_account,password,"135168101","1",version,"3","success",0},
//                {"获取验证码-绑定手机号为空",user_account,password,"","1",version,"3","mobile不能为空",1002},
                {"获取验证码-绑定手机号内容错误",user_account,password,"!@#$%^&*()","1",version,"3","success",0},
                {"获取验证码-is_mobile_bind为0",user_account,password,"13516810155","0",version,"3","success",0},
                {"获取验证码-is_mobile_bind为-2",user_account,password,"13516810156","-2",version,"3","success",0},
                {"获取验证码-is_mobile_bind为32",user_account,password,"13516810156","32",version,"3","success",0},
                {"获取验证码-is_mobile_bind为非数字",user_account,password,"13516810156","fsd",version,"3","success",0},
                {"获取验证码-is_mobile_bind为空",user_account,password,"13516810157","",version,"3","success",0},
                {"获取验证码-code_type为0",user_account,password,"13516810158","1",version,"0","success",0},
                {"获取验证码-code_type为2",user_account,password,"13516810159","1",version,"2","亲，该手机号还未出生，请重新输入",1000},
                {"获取验证码-code_type为564",user_account,password,"13516810160","1",version,"564","success",0},
                {"获取验证码-code_type为-8",user_account,password,"13516810161","1",version,"-8","success",0},
                {"获取验证码-code_type为非数字",user_account,password,"13516810162","1",version,"io","success",0},
                {"获取验证码-code_type为空",user_account,password,"13516810163","1",version,"","亲，该手机号还未出生，请重新输入",1000},
        };
        return data;
    }
}
