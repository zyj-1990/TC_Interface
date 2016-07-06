package tc.testcase.user;

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
import java.util.List;

/**
 * 首页关注医院
 * Created by zhaoyanji on 2016/6/29.
 */
public class AttEnt extends ZhaoyanjiConfig{
    List<Parameter> cdn = new ArrayList<Parameter>();
    String name = "杭口总院";

    @BeforeClass
    public void beforeClass() throws Exception{
        cdn.add(new Parameter("name",name));
        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        //查询医院当前是否被关注
        CommonOperation.cancleAtt(id,ent_id);
        CommonOperation.newIndex(id);
        String is_follow = SqlApi.sql_select_data(hospitalTable,"is_follow",cdn);
        Assert.assertEquals(is_follow,"","当前应还未关注");
    }

    @Test(dataProvider = "data")
    public void attEnt(String msg,String name,String id,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> cdn = new ArrayList<Parameter>();
        cdn.add(new Parameter("name",name));

        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/attEnt",null,null);
        System.out.println(res);
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @Test(dependsOnMethods = "attEnt")
    public void checkIfAttEnt() throws Exception{
//        //查询数据库，是否关注医院
        CommonOperation.newIndex(id);
        String is_follow = SqlApi.sql_select_data(hospitalTable,"is_follow",cdn);
        Assert.assertEquals(is_follow,"1","当前应已关注");

    }
    @AfterClass
    public void afterClass() throws Exception{
        //取消关注企业
        cdn.add(new Parameter("name",name));
        String ent_id = SqlApi.sql_select_data(hospitalTable,"ent_id",cdn);
        CommonOperation.cancleAtt(id,ent_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"关注医院",name,id,user_account,password,version,"success",0},
        };
        return data;
    }
}
