package tc.testcase.Appointments;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.SqlApi;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/7.
 */
public class Add extends ZhaoyanjiConfig{
    List<Parameter> cdn = new ArrayList<Parameter>();
    String name = "杭口城西分院";

    @Test(dataProvider = "data")
    public void patientInfo(String msg,String ent_name,String username,String time,String mobile,String symptom,String customerAge,String remark,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        cdn.add(new Parameter("name",ent_name));

        CommonApi.getDaysAgo(1);

        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("name",username));
        paras.add(new Parameter("time",time));
        paras.add(new Parameter("mobile",mobile));
        String append = "";
        paras.add(new Parameter("append",append));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"Appointments/Add",null,null);

        CheckResult.checkResult(res,expCode,expMsg,msg);

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"团队列表",name,"患者名字","1990-8-13","15158037967","","","",user_account,password,version,"success",0},
        };
        return data;
    }
}
