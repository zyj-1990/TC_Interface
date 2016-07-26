package tc.helper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import tc.config.ZhaoyanjiConfig;
import tc.utils.*;

import java.io.File;
import net.sf.json.JSONObject;
import tc.config.ZhaoyanjiConfig;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * Created by zhaoyanji on 2016/7/4.
 */
public class CommonOperation extends ZhaoyanjiConfig{
    public static StringBuilder temp = new StringBuilder("[]");

    /**
     *
     * @throws Exception
     */
    public static void in(String user_account,String password) throws Exception {
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
        CheckResult.checkResult(res,0,"success","登陆获取信息接口调用成功");
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
        CheckResult.checkResult(res,0,"success","创建叽歪群失败");
    }
    public static JSONArray list() throws Exception {
        int pagesize = 1000;
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
        CheckResult.checkResult(res,0,"success","获取通讯录数据失败");
        JSONArray jsonArr = res.getJSONObject("bizobj").getJSONArray("addbook");
        return jsonArr;
    }

    public static void addMemberAndCheckIfIn(String ent_id,String user_account,Long g_id) throws Exception {
        List<String> expNickname = new ArrayList<String>();

        JSONArray jsonArr = CommonOperation.list();
        List<Map> user_info = new ArrayList<Map>();

        for(int i = 0; i< 3; i++) {
            Map m = new HashMap();
            JSONObject obj = jsonArr.getJSONObject(CommonOperation.randomInt(100));
            m.put("nick_name",obj.getString("user_name"));
            expNickname.add(i,obj.getString("user_name"));
            m.put("user_id",obj.getString("user_id"));
            m.put("global_user_id",obj.getString("global_user_id"));
            user_info.add(m);
        }

        Map m = new HashMap();
        m.put("nick_name",getOrderInfo(jsonArr,"mobile_phone",user_account,"user_name"));
        expNickname.add(3,getOrderInfo(jsonArr,"mobile_phone",user_account,"user_name").toString());
        m.put("user_id",getOrderInfo(jsonArr,"mobile_phone",user_account,"user_id"));
        m.put("global_user_id",getOrderInfo(jsonArr,"mobile_phone",user_account,"global_user_id"));
        user_info.add(m);

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("g_id",g_id));
        paras.add(new Parameter("user_info",JSONArray.fromObject(user_info).toString()));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host , "gchat/inMember",null,null);
        CheckResult.checkResult(res,0,"success","群组添加成员成功");

        //获取当前群组的成员
        JSONObject result = CommonOperation.getUserInfoByGGId(g_id);
        JSONArray jsonArr1 = result.getJSONArray("bizobj");
        for(int i = 0; i< 3; i++){
            Assert.assertEquals(jsonArr1.getJSONObject(i+1).getString("nick_name"),expNickname.get(i),"添加成员不在群聊中");
        }
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
        CheckResult.checkResult(res,0,"success","解散叽歪群失败");
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
        CheckResult.checkResult(res,0,"success","获取群组信息失败");
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
        CheckResult.checkResult(res,0,"success","通过gid获取群聊成员信息失败");
        return res;
    }

    /**
     *
     * @param mobile
     * @param is_mobile_bind
     * @param code_type
     * @return
     * @throws Exception
     */
    public static String getVerifyCode(String mobile,String is_mobile_bind,String code_type) throws Exception {
        //验证码类型，2：找回密码（默认）、3：手机号码验证、4：其他
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("mobile",mobile));
        if(is_mobile_bind != null){
            paras.add(new Parameter("is_mobile_bind",is_mobile_bind));
        }
        paras.add(new Parameter("code_type",code_type));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "findPwd/getVerifyCode");
        CheckResult.checkResult(res,0,"success","获取验证码失败");
        return res.getJSONObject("bizobj").getString("verify_code");
    }

    /**
     *
     * @param global_user_id
     * @param mobile
     * @param user_account
     * @throws Exception
     */
    public static void updateBindMob(String global_user_id,String mobile,String user_account) throws Exception {
        String verify_code = CommonOperation.getVerifyCode(mobile,"1","3");

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("global_user_id",global_user_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("mobile",mobile));
        paras.add(new Parameter("verify_code",verify_code));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",toMD5(password)));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"user/updateBindMob",null,null);
        CheckResult.checkResult(res,0,"success","换绑手机号码失败");
    }

    public static void att(String ent_id,String user_id,String attent_user_id) throws Exception {
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("attent_user_id",attent_user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"attention/att");
        CheckResult.checkResult(res,0,"success","关注/取消关注用户");
    }

    /**
     *
     * @param name
     * @param ent_id
     * @throws Exception
     */
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

        CheckResult.checkResult(res,0,"success","关注医院失败");
    }

    /**
     *
     * @param ent_id
     * @param id
     * @throws Exception
     */
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

    /**
     *
     * @param birthday
     * @param address
     * @param user_name
     * @param nickname
     * @param sex
     * @throws Exception
     */
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

    /**
     *
     * @param user_account
     * @param password
     * @param version
     * @param mobile_uid
     * @return
     * @throws Exception
     */
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

    /**
     *
     * @param mobile_uid
     * @return
     * @throws Exception
     */
    public static JSONObject newIndex(String mobile_uid) throws Exception {
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

    /**
     *
     * @param name
     * @param mobile
     * @return
     * @throws Exception
     */
    public static JSONObject cardList(String name,String mobile) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account", user_account));
        paras.add(new Parameter("password", password));
        paras.add(new Parameter("version", version));
        paras.add(new Parameter("global_user_id", global_user_id));
        paras.add(new Parameter("name", name));
        paras.add(new Parameter("mobile", mobile));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest(httpRequest, host, "/integral/cardList");
        CheckResult.checkResult(res, 0, "success", "获取积分会员卡的列表失败");
        return res;
    }

    public static JSONArray entList() throws Exception{
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("update_time","0"));
        paras.add(new Parameter("direction","1"));
        paras.add(new Parameter("show_disable","2"));
        paras.add(new Parameter("show_notice","1"));
        paras.add(new Parameter("show_comment","1"));
        paras.add(new Parameter("page","1"));
        paras.add(new Parameter("pagesize","50"));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "weibo/entList");
        CheckResult.checkResult(res,0,"success","获取企业动态列表失败");
        return res.getJSONArray("bizobj");
    }

    /**
     *
     * @param ent_id
     * @param user_id
     * @param content
     * @return
     * @throws Exception
     */
    public static JSONObject addDynamic(String ent_id,String user_id,String content) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("content",content));
        paras.add(new Parameter("type","2"));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        int sum = CommonOperation.randomInt(9);
        for(int i = 0; i < sum; i++ ){
            paras.add(new Parameter("upfile["+ i +"]",CommonOperation.upLoadPic()));
        }
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/add",null,null);
        CheckResult.checkResult(res,0,"success","发布动态失败");
        return res;
    }

    /**
     *
     * @param ent_id
     * @param w_ids
     * @param op_user_id
     * @throws Exception
     */
    public static void delDynamic(String ent_id,String w_ids,String op_user_id) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("w_ids",w_ids));
        paras.add(new Parameter("op_user_id",op_user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/del",null,null);
        CheckResult.checkResult(res,0,"success","删除动态");
    }


    public static void delAllDynamic() throws Exception {
        JSONArray jsonArr = entList();

        for(int i = 0; i < jsonArr.size(); i++){
            delDynamic(jsonArr.getJSONObject(i).getString("ent_id"),jsonArr.getJSONObject(i).getString("w_id"),jsonArr.getJSONObject(i).getString("user_id"));
        }
    }

    public static JSONObject addFeedBack(String user_id,String ent_id,String content) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("content",content));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest, host, "feedback/add",null,null);
        CheckResult.checkResult(res,0,"success","添加反馈失败");
        return res;
    }

    public static String upLoadPic() throws Exception{
        String pic_url = null;
        //本地res下存文件，文件名命名为数字，10个，随机抽取1个图片文件
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator ;
        //调用upload接口上传图片
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        String fileName = randomInt(9) + ".jpg";
        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host,"weibo/upload","file",new File(filePath + fileName));
        CheckResult.checkResult(res,0,"success","上传图片失败");
        //将返回结果的图片在服务器的地址返回回去
        pic_url = res.getJSONObject("bizobj").getString("url");
        return pic_url;
    }

    public static JSONObject noticeList(String ent_id) throws  Exception{
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"notice/list");
        CheckResult.checkResult(res,0,"success","获取公告列表失败");
        return res;
    }

    public static JSONObject noticeList(String ent_id,String page,String pageSize) throws  Exception{
        java.util.List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("ent_id",ent_id));
        paras.add(new Parameter("page",page));
        paras.add(new Parameter("pageSize",pageSize));
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));

        Http httpRequest = new Http("get", paras, null, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest,host,"notice/list");
        CheckResult.checkResult(res,0,"success","获取公告列表失败");
        return res;
    }

    public static String createUrl(String url){
        String tempUrl = splitUrl(url);
        if(temp.length() == 2){
            temp.insert(temp.length()-1,"\"" +tempUrl + "\"");
        }else{
            temp.insert(temp.length()-1,"," + "\"" +tempUrl + "\"");
        }
        return temp.toString();
    }

    public static String splitUrl(String url){
        String[] str =url.split("entity");
        System.out.println(str[1]);
        return "/entity" + str[1];
    }

    public static String getUrlPath(String pattern,String url){
        String tempUrl = "";
        System.out.println(url);
        if(url.substring(0,4).equals("http")){
            tempUrl = url.split(pattern)[1];
            System.out.println(tempUrl);
            System.out.println( url.split(pattern)[0]);
        }
        return tempUrl;
    }

    public static String time(String type) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("type",type));

        Http httpRequest = new Http("get", paras, headers, null);
        JSONObject res = HttpRequest.sendRequest_EntityOrParas(httpRequest, host, "/index/Time");
        CheckResult.checkResult(res,0,"success","获取服务器时间戳失败");
        return res.getJSONObject("bizobj").getString("time");
    }

    public static int randomInt(int pagesize){
        Random ra =new Random();
        return ra.nextInt(pagesize);
    }
}
