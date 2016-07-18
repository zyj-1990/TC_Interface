package tc.testcase.FindPwd;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;

/**
 * Created by zhaoyanji on 6/24/16.
 * 重新绑定获取验证码
 */
public class GetVerifyCode extends ZhaoyanjiConfig{

    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void getVerifyCode(String msg,String mobile,String is_mobile_bind,String code_type,String version,String exp_msg,int exp_code) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("is_mobile_bind",is_mobile_bind));
        paras.add(new Parameter("version",version));
        //验证码类型，2：找回密码（默认）、3：手机号码验证、4：其他
        paras.add(new Parameter("code_type",code_type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "findPwd/getVerifyCode");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //验证码类型，2：找回密码（默认）、3：手机号码验证、4：其他
                {"获取验证码","15158037969","1","3",version,"success",0},
        };
        return data;
    }
}
