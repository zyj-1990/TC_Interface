package tc.testcase.hangkou;

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
        cunji_urlPath = getUrlPath(getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","杭口总院","wifi_website").toString());
        ent_id = getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","杭口总院","ent_id").toString();
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

        Http httpRequest = new Http("get", paras, null, null);
        String res = "";
        if(cunji_urlPath.contains("?")){
            String[] str = cunji_urlPath.split("[?]");
            cunji_urlPath = str[0];
            String path = str[1];
            res = HttpRequest.sendRequest_GetHTML(httpRequest,CunjiHost,cunji_urlPath,path);
        }else{
            res = HttpRequest.sendRequest_GetHTML(httpRequest,CunjiHost,cunji_urlPath);
        }
        Document doc = Jsoup.parse(res);
        Elements elements = doc.getElementsByClass("firstPage-jw-modules");
        Assert.assertEquals(elements.select("a").get(0).attr("href"),"/out/out_h5/wifi/index.php?r=wifi/citys","快速预约网址错误");
        Assert.assertEquals(elements.select("a").get(0).text(),"快速预约","内容错误");
        Assert.assertEquals(elements.select("a").get(1).attr("href"),"/out/out_h5/wifi/index.php?r=wifi/index","医院官网网址错误");
        Assert.assertEquals(elements.select("a").get(1).text(),"医院官网","内容错误");
        Assert.assertEquals(elements.select("a").get(2).attr("href"),"/out/out_h5/wifi/index.php?r=my/index","个人中心网址错误");
        Assert.assertEquals(elements.select("a").get(2).text(),"个人中心","内容错误");
    }

    @DataProvider
    public Object[][] data(){
        Object[][] data = null;
        data = new Object[][]{
                {"杭口总院医院首页",wecha_id,wecha_id,user_id,ent_id},
        };
        return data;
    }

}
