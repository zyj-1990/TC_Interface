//package tc.testcase.Weibo;
//
//import net.sf.json.JSONObject;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import tc.config.ZhaoyanjiConfig;
//import tc.helper.CommonOperation;
//import tc.utils.CheckResult;
//import tc.utils.Http;
//import tc.utils.HttpRequest;
//import tc.utils.Parameter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 阅读动态全文
// * Created by zhaoyanji on 2016/7/13.
// */
//public class Fulltext extends ZhaoyanjiConfig{
//    String w_id = "";
//    @BeforeClass
//    public void beforeClass() throws Exception{
//        w_id = CommonOperation.addDynamic(ent_id,user_id,"动态内容").getJSONObject("bizobj").getString("w_id");
//    }
//
//    @Test(dataProvider = "data")
//    public void fulltext(String msg,String user_account,String password,String version,String ent_id,String w_id,String exp_msg,int exp_code) throws Exception {
//        List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("version",version));
//        paras.add(new Parameter("ent_id",ent_id));
//        paras.add(new Parameter("w_id",w_id));
//
//        Http httpRequest = new Http("get", paras, headers, null);
//        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "weibo/fulltext");
//        System.out.println("res++++++++++++++++++++++++++++" + res);
//        CheckResult.checkResult(res,exp_code,exp_msg,msg);
//    }
//
//    @AfterClass
//    public void afterClass() {
//        //缺一个删除动态的方法
//
//    }
//
//    @DataProvider
//    public Object[][] data(){
//        Object[][] data = null;
//        data = new Object[][]{
//                {"阅读动态全文",user_account,toMD5(password),version,ent_id,w_id,"success",0},
//        };
//        return data;
//    }
//}
