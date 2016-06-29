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
                {"获取验证码",user_account,password,"13516810155","1","100000","3","success",0},
        };
        return data;
    }
}
