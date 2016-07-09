package tc.helper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.config.ZhaoyanjiConfig;
import tc.utils.*;
import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.config.ZhaoyanjiConfig;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/groupDismiss",null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
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

    public static String getVerifyCode(String mobile,String is_mobile_bind,String code_type) throws Exception {
        //验证码类型，2：找回密码（默认）、3：手机号码验证、4：其他
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("is_mobile_bind",is_mobile_bind));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("code_type",code_type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "findPwd/getVerifyCode");
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg,"success","");
        Assert.assertEquals(err_code,0,"");
        return res.getJSONObject("bizobj").getString("verify_code");
    }

    public static void updateBindMob(String global_user_id,String mobile,String user_account,String passworde) throws Exception {
        String verify_code = CommonOperation.getVerifyCode(mobile,"1","3");

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("verify_code",verify_code));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        System.out.println(paras);

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/updateBindMob",null,null);

        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, "success", "换绑手机号码失败");
        Assert.assertEquals(err_code,0, "换绑手机号码失败");
    }



    public static void attEnt(String name,String ent_id) throws Exception {
        List<Parameter> cdn = new ArrayList<Parameter>();
        cdn.add(new Parameter("name",name));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/attEnt",null,null);

        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        Assert.assertEquals(err_msg, "success", "关注医院失败");
        Assert.assertEquals(err_code, 0, "关注医院失败");
    }

    public static void cancleAtt(String ent_id,String id) throws Exception {
        List<Parameter> cdn = new ArrayList<Parameter>();
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("id",id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/cancleAtt",null,null);

        CheckResult.checkResult(res,0,"success","取消关注医院失败");
        try{
            CommonOperation.getOrderInfo(res.getJSONObject("bizobj").getJSONArray("ent_list"),"name",name,"is_follow");
        }catch(Exception e){
            Map m = new HashMap();
            m.put("is_follow","");
            SqlApi.sql_update(hospitalTable,m,cdn);
        }
    }

    public static void editPersonal(String birthday,String address,String user_name,String nickname,Integer sex) throws Exception {
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
        CheckResult.checkResult(res,0,"success","修改用户个人信息失败");
    }

    public static JSONObject newIndex(String user_account, String password, String version, String mobile_uid) throws Exception {
        setHeaders();

        Map m = new HashMap();
        m.put("user_account",user_account);
        m.put("password",password);
        m.put("version",version);
        m.put("mobile_uid",mobile_uid);
        List<Parameter> paras = JsonConvert.mapToKV(m);
        List<Parameter> conditions = new ArrayList<Parameter>();
        String data = JSONObject.fromObject(m).toString();
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(data));

        Http httpRequest = new Http("post", paras, null,null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "/index/NewIndex",null,null);
        CheckResult.checkResult(res,0,"success","首页获取信息失败");

        Map key = new HashMap();
        key.put("ent_id",null);
        JSONArray jsonArr = res.getJSONObject("bizobj").getJSONArray("ent_list");
        CommonApi.setJsonArrToSql(jsonArr,hospitalTable,key,conditions);
        return res;
    }

    public static int randomInt(int pagesize){
        Random ra =new Random();
        return ra.nextInt(pagesize);
    }
}




