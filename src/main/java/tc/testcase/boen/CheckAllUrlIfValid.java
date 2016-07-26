package tc.testcase.boen;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tc.helper.CommonOperation;
import tc.utils.Http;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/26.
 */
public class CheckAllUrlIfValid extends CommonOperation{
    List<Parameter> paras = new ArrayList<Parameter>();

    /**
     * 要用java编写一个栈方法
     * 1.首先要有一个初始的url，发起请求，从返回结果中取得html页面
     * 2.解析返回的html页面，判断是否包含url
     * 3.如果包含url，获取结果中的所有url，循环执行每个url，将当前执行的host入栈
     * 4.如果结果中包含url，则重复步骤1
     * 5.如果不包含url，则顺序执行该父节点下的其他url，
     */
    @BeforeClass
    public void init(){
        headers = setHeaders("Content-Type","text/html; charset=utf-8","Content-Type");
        paras.add(new Parameter("wifiMobile",wecha_id));
        paras.add(new Parameter("wecha_id",wecha_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
    }

    @Test
    public void checkAllUrlIfValid (){
        //发起请求，
    }


    public void test(){

    }
}
