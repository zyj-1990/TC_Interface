//package tc.testcase.Feedback;
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
// * 获取反馈列表
// * Created by zhaoyanji on 2016/7/11.
// */
//public class Query extends ZhaoyanjiConfig{
//
//    @Test(dataProvider = "data")
//    public void query(String msg,String ent_id,String user_id,String is_reply,String order_type,String pagesize,String page,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
//        java.util.List<Parameter> paras = new ArrayList<Parameter>();
//        paras.add(new Parameter("ent_id",ent_id));
//        paras.add(new Parameter("user_id",user_id));
////        paras.add(new Parameter("is_reply",is_reply));
////        paras.add(new Parameter("order_type",order_type));
//        paras.add(new Parameter("page",page));
//        paras.add(new Parameter("pagesize",pagesize));
//        paras.add(new Parameter("user_account",user_account));
//        paras.add(new Parameter("password",password));
//        paras.add(new Parameter("version",version));
//
//        Http httpRequest = new Http("get", paras, null, null);
//        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"feedback/query");
//        CheckResult.checkResult(res,expCode,expMsg,msg);
//    }
//
//    @DataProvider
//    public Object[][] data(){
//        Object[][] data = null;
//        data = new Object[][]{
//                //is_reply:默认为0未回复，1已回复，-1不限制
//                //order_type:排序条件（1：默认-创建时间倒序；2：是否回复字段排前面）
//                {"获取反馈列表",ent_id,user_id,"-1","1","1","50",user_account,toMD5(password),version,"success",0},
//        };
//        return data;
//    }
//}
