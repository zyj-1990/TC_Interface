package tc.testcase.SysMsg;

import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.utils.CheckResult;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;

/**
 * 个人消息列表
 * Created by zhaoyanji on 2016/7/14.
 */

public class List extends ZhaoyanjiConfig{
    @Test(dataProvider = "data")
    public void list(String msg,String ent_id,String user_id,String msg_type,String is_read,String update_time,String direction,String page,String pageSize,String user_account,String password,String version,String expMsg,int expCode) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("msg_type",msg_type));
        paras.add(new Parameter("is_read",is_read));
        paras.add(new Parameter("update_time",update_time));
        paras.add(new Parameter("direction",direction));
//        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pageSize",pageSize));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"sysMsg/list");
        CheckResult.checkResult(res,expCode,expMsg,msg);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //msg_type:消息类型（1公告消息、2@提醒、3评论提醒、4关注提醒），默认全部
                //is_read:是否已读（1是0否），默认全部
                //update_time:更新时间，默认为0取当前最新列表
                //direction:默认1（取>update_time）2（取<update_time），若update_time为0则direction无效
                {"个人消息列表",ent_id,user_id,"1","1","0","1","1","50",user_account,password,version,"success",0},
        };
        return data;
    }
}
