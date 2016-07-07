package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.helper.SqlApi;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取消关注医院
 * Created by zhaoyanji on 2016/6/29.
 */
public class CancleAtt extends ZhaoyanjiConfig{
    List<Parameter> cdn = new ArrayList<Parameter>();
    String name = "杭口总院";

    @BeforeClass
    public void beforeClass() throws Exception{
        cdn.add(new Parameter("name",name));
        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        //查询医院当前是否被关注
        CommonOperation.attEnt(name,ent_id);
        JSONObject res = CommonOperation.newIndex(id);
        String is_follow = CommonOperation.getOrderInfo(res.getJSONObject("bizobj").getJSONArray("ent_list"),"name",name,"is_follow").toString();
        Assert.assertEquals(is_follow,"1","当前应关注");
    }

    @Test(dataProvider = "data")
    public void cancleAtt(String msg,String id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        cdn.add(new Parameter("name",name));
        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/cancleAtt",null,null);

        CheckResult.checkResult(res,expCode,expMsg,msg);
        try{
            CommonOperation.getOrderInfo(res.getJSONObject("bizobj").getJSONArray("ent_list"),"name",name,"is_follow");
        }catch(Exception e){
            Map m = new HashMap();
            m.put("is_follow","");
            SqlApi.sql_update(hospitalTable,m,cdn);
        }
    }

    @AfterClass
    public void checkIfCancleAtt() throws Exception{
            //查询数据库，是否关注医院
        CommonOperation.newIndex(mobile_uid);
        String is_follow = SqlApi.sql_select_data(hospitalTable,"is_follow",cdn);
        Assert.assertEquals(is_follow,"","当前应取消关注");
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"取消关注医院",id,user_account,password,version,"success",0},
        };
        return data;
    }

}
