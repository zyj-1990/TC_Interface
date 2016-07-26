package tc.testcase.cunji;

import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
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
 * Created by zhaoyanji on 2016/7/19.
 */
public class IndexPage extends CommonOperation{
    String cunji_urlPath = "";
    String ent_id = "";
    @BeforeClass
    public void getUrl() throws Exception{
        JSONObject result = newIndex(user_id);
        cunji_urlPath = getUrlPath("com",getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","存济网络医院","wifi_website").toString());
        ent_id = getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","存济网络医院","ent_id").toString();
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
        res = HttpRequest.sendRequest_GetHTML(httpRequest,CunjiHost,cunji_urlPath);

        Document doc = Jsoup.parse(res);
        Assert.assertEquals(doc.title(),"存济网络医院","页面跳转正确");
        Elements elements = doc.getElementsByClass("menu");
        Assert.assertEquals(elements.select("a").get(0).attr("href"),"/index.php?g=Wap&m=Savaid&a=addappointment&token=ed57a8bd-15cc-d9b1-6a1e-97ec43337684","快速预约网址错误");
        Assert.assertEquals(elements.select("h4").get(0).text(),"快速预约","内容错误");
        Assert.assertEquals(elements.select("a").get(1).attr("href"),"/index.php?g=Wap&m=Savaid&a=taobaolist&token=ed57a8bd-15cc-d9b1-6a1e-97ec43337684","生育检查网址错误");
        Assert.assertEquals(elements.select("h4").get(1).text(),"生育检查","内容错误");
        Assert.assertEquals(elements.select("a").get(2).attr("href"),"/index.php?g=Wap&m=Savaid&a=procreatelist&token=ed57a8bd-15cc-d9b1-6a1e-97ec43337684","生殖中心网址错误");
        Assert.assertEquals(elements.select("h4").get(2).text(),"生殖中心","内容错误");
        Assert.assertEquals(elements.select("a").get(3).attr("href"),"/index.php?g=Wap&m=SavaidDoctor&a=doctorList&token=ed57a8bd-15cc-d9b1-6a1e-97ec43337684","专家团队网址错误");
        Assert.assertEquals(elements.select("h4").get(3).text(),"专家团队","内容错误");
        Assert.assertEquals(elements.select("a").get(4).attr("href"),"http://bbssavaid.eetopintest.com/forum.php","助孕社区网址错误");
        Assert.assertEquals(elements.select("h4").get(4).text(),"助孕社区","内容错误");
        Assert.assertEquals(elements.select("a").get(5).attr("href"),"/index.php?g=Wap&m=Savaid&a=guidelist&token=ed57a8bd-15cc-d9b1-6a1e-97ec43337684","就诊指南网址错误");
        Assert.assertEquals(elements.select("h4").get(5).text(),"就诊指南","内容错误");

        Elements urls = doc.select("a");
        CheckAllUrlsValidUnderCurPage(urls,httpRequest,CunjiHost);
        System.out.println(urls.size());

    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"存济网络医院首页",wecha_id,wecha_id,user_id,ent_id},
        };
        return data;
    }

}
