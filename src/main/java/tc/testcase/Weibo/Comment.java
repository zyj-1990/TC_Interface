package tc.testcase.Weibo;

import net.sf.json.JSONObject;
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
 * 评论动态
 * Created by zhaoyanji on 2016/7/18.
 */
public class Comment extends ZhaoyanjiConfig{
    String w_id = "";

    @BeforeClass
    public void addDynamic() throws Exception{
        w_id = CommonOperation.addDynamic(ent_id,user_id,"content").getJSONObject("bizobj").getString("w_id");
    }

    @Test(dataProvider = "data")
    public void comment(String msg,String w_id,String ent_id,String user_id,String content,String reply_user_id,String toid,String is_send,String parent_id,String root_id,String user_account,String password,String version,String exp_msg,int exp_code)throws Exception{
        String images = "";
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("w_id",w_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("content",content));
        paras.add(new Parameter("reply_user_id",reply_user_id));
        paras.add(new Parameter("toid",toid));
        paras.add(new Parameter("is_send",is_send));
        paras.add(new Parameter("parent_id",parent_id));
        paras.add(new Parameter("root_id",root_id));
        int sum = CommonOperation.randomInt(9);
//        for(int i = 0; i < sum; i++ ){
//            String url =CommonOperation.upLoadPic();
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ " + url);
//            images = CommonOperation.createUrl(url);
//        }
        String url =CommonOperation.upLoadPic();
        images = CommonOperation.createUrl(url);

        paras.add(new Parameter("imgs",images));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "weibo/comment");
        CheckResult.checkResult(res,exp_code,exp_msg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"评论动态",w_id,ent_id,user_id,"content11","","","0","","",user_account,toMD5(password),version,"success",0},
        };
        return data;
    }
}
