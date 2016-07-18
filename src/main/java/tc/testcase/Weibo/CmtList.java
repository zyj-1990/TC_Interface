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
 * 评论列表
 * Created by zhaoyanji on 2016/7/18.
 */
public class CmtList extends ZhaoyanjiConfig{
    String w_id = "";
    @BeforeClass
    public void addDynamic() throws Exception{
        w_id = CommonOperation.addDynamic(ent_id,user_id,"content").getJSONObject("bizobj").getString("w_id");
    }

    @Test
    public void checkIfAdded(){}

    @Test
    public void addComment(){

    }

    @Test(dataProvider = "data")
    public void cmtList(String msg,String user_account,String password,String version,String ent_id,String entity_type,String w_id,String page,String pagesize,String exp_msg,int exp_code) throws Exception{
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("entity_type",entity_type));
        paras.add(new Parameter("w_id",w_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pagesize",pagesize));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "weibo/cmtList");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void delDynamicAndCheck(){

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"评论列表",user_account,toMD5(password),version,ent_id,"1",w_id,"1","50","success",0},
        };
        return data;
    }
}
