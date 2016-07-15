package tc.testcase.Weibo;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业动态列表
 * Created by zhaoyanji on 2016/7/13.
 */
public class EntList extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
    }

    @Test(dataProvider = "data")
    public void list(String msg,String user_account,String password,String version,String ent_id,String user_id,String update_time,String direction,String show_disable,String show_notice,String show_comment,String page,String pagesize,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("update_time",update_time));
        paras.add(new Parameter("direction",direction));
        paras.add(new Parameter("show_disable",show_disable));
        paras.add(new Parameter("show_notice",show_notice));
        paras.add(new Parameter("show_comment",show_comment));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pagesize",pagesize));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "weibo/entList");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"企业动态列表",user_account,toMD5(password),version,ent_id,user_id,"0","1","2","1","1","1","50","success",0},
        };
        return data;
    }
}
