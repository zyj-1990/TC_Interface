//package tc.testcase.Index;
//
//import net.sf.json.JSONObject;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import tc.config.ZhaoyanjiConfig;
//import tc.helper.CommonApi;
//import tc.utils.Http;
//import tc.utils.HttpRequest;
//import tc.utils.Parameter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by zhaoyanji on 6/19/16.
// */
//public class Notices extends ZhaoyanjiConfig{
//    @BeforeClass
//    public void beforeClass() {
//
//    }
//
//    @Test(dataProvider = "data")
//    public void notices() throws Exception {
//        List<Parameter> headers = new ArrayList<Parameter>();
//        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
//        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));
//
//        List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("version",version));
//        paras.add(new Parameter("user_id",user_id));
//
//        Http httpRequest = new Http("get", paras, headers, null);
//        JSONObject res = HttpRequest.sendRequest(httpRequest, host, "/index/notices");
//        String err_msg = CommonApi.get_ErrorMsg(res);
//        System.out.println(res);
//        Assert.assertEquals(err_msg, "success", "获取通知消息接口调用不成功");
//    }
//
//    @AfterClass
//    public void afterClass() {
//
//    }
//
//    @DataProvider
//    public static Object[][] data(){
//        Object[][] data = null;
//        data = new Object[][]{
//                {},
//        };
//        return data;
//    }
//
//}
//TODO  接口暂时废弃