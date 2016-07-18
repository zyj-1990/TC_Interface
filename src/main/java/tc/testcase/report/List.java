package tc.testcase.Report;

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
 * 检查报告列表-按品牌(暂不支持分页)
 * Created by zhaoyanji on 2016/7/11.
 */
public class List extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void list(String msg,String global_user_id,String brand_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {

        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("brand_id",brand_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"report/list");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 找到这个接口在哪调用，造数据，删除生成数据
                {"预约列表",global_user_id,"2",user_account,password,version,"success",0},
        };
        return data;
    }
}
