package tc.testcase.yinxiu;

import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tc.helper.CommonOperation;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/20.
 */
public class IndexPage extends CommonOperation{
    String yx_urlPath = "";
    String ent_id = "";
    @BeforeClass
    public void getUrl() throws Exception{
        JSONObject result = newIndex(user_id);
        yx_urlPath = getUrlPath("com",getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","Hibeauty隐秀","wifi_website").toString());
        ent_id = getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","Hibeauty隐秀","ent_id").toString();
    }

    @Test(dataProvider = "data")
    public void indexPage(String msg,String wifiMobile,String wecha_id,String user_id,String ent_id) throws Exception{
        //利用cunji_url跳转页面，获取的html用jsoup验证页面的元素及跳转页面信息是否错误。
        List<Parameter> headers = new ArrayList<Parameter>();
        headers.add(new Parameter("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        headers.add(new Parameter("Content-Type","text/html; charset=utf-8"));

        List<Parameter> paras = new ArrayList<Parameter>();
        paras.add(new Parameter("wifiMobile",wifiMobile));
        paras.add(new Parameter("wecha_id",wecha_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));

        Http httpRequest = new Http("get", paras, headers, null);
        String res = "";
        System.out.println(yx_urlPath);
        res = HttpRequest.sendRequest_GetHTML(httpRequest,YinXiuHost,yx_urlPath);
        Document doc = Jsoup.parse(res);
        System.out.println(doc);
        Assert.assertEquals(doc.title(),"隐秀移动官网","内容错误");
        Elements elements = doc.getElementsByClass("menubox");
        //为什么手机上进隐秀，返回页面后面带了wecha_id，调接口或者网页，都没带wecha_id             &wecha_id=4739fcb7-0dca-f6a2-fc50-6b286b61e7b6
        Assert.assertEquals(elements.select("a").get(0).attr("href"),"/index.php?g=Wap&m=YinXiuOut&a=about&token=76f66f3b-9775-4500-bb06-f8aec63e1cf1&linkArg=0","走进隐秀网址错误");
        Assert.assertEquals(elements.select("em").get(0).text(),"走进隐秀","内容错误");
        Assert.assertEquals(elements.select("a").get(1).attr("href"),"/index.php?g=Wap&m=Index&a=lists&classid=608&token=76f66f3b-9775-4500-bb06-f8aec63e1cf1","典型病例网址错误");
        Assert.assertEquals(elements.select("em").get(1).text(),"典型病例","内容错误");
        Assert.assertEquals(elements.select("a").get(2).attr("href"),"/index.php?g=Wap&m=YinXiuUserCenter&a=personHome&token=76f66f3b-9775-4500-bb06-f8aec63e1cf1","会员中心网址错误");
        Assert.assertEquals(elements.select("em").get(2).text(),"会员中心","内容错误");
        Assert.assertEquals(elements.select("a").get(3).attr("href"),"/index.php?g=Wap&m=YinXiuOut&a=store&token=76f66f3b-9775-4500-bb06-f8aec63e1cf1","隐秀中心网址错误");
        Assert.assertEquals(elements.select("em").get(3).text(),"隐秀中心","内容错误");

        Elements urls = doc.select("a");
        CheckAllUrlsValidUnderCurPage(urls,httpRequest,YinXiuHost);
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"Hibeauty隐秀医院首页",wecha_id,wecha_id,user_id,ent_id},
        };
        return data;
    }

}
