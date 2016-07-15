package tc.testcase.Weibo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/14.
 */
public class Praise extends ZhaoyanjiConfig{
    String addEnt_id = "";
    String addUser_id = "";
    String user_name = "";
    String w_id = "";
    @BeforeClass
    public void beforeClass() throws Exception{
        //根据手机号码获取其他相关信息
        JSONArray jsonArr = CommonOperation.list();
        System.out.println(jsonArr);
        addUser_id = CommonApi.getOrderInfo(jsonArr,"mobile_phone","15158037967","user_id").toString();
        System.out.println("user_id " + user_id);
        addEnt_id = CommonApi.getOrderInfo(jsonArr,"mobile_phone","15158037967","ent_id").toString();
        System.out.println("ent_id :" + ent_id);
        user_name = CommonApi.getOrderInfo(jsonArr,"mobile_phone","15158037967","user_name").toString();
        //创建动态
        w_id = CommonOperation.addDynamic(addEnt_id,addUser_id,"点赞专用").getJSONObject("bizobj").getString("w_id");
        System.out.println(w_id);
    }

    @Test(dataProvider = "data")
    public void praise(String msg,String user_id,String ent_id,String author_id,String user_name,String w_id,String user_account,String password,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("author_id",author_id));
        paras.add(new Parameter("user_name",user_name));
        System.out.println("w_id+++++++++++++++++++++++++++++++++++++++++++++++++++" + w_id);
        paras.add(new Parameter("w_id",w_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "weibo/praise",null,null);
        System.out.println("start");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
        System.out.println("end");
    }

    @AfterClass
    public void afterClass() throws Exception{
        //删除动态
        CommonOperation.delDynamic(addEnt_id,w_id,addUser_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"添加反馈",user_id,ent_id,addUser_id,user_name,w_id,user_account,toMD5(password),version,"success",0},
        };
        return data;
    }
}
