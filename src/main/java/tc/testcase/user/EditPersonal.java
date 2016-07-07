package tc.testcase.User;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 6/24/16.
 * 编辑个人信息
 */
public class EditPersonal extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {
        //
    }

    @Test(dataProvider = "data")
    public void editPersonal(String msg,String id,String birthday,String address,String user_name,String nickname,Integer sex,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("id",id));
        if(birthday != null){
            paras.add(new Parameter("birthday",birthday));
        }
        if(address != null){
            paras.add(new Parameter("address",address));
        }
        if(user_name != null){
            paras.add(new Parameter("user_name",user_name));
        }
        if(nickname != null){
            paras.add(new Parameter("nickname",nickname));
        }
        if(sex != null){
            paras.add(new Parameter("sex",sex));
        }
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/editPersonal",null,null);

        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @AfterClass
    public void afterClass() throws Exception{
        //修改了的字段值要改回去
        CommonOperation.editPersonal("1990-08-13","","ITTestAccount","nickname",0);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"编辑用户名字",mobile_uid,"2016-06-20","","hha","nickname",1,user_account,password,version,"success",0},
        };
        return data;
    }
}
