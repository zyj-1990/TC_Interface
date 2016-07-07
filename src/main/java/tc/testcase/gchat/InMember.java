package tc.testcase.Gchat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
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
 * 邀请叽歪群成员
 * Created by zhaoyanji on 2016/6/23.
 */
public class InMember extends ZhaoyanjiConfig{
    //生成一个g_id
    static Long g_id = CommonApi.get_UnixTime();
    List<String> expNickname = new ArrayList<String>();

    @BeforeClass
    public void beforeClass() throws Exception{
        //创建一个叽歪群
        CommonOperation.create("空","1","1",g_id,"1","啦啦啦");
    }

    @Test
    public void CheckIfGroupCreated() throws Exception {
        List<Parameter> conditions = new ArrayList<Parameter>();
        conditions.add(new Parameter("user_id",user_id));
        JSONObject res = CommonOperation.getUserInfoByGGId(g_id);
        JSONArray jsonArr = res.getJSONArray("bizobj");
        Assert.assertEquals(jsonArr.getJSONObject(0).getString("nick_name"), SqlApi.sql_select_data(loginTable,"name",conditions),"创建群组失败");
    }

    @Test(dataProvider = "data",dependsOnMethods = "CheckIfGroupCreated")
    public void inMember(String msg,String ent_id,String user_account,Long g_id,String password,String version,String exp_msg,int exp_code) throws Exception {
        JSONArray jsonArr = CommonOperation.list();
        List<Map> user_info = new ArrayList<Map>();

        for(int i = 0; i< 3; i++) {
            Map m = new HashMap();
            JSONObject obj = jsonArr.getJSONObject(CommonOperation.randomInt(100));
            m.put("nick_name",obj.getString("user_name"));
            expNickname.add(i,obj.getString("user_name"));
            m.put("user_id",obj.getString("user_id"));
            m.put("global_user_id",obj.getString("global_user_id"));
            user_info.add(m);
        }

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("user_info",JSONArray.fromObject(user_info).toString()));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/inMember",null,null);
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @Test(dependsOnMethods = "inMember")
    public void CheckIfMemberInGroup() throws Exception {
        //获取当前群组的成员
        JSONObject res = CommonOperation.getUserInfoByGGId(g_id);
        JSONArray jsonArr = res.getJSONArray("bizobj");
        for(int i = 0; i< 3; i++){
            Assert.assertEquals(jsonArr.getJSONObject(i+1).getString("nick_name"),expNickname.get(i),"添加成员不在群聊中");
        }
    }

    @AfterClass
    public void afterClass() throws Exception{
        //解散一个叽歪群
        CommonOperation.groupDismiss(g_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"邀请叽歪群成员",ent_id,user_account,g_id,password,version,"success",0},
        };
        return data;
    }
}
