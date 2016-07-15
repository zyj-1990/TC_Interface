package tc.testcase.Notice;

import net.sf.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.*;

/**
 * 公告详情
 * Created by zhaoyanji on 2016/7/14.
 */
public class Detail extends ZhaoyanjiConfig{
    String an_id = "";

    @BeforeClass
    public void beforeClass() throws Exception{
        JSONObject result = CommonOperation.noticeList(ent_id);
        int num = result.getJSONObject("page_info").getInt("total_items");
        an_id = result.getJSONArray("bizobj").getJSONObject(CommonOperation.randomInt(num)).getString("an_id");
    }

    @Test(dataProvider = "data")
    public void detail(String msg,String ent_id,String an_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("an_id",an_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"notice/detail");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"公告详情",ent_id,an_id,user_account,password,version,"success",0},
        };
        return data;
    }
}
