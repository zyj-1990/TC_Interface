package tc.testcase.Weibo;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonOperation;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传图片
 * Created by zhaoyanji on 2016/7/14.
 */
public class Upload extends ZhaoyanjiConfig{

    @Test(dataProvider = "data")
    public void upload(String msg,String ent_id,String user_id,String fileName,String user_account,String password,String version,String expMsg,int expCode) throws Exception{
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator ;
        //调用upload接口上传图片
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/upload","file",new File(filePath + fileName));
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"上传图片",ent_id,user_id,"1.jpg",user_account,password,version,"success",0},
        };
        return data;
    }

}
