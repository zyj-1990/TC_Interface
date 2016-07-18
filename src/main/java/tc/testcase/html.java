package tc.testcase;

import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
 * Created by zhaoyanji on 2016/6/28.
 */
public class html extends ZhaoyanjiConfig{
    @BeforeClass
    public void beforeClass() {

    }
    //http://ectest.zotohui.com/index.php?g=Wap&m=Member&a=cardCenter&token=64fc77ae-28ee-4833-8df5-1f19ea22a430&wecha_id=4739fcb7-0dca-f6a2-fc50-6b286b61e7b6
    @Test(dataProvider = "data")
    public void html(String msg,String g,String m,String a,String token,String wecha_id) throws Exception {
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"));
        headers.add(new Parameter("Content-Type", "application/x-www-form-urlencoded"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("g",g));
        paras.add(new Parameter("m",m));
        paras.add(new Parameter("a",a));
        paras.add(new Parameter("token",token));
        paras.add(new Parameter("wecha_id",wecha_id));

        Http httpRequest = new Http("get", paras, headers, null);
        String  res = HttpRequest.sendRequest_GetHTML(httpRequest, "ectest.zotohui.com", "/index.php");
        System.out.println(res);
        Document doc = Jsoup.parse(res);
        Element e = doc.getElementById("J_SubmitData");
//        doc.getElementsByTag();

        String linkHref = e.attr("href");
        String data_url = e.attr("data-url");
        String linkText = e.text();
        System.out.println("linkHref : " + linkHref);
        System.out.println("linkText : " + linkText);
        System.out.println("data-url : " + data_url);
    }

    @AfterClass
    public void afterClass() {

    }

    @DataProvider
    public static Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                //TODO 接口测试用例还未写完整
                {"关闭特色列表接口-账号为空","Wap","Member","cardCenter","64fc77ae-28ee-4833-8df5-1f19ea22a430","4739fcb7-0dca-f6a2-fc50-6b286b61e7b6"},
        };
        return data;
    }
}
