package tc.testcase.IntegralRecord;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.helper.SqlApi;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 购买积分
 * Created by zhaoyanji on 2016/7/8.
 */
public class Buy extends ZhaoyanjiConfig{
    //TODO 购买不了积分，不知道key，和待加密的字符串
    @Test(dataProvider = "data")
    public void add(String msg,String global_user_id,String mobile,String str,String amount,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        String time = CommonOperation.time("1");
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("mobile",mobile));

        paras.add(new Parameter("str",str));
        paras.add(new Parameter("amount",amount));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"Appointments/Add",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void checkIfAdded(){

    }


    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"购买积分",global_user_id,user_account,"","100",user_account,password,version,"success",0},
        };
        return data;
    }
}
