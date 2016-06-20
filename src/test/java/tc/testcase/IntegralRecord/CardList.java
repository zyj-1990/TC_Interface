package tc.testcase.IntegralRecord;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class CardList extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test
    public void cardList() throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("name",name));
        paras.add(new Parameter("mobile",mobile));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest(httpRequest, host, "/integral/cardList");
        String err_msg = CommonApi.get_ErrorMsg(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, "success", "积分兑换接口调用不成功");
    }

    @AfterClass
    public void afterClass() {

    }
}
