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
 * Created by zhaoyanji on 2016/6/27.
 */
public class In extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void in(String msg,String user_account,String password,String version,String client_type,String sys_type,String personal_info,String user_token,String expMsg,int expCode) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "multipart/form-data; boundary=Boundary+6EBBDD6EA85A4457"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("client_type",client_type));
        paras.add(new Parameter("sys_type",sys_type));
        paras.add(new Parameter("personal_info",personal_info));
        paras.add(new Parameter("user_token",user_token));

        JSONObject res = HttpRequest.sendMultiPartRequest("http://" + host + "/login/in", paras, null,null);
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
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 接口测试用例还未写完整
//                {"关闭特色列表接口-账号为空","","dc483e80a7a0bd9ef71d8cf973673924","100000",mobile_uid,"success",0},
                {"登陆获取信息接口",user_account,"a123456","100000","per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","success",0},
//                {"关闭特色列表接口-账号或者密码错误","18668462782","dc483e80a7a0bd9ef71d8cf973673112924","100000",mobile_uid,"success",0},
        };
        return data;
    }
}
