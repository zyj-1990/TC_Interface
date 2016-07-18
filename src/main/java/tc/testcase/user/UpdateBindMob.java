package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 6/24/16.
 * 修改绑定手机
 */
public class UpdateBindMob extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        //
    }

    @Test(dataProvider = "data")
    public void updateBindMob(String msg,String global_user_id,String user_id,String mobile,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        String verify_code = CommonOperation.getVerifyCode(mobile,"1","3");
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("verify_code",verify_code));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/updateBindMob",null,null);

        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, expMsg, msg);
        Assert.assertEquals(err_code, expCode, msg);
    }

    @Test(dataProvider = "data",dependsOnMethods = "updateBindMob")
    public void checkIfBindMobileChanged(String msg,String global_user_id,String user_id,String mobile,String user_account,String password,String version,String expMsg,int expCode) throws Exception{
        CommonOperation.updateBindMob(global_user_id,user_account,mobile);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"修改绑定手机",global_user_id,user_id,"15158038000",user_account,password,version,"success",0},
        };
        return data;
    }
}
