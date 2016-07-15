package tc.testcase.Attention;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.SqlApi;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;
import java.util.*;

/**
 * 关注用户/取消关注
 * Created by zhaoyanji on 2016/7/14.
 */
public class Att extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void att(String msg,String ent_id,String user_id,String attent_user_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("attent_user_id",attent_user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"attention/att",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass(){

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 关注接口的user_id，为什么会报出不同的错出来
                {"关注用户/取消关注",ent_id,user_id,"180017",user_account,password,version,"success",0},
        };
        return data;
    }
}
