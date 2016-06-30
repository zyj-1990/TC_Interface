package tc.testcase.gchat;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyanji on 2016/6/23.
 */
public class Create extends ZhaoyanjiConfig{

    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void create(String msg,String nick_name,String ent_id,String user_account,String g_type,String is_open,String global_user_id,String g_id,String user_id,String password,String is_ent,String intro,String version,String exp_msg,int exp_code) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "x-www-form-urlencoded"));
        headers.add(new Parameter("Content-Type", "multipart/form-data; boundary=Boundary+88BFDEA6704849E0"));

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
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,exp_msg,msg);
        Assert.assertEquals(err_code,exp_code,msg);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"正常创建群聊","空","72720","13516810150","1","1","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6","1466584697899","180011","dc483e80a7a0bd9ef71d8cf973673924","1","啦啦啦","100000","success",0,},
        };
        return data;
    }
}
