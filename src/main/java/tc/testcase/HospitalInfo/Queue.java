package tc.testcase.HospitalInfo;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.SqlApi;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 排队信息
 * Created by zhaoyanji on 2016/7/7.
 */
public class Queue extends ZhaoyanjiConfig{
    List<Parameter> cdn = new ArrayList<Parameter>();
    String name = "杭口城西分院";



    @Test(dataProvider = "data")
    public void queue(String msg,String name,String page,String page_size,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        cdn.add(new Parameter("name",name));

        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("page_size",page_size));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"HospitalInfo/Team",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"排队信息",name,"1","50",user_account,password,version,"success",0},
        };
        return data;
    }
}
