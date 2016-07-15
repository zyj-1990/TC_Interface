package tc.testcase.Notice;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;

/**
 * 公告列表
 * Created by zhaoyanji on 2016/7/14.
 */
public class List extends ZhaoyanjiConfig{
    String an_id = "";
    @Test(dataProvider = "data")
    public void list(String msg,String ent_id,String page,String pageSize,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pageSize",pageSize));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"notice/list");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"公告列表",ent_id,"1","50",user_account,password,version,"success",0},
        };
        return data;
    }
}
