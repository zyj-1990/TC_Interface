package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.helper.SqlApi;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/8.
 */
public class Reg extends ZhaoyanjiConfig{
    //TODO 注册新账号接口不能写成接口自动化，无法注销叽歪账号
    //可以删库，要写delete数据库方法，根据条件删除，与开发确认新注册账号都有哪些表新增了数据

    @Test(dataProvider = "data")
    public void attEnt(String msg,String mobile,String password,String nickname,String sex,String username,String version,String expMsg,int expCode) throws Exception {

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("nickname",nickname));
        paras.add(new Parameter("sex",sex));
        paras.add(new Parameter("username",username));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/reg",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() throws Exception{
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"用户注册","15158037968","a123456","nickname","0","名字",version,"success",0},
        };
        return data;
    }
}
