package tc.testcase.IntegralRecord;

import net.sf.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分兑换
 * Created by zhaoyanji on 2016/7/8.
 */
public class Exchange extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider =  "data")
    public void exchange(String msg,String global_user_id,String aims,String mobile,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        JSONObject temp = CommonOperation.cardList("帅比名",mobile);
        JSONArray jsonArr = temp.getJSONArray("bizobj");
        JSONArray list = null;
        Map m = new HashMap();
        m.put("where",jsonArr.getJSONObject(0).getJSONObject("where").toString());
        m.put("cardid",jsonArr.getJSONObject(0).getString("cardid"));
        m.put("mark",jsonArr.getJSONObject(0).getString("mark"));
        m.put("aims",aims);

        list = JSONArray.fromObject(m);
        System.out.println("list" + list);
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("list",list));
        paras.add(new Parameter("mobile",mobile));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "/integral/Exchange",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() {
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"积分兑换",global_user_id,"100",user_account,user_account,toMD5(password),version,"success",0},
        };
        return data;
    }
}
