package tc.testcase.Weibo;

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
 * 删除动态
 * Created by zhaoyanji on 2016/7/13.
 */
public class Del extends ZhaoyanjiConfig{
    String w_id = "";
    @BeforeClass
    public void beforeClass() throws Exception{
        w_id = CommonOperation.addDynamic(ent_id,user_id,"content").getJSONObject("bizobj").getString("w_id");
    }

    @Test(dataProvider = "data")
    public void del(String msg,String ent_id,String w_ids,String op_user_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("w_ids",w_ids));
        paras.add(new Parameter("op_user_id",op_user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/del",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass(){
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"删除动态",ent_id,w_id,user_id,user_account,password,version,"success",0},
        };
        return data;
    }
}
