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

/**
 * Created by zhaoyanji on 6/24/16.
 * 修改密码
 */
public class UpdatePwd extends ZhaoyanjiConfig  {
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void updatePwd(String msg,String user_account,String global_user_id,String ori_pwd,String password,String confirm_pwd,String new_pwd,String version,String exp_msg,int exp_code) throws Exception {
        java.util.List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("ori_pwd",ori_pwd));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("confirm_pwd",confirm_pwd));
        paras.add(new Parameter("new_pwd",new_pwd));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "user/updatePwd");
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
                {"修改密码",user_account,"a123456",global_user_id,CommonApi.toMD5("a123456"),"a123456","a123456","100000","success",0},
        };
        return data;
    }
}
