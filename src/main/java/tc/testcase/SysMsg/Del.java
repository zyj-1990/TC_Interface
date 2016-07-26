//package tc.testcase.SysMsg;
//
//import net.sf.json.JSONObject;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import tc.config.ZhaoyanjiConfig;
//import tc.utils.CheckResult;
//import tc.utils.Http;
//import tc.utils.HttpRequest;
//import tc.utils.Parameter;
//
//import java.util.ArrayList;
//
///**
// * 删除消息
// * Created by zhaoyanji on 2016/7/14.
// */
//public class Del extends ZhaoyanjiConfig{
//    //msgid从List的返回参数中获取，要先创建对应的数据出来，再将其删除。
//    String msg_id = "";
//    @Test(dataProvider = "data")
//    public void del(String msg,String ent_id,String user_id,String msg_id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
//        java.util.List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("ent_id",ent_id));
//        paras.add(new Parameter("user_id",user_id));
//        paras.add(new Parameter("msg_id",msg_id));
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("version",version));
//
//        Http httpRequest = new Http("post", paras, null, null);
//        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"sysMsg/del",null,null);
//        CheckResult.checkResult(res,expCode,expMsg,msg);
//    }
//
//    @DataProvider
//    public Object[][] data(){
//        Object[][] data = null;
//        data = new Object[][]{
//                {"删除消息",ent_id,user_id,msg_id,user_account,password,version,"success",0},
//        };
//        return data;
//    }
//}
