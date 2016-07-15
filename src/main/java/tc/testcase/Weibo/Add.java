package tc.testcase.Weibo;

import net.sf.json.JSONObject;
import org.testng.annotations.AfterClass;
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
 * 发布动态
 * Created by zhaoyanji on 2016/7/11.
 */
public class Add extends ZhaoyanjiConfig{
    String w_id = null;

    @Test(dataProvider = "data")
    public void add(String msg,String ent_id,String user_id,String content,String type,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("content",content));
        paras.add(new Parameter("type",type));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        int sum = CommonOperation.randomInt(9);
        for(int i = 0; i < sum; i++ ){
            paras.add(new Parameter("upfile["+ i +"]",CommonOperation.upLoadPic()));
        }
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/add",null,null);
        CheckResult.checkResult(res,expCode,expMsg,msg);
        w_id = res.getJSONObject("bizobj").getString("w_id");
    }

    @AfterClass()
    public void afterClass() throws Exception{
        CommonOperation.delDynamic(ent_id,w_id,user_id);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"发布动态",ent_id,user_id,"hello","2",user_account,password,version,"success",0},
        };
        return data;
    }

}
