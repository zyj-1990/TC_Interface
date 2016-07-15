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
 * 动态详情
 * Created by zhaoyanji on 2016/7/13.
 */
public class Detail extends ZhaoyanjiConfig{
    String id = "";
    @BeforeClass
    public void beforeClass() throws Exception{
        id = CommonOperation.addDynamic(ent_id,user_id,"动态内容").getJSONObject("bizobj").getString("w_id");
    }

    @Test(dataProvider = "data")
    public void detail(String msg,String user_account,String password,String version,String ent_id,String user_id,String id,String entity_type,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("entity_type",entity_type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "weibo/detail");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() throws Exception{
        //缺一个删除动态的方法
        CommonOperation.delDynamic(ent_id,id,user_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"动态详情",user_account,toMD5(password),version,ent_id,user_id,id,"1","success",0},
        };
        return data;
    }
}
