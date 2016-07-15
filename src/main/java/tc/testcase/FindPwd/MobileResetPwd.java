package tc.testcase.FindPwd;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机重置密码
 * Created by zhaoyanji on 2016/7/11.
 */
public class MobileResetPwd extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void mobileResetPwd(String msg,String mobile,String new_password,String confirm_password,String version,String exp_msg,int exp_code) throws Exception {
        String code = CommonOperation.getVerifyCode(mobile,null,"2");
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("code",code));
        paras.add(new Parameter("new_password",new_password));
        paras.add(new Parameter("confirm_password",confirm_password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "findPwd/mobileResetPwd",null,null);
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"手机重置密码",user_account,"a123456","a123456",version,"success",0},
        };
        return data;
    }
}
