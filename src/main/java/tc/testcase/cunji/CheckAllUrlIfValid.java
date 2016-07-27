package tc.testcase.cunji;

import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tc.helper.CommonOperation;
import tc.utils.Http;
import tc.utils.HttpRequest;
import tc.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyanji on 2016/7/26.
 */
public class CheckAllUrlIfValid extends CommonOperation {
    List<Parameter> paras = new ArrayList<Parameter>();
    String path = "";
    Elements urls = null;
    /**
     * 要用java编写一个栈方法
     * 1.首先要有一个初始的url，发起请求，从返回结果中取得html页面
     * 2.解析返回的html页面，判断是否包含url
     * 3.如果包含url，获取结果中的所有url，循环执行每个url，将当前执行的host入栈
     * 4.如果结果中包含url，则重复步骤1
     * 5.如果不包含url，则顺序执行该父节点下的其他url，
     */
    @BeforeClass
    public void init() throws Exception{
        headers = setHeaders("Content-Type","text/html; charset=utf-8","Content-Type");
        paras.add(new Parameter("wifiMobile",wecha_id));
        paras.add(new Parameter("wecha_id",wecha_id));
        paras.add(new Parameter("user_id",user_id));
        paras.add(new Parameter("ent_id",ent_id));
        JSONObject result = newIndex(user_id);
        path = getUrlPath("com",getOrderInfo(result.getJSONObject("bizobj").getJSONArray("ent_list"),"name","存济网络医院","wifi_website").toString());
        urls = sendUrl(path);
        System.out.println("url : " + urls);
    }

    @Test
    public void test() throws Exception{
        checkAllUrlIfValid(urls);
    }

    public void checkAllUrlIfValid(Elements urls) throws Exception {
        //发起请求
//        Elements urls = sendUrl(path);
        System.out.println("start");
        System.out.println("urls.size : " + urls.size());
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i).attr("href");
            Elements elements = sendUrl(url);
            if(elements != null){
                System.out.println("href :" + elements.attr("href"));
                if (elements.attr("href") != null){
                    System.out.println("start1");
                    checkAllUrlIfValid(elements);
                    System.out.println("end1");
                }
            }
        }
    }


    public Elements sendUrl(String path) throws Exception {
        Http httpRequest = new Http("get", paras, headers, null);
        String res = "";
        res = HttpRequest.sendRequest_GetHTML(httpRequest, CunjiHost, path);
        if (res != null) {
            Document doc = Jsoup.parse(res);
            Elements urls = doc.select("a");
            return urls;
        }
        return null;
    }

}
