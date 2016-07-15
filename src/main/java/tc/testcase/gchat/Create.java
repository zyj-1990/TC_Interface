package tc.testcase.Gchat;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.helper.CommonOperation;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建叽歪群
 * Created by zhaoyanji on 2016/6/23.
 */
public class Create extends ZhaoyanjiConfig{
    //生成一个g_id
    static Long g_id = CommonApi.get_UnixTime();
    @BeforeClass
    public void beforeClass() {
        CommonApi.getCommonValueFromSql();
    }

    @Test(dataProvider = "data")
    public void create(String msg,String nick_name,String ent_id,String user_account,String g_type,String is_open,String global_user_id,String user_id,String password,String is_ent,String intro,String version,String exp_msg,int exp_code) throws Exception {

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("nick_name",nick_name));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("g_type",g_type));
        paras.add(new Parameter("is_open",is_open));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("is_ent",is_ent));
        paras.add(new Parameter("intro",intro));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/create",null,null);
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @AfterClass
    public void afterClass() throws Exception{
        CommonOperation.groupDismiss(g_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"创建叽歪群","空",ent_id,user_account,"1","1",global_user_id,user_id,toMD5(password),"1","啦啦啦",version,"success",0,},
        };
        return data;
    }
}
