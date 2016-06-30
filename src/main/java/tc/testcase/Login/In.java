package tc.testcase.Login;

import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.config.ZhaoyanjiConfig;
import tc.helper.CommonApi;
import tc.utils.Entity;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyanji on 2016/6/27.
 * 登陆获取信息
 */
public class In extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }

    @Test(dataProvider = "data")
    public void in(String msg,String user_account,String password,String version,String client_type,String sys_type,String personal_info,String user_token,String expMsg,int expCode) throws Exception {
        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("user_account",user_account));
        paras.add(new Parameter("password",password));
        paras.add(new Parameter("version",version));
        paras.add(new Parameter("client_type",client_type));
        paras.add(new Parameter("sys_type",sys_type));
        paras.add(new Parameter("personal_info",personal_info));
        paras.add(new Parameter("user_token",user_token));

        Http httpRequest = new Http("post", paras, null, null);
        JSONObject res = HttpRequest.sendMultiPartRequest(httpRequest,host, "/login/in", null,null);
        String err_msg = CommonApi.get_ErrorMsg(res);
        int err_code = CommonApi.get_ErrorCode(res);
        System.out.println(res);
        Assert.assertEquals(err_msg, expMsg, msg);
        Assert.assertEquals(err_code, expCode, msg);
        System.out.println(err_msg);
        System.out.println(err_code);
        if(err_msg.equals("success") && err_code == 0){
            System.out.println("get in");
            CommonApi.setParasToSql(res);
        }
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"登陆获取信息接口",user_account,password,version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","success",0},
//                {"登陆获取信息接口-账号不存在","13516810157",password,version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-账号为空","",password,version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号不能为空",1002},
//                {"登陆获取信息接口-账号格式错误","1351681015",password,version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-密码为空",user_account,"",version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","密码不能为空",1002},
//                {"登陆获取信息接口-密码错误",user_account,"a123456789",version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-密码为特殊字符",user_account,"！@#￥%……&*",version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-密码长度超过16位",user_account,"a123456a123456789",version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-密码短于6位",user_account,password,version,"per","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                //TODO 所有报错，都是提示账号\密码有误，请检查后重新输入
//                {"登陆获取信息接口-clientType为空",user_account,password,version,"","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-clientType内容不合要求",user_account,password,version,"perxxx","2","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-sys_type为空",user_account,password,version,"perxxx","","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-sys_type非数字",user_account,password,version,"perxxx","upup","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-sys_type数字不合法",user_account,password,version,"perxxx","99","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-sys_type数字为1",user_account,password,version,"perxxx","1","1","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-personal_info为空",user_account,password,version,"perxxx","1","","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-personal_info非数字",user_account,password,version,"perxxx","1","upoup","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-personal_info数字不合法",user_account,password,version,"perxxx","1","999","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-personal_info数字为2",user_account,password,version,"perxxx","1","2","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-personal_info数字为0",user_account,password,version,"perxxx","1","0","4245fc927e51bf813d4c077315a73de8094db29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-user_token值为空",user_account,password,version,"perxxx","1","0","","账号\\密码有误，请检查后重新输入",1003},
//                {"登陆获取信息接口-user_token值为非法",user_account,password,version,"perxxx","1","0","4245fc927e51bf813d4c077315a73de8094dfsdfsfsb29f1a56122236dedb9b707530ef","账号\\密码有误，请检查后重新输入",1003},
        };
        return data;
    }
}
