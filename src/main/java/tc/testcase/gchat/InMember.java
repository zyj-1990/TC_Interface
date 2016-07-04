package tc.testcase.gchat;

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
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 邀请叽歪群成员
 * Created by zhaoyanji on 2016/6/23.
 */
public class InMember extends ZhaoyanjiConfig{
    //生成一个g_id
    static Long g_id = CommonApi.get_UnixTime();

    @BeforeClass
    public void beforeClass() throws Exception{
        //创建一个叽歪群
        CommonOperation.create("空","1","1",g_id,"1","啦啦啦");
    }

    @Test
    public void CheckIfGroupCreated() throws Exception {

    }

    @Test(dataProvider = "data",dependsOnMethods = "CheckIfGroupCreated")
    public void inMember(String msg,String ent_id,String user_account,Long g_id,String password,String version,String exp_msg,int exp_code) throws Exception {
        JSONArray jsonArr = CommonOperation.list();
        Map user_info = new HashMap();
        for(int i = 0; i< 3; i++) {
            JSONObject obj = jsonArr.getJSONObject(CommonOperation.randomInt(100));
            Map m = new HashMap();
            m.put("nick_name",obj.getString("user_name"));
            m.put("user_id",obj.getString("user_id"));
            m.put("global_user_id",obj.getString("global_user_id"));
            user_info.putAll(m);
        }
        Map m = new HashMap();
        m.put("ent_id",ent_id);
        m.put("user_account",user_account);
        m.put("g_id",g_id);
        m.put("user_info",user_info);
        m.put("password",password);
        m.put("version",version);

        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", null, null, entities);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/inMember",null,null);
        System.out.println("res:    " + res);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,exp_msg,msg);
        Assert.assertEquals(err_code,exp_code,msg);
    }

    @Test(dependsOnMethods = "inMember")
    public void CheckIfMemberInGroup() throws Exception {
        //获取当前群组的成员
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
