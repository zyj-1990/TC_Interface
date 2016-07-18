package tc.testcase.Weibo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
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
 * 转发动态
 * Created by zhaoyanji on 2016/7/18.
 */
public class Transpond extends ZhaoyanjiConfig{
    String w_id = "";
    String dept_id = "";
    String root_id = "";
    String parent_id = "";
    String createw_id = "";
    @BeforeClass
    public void addDynamic() throws Exception{
        JSONObject result = CommonOperation.addDynamic(ent_id,user_id,"content").getJSONObject("bizobj");
        w_id = result.getString("w_id");
        dept_id = result.getString("dept_id");
        root_id = result.getString("w_id");
        parent_id = result.getString("parent_id");
    }

    @Test(dataProvider = "data")
    public void transpond(String msg,String content,String ent_id,String dept_id,String root_id,String user_id,String type,String parent_id,String user_account,String password,String version,String exp_msg,int exp_code)throws Exception{
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("content",content));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("dept_id",dept_id));
        paras.add(new Parameter("root_id",root_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("type",type));
        paras.add(new Parameter("parent_id",parent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "weibo/transpond");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
        createw_id = res.getJSONObject("bizobj").getJSONObject("bizobj").getString("id");
    }

    @Test(dependsOnMethods = "transpond")
    public void checkIfTranspondSuccess()throws Exception{
        JSONArray jsonArr = CommonOperation.entList();
        Assert.assertTrue(CheckResult.checkIfInJsonArray("w_id",createw_id,jsonArr),"转发动态失败");

    }

    @AfterClass
    public void afterClass() throws Exception{
        //删除列表所有动态
        CommonOperation.delAllDynamic();
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"转发动态","content11",ent_id,dept_id,root_id,user_id,"2",parent_id,user_account,toMD5(password),version,"success",0},
        };
        return data;
    }

}
