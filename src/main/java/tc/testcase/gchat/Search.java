//package tc.testcase.Gchat;
//该接口不存在
//import net.sf.json.JSONObject;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import tc.config.ZhaoyanjiConfig;
//import tc.utils.CheckResult;
//import tc.utils.Http;
//import tc.utils.HttpRequest;
//import tc.utils.Parameter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 搜索叽歪群
// * Created by zhaoyanji on 2016/7/4.
// */
//public class Search extends ZhaoyanjiConfig{
//    @Test(dataProvider = "data")
//    public void search(String msg,String ent_id,String user_account,String user_id,String password,String nickname,String g_type,String version,String exp_msg,int exp_code) throws Exception {
//        List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("ent_id",ent_id));
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("nick_name",nickname));
//        paras.add(new Parameter("user_id",user_id));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("g_type",g_type));
//        paras.add(new Parameter("version",version));
//
//        Http httpRequest = new Http("get", paras, headers, null);
//        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "gchat/search");
//        CheckResult.checkResult(res,exp_code,exp_msg,msg);
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
//                {"搜索叽歪群",ent_id,user_account,user_id,toMD5(password),"nickname","1",version,"success",0},
//        };
//        return data;
//    }
//}
