package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑个人资料
 * Created by zhaoyanji on 2016/7/15.
 */
public class Edit extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        //
    }

    @Test(dataProvider = "data")
    public void edit(String msg,String user_id,String emp_id,String ent_id,String emp_email,String user_name,String avatar,String spell,String dept_id,String sex,String birthday,String qq,String tel,String mobile_phone,String introduce,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        CommonOperation.in("15158037967","a123456");
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("emp_id",emp_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_name",user_name));
        paras.add(new Parameter("spell",spell));
        paras.add(new Parameter("dept_id",dept_id));
        if(emp_email != null){
            paras.add(new Parameter("emp_email",emp_email));
        }
        if(avatar != null){
            paras.add(new Parameter("avatar",avatar));
        }
        if(sex != null){
            paras.add(new Parameter("sex",sex));
        }
        if(birthday != null){
            paras.add(new Parameter("birthday",birthday));
        }
        if(qq != null){
            paras.add(new Parameter("qq",qq));
        }
        if(tel != null){
            paras.add(new Parameter("tel",tel));
        }
        if(mobile_phone != null){
            paras.add(new Parameter("mobile_phone",mobile_phone));
        }
        if(introduce != null){
            paras.add(new Parameter("introduce",introduce));
        }
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/edit",null,null);

        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() throws Exception{
        //修改了的字段值要改回去
//        CommonOperation.editPersonal("1990-08-13","","ITTestAccount","nickname",0);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"编辑个人资料-恢复个人资料初始状态","180011","74176","72720","","ITTestAccount","","zyj","1004","","","","","","",user_account,password,version,"success",0},
        };
        return data;
    }
}
