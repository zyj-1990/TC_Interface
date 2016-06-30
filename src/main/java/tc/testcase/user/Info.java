//package tc.testcase.user;
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
// * Created by zhaoyanji on 6/24/16.
// * 接口暂时掉不通，废弃
// */
//public class Info extends ZhaoyanjiConfig{
//
//    @BeforeClass
//    public void beforeClass() {
//
//    }
//
//    @Test(dataProvider = "data")
//    public void info(String msg,String ent_id,String user_id,String user_account,String password,String version,String exp_msg,int exp_code) throws Exception {
//        List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("ent_id",ent_id));
//        paras.add(new Parameter("user_id",user_id));
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("version",version));
//
//        Http httpRequest = new Http("get", paras, headers, null);
//        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "user/info");
//        String err_msg = CommonApi.get_ErrorMsg(res);
//        int err_code = CommonApi.get_ErrorCode(res);
//        System.out.println(res);
//        Assert.assertEquals(err_msg,exp_msg,msg);
//        Assert.assertEquals(err_code,exp_code,msg);
//    }
//
//    @AfterClass
//    public void afterClass() {
//
//    }
//
//    @DataProvider
//    public Object[][] data(){
//        Object[][] data = null;
//        data = new Object[][]{
//                {"获取验证码","-1","-1",user_account,"1","100000","success",0},
//        };
//        return data;
//    }
//}
