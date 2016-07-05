package tc.helper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.config.ZhaoyanjiConfig;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.*;

/**
 * Created by zhaoyanji on 2016/7/4.
 */
public class CommonOperation extends ZhaoyanjiConfig{
    /**
     *
     * @throws Exception
     */
    public static void in() throws Exception {
        List<Parameter> conditions = new ArrayList<Parameter>();

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("client_type","per"));
        paras.add(new Parameter("sys_type","2"));
        paras.add(new Parameter("personal_info","1"));
        paras.add(new Parameter("user_token","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef"));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host, "/login/in", null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        Assert.assertEquals(err_msg, "success", "登陆获取信息接口调用成功");
        Assert.assertEquals(err_code, 0, "登陆获取信息接口调用成功");
        if(err_msg.equals("success") && err_code == 0){
            //根据传入的JsonObject分解参数
            JSONArray jsonArr = res.getJSONArray("bizobj");
            //用户数据获取
            JSONObject user_info = jsonArr.getJSONObject(0);
            //用户加入企业，获取所加企业相关字段信息
            JSONArray ent_info = jsonArr.getJSONArray(1);
            //要传入几个key，作为判断的记录是否存在的条件
            Map key = new HashMap();
            key.put("user_id",null);
            CommonApi.setJsonArrToSql(ent_info,loginTable,key,conditions);
            CommonApi.setJsonObjectToSql(user_info,loginTable,key,conditions);
        }
    }

    /**
     *
     * @param nickname
     * @param g_type
     * @param is_open
     * @param g_id
     * @param is_ent
     * @param intro
     * @throws Exception
     */
    public static void create(String nickname,String g_type,String is_open,Long g_id,String is_ent,String intro) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("nick_name",nickname));
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
        Assert.assertEquals(err_msg,"success","创建叽歪群失败");
        Assert.assertEquals(err_code,0,"创建叽歪群失败");
    }


    public static JSONArray list() throws Exception {
        int pagesize = 100;
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account", user_account));
        paras.add(new Parameter("password", password));
        paras.add(new Parameter("version", version));
        paras.add(new Parameter("ent_id", ent_id));
        paras.add(new Parameter("user_id", user_id));
        paras.add(new Parameter("page", "1"));
        paras.add(new Parameter("pagesize", pagesize));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "address/list");
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        Assert.assertEquals(err_msg, "success", "获取通讯录数据失败");
        Assert.assertEquals(err_code, 0, "获取通讯录数据失败");

        JSONArray jsonArr = res.getJSONObject("bizobj").getJSONArray("addbook");
        return jsonArr;
    }

    /**
     *
     * @param nick_name
     * @param g_type
     * @param is_open
     * @param g_id
     * @param is_ent
     * @param intro
     * @throws Exception
     */
    public void create(String nick_name,String g_type,String is_open,String g_id,String is_ent,String intro) throws Exception {

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

        Http httpRequest = new Http("post", paras, headers, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/create",null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,"success","创建叽歪群失败");
        Assert.assertEquals(err_code,0,"创建叽歪群失败");
    }

    /**
     * 解散叽歪群
     * @param g_id
     * @throws Exception
     */
    public static void groupDismiss(Long g_id) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        System.out.println("start");
        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/groupDismiss",null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println("end");
        Assert.assertEquals(err_msg,"success","解散叽歪群失败");
        Assert.assertEquals(err_code,0,"解散叽歪群失败");

    }

    /**
     *
     * @param g_id
     * @throws Exception
     */
    public static JSONObject groupDetail(Long g_id) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "gchat/groupDetail",null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,"success","获取群组信息失败");
        Assert.assertEquals(err_code,0,"获取群组信息失败");

        return  res;
    }

    /**
     *
     * @param g_id
     * @throws Exception
     */
    public static JSONObject getUserInfoByGGId(Long g_id) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "gchat/getUserInfoByGGId");
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,"success","通过gid获取群聊成员信息失败");
        Assert.assertEquals(err_code,0,"通过gid获取群聊成员信息失败");

        return res;
    }

    public static int randomInt(int pagesize){
        Random ra =new Random();
        return ra.nextInt(pagesize);
    }
}