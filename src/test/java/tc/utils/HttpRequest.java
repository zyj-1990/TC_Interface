package tc.utils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import tc.config.ZhaoyanjiConfig;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Created by zhaoyanji on 6/19/16.
 */
public class HttpRequest {
    static Logger logger = Logger.getLogger(HttpRequest.class);

    /**
     * 发送请求
     *
     * @param httpRequest
     * @param host
     * @param path
     * @param port
     * @return
     * @throws Exception
     */
    public static JSONObject search(Http httpRequest, String host, String path, int port) throws Exception {
        HttpUriRequest request = null;
        URIBuilder builder = new URIBuilder();
//        if (port == 0) {
//            builder.setScheme("http").setHost(host).setPath(path);
//        } else {
//            builder.setScheme("http").setHost(host).setPort(port).setPath(path);
//        }

        List<Parameter> paras = httpRequest.getParameters();
        if (paras != null) {
            for (Parameter p : paras) {
                builder.setParameter(p.getName(), p.getValue().toString());
            }
        }
        String url = builder.build().toString();

        if (httpRequest.getConnection().equalsIgnoreCase("post")) {
            logger.info("   [  post  ] " + url);
            request = new HttpPost(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("get")) {
            logger.info("   [  get   ] " + url);
            request = new HttpGet(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("delete")) {
            logger.info("   [ delete ] " + url);
            request = new HttpDelete(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
            logger.info("   [  put   ] " + url);
            request = new HttpPut(url);
        } else
            Assert.fail("connection donnot support :" + httpRequest.getConnection());
        // header builder
        List<Parameter> headers = httpRequest.getHeaders();
        if (headers != null) {
            for (Parameter p : headers) {
                logger.info("   [ Header ] " + String.format("%1$-20s :     %2$s", p.getName(), p.getValue()));
                request.addHeader(p.getName(), p.getValue().toString());
            }
        }
        // entity builder
        List<Entity> entities = httpRequest.getEntity();
        if (entities != null) {
            for (Entity e : entities) {
                StringEntity se = new StringEntity(e.getValue(), "utf-8");
                if (httpRequest.getConnection().equalsIgnoreCase("post")) {
                    HttpPost post = (HttpPost) request;
                    post.setEntity(se);
                } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
                    HttpPut put = (HttpPut) request;
                    put.setEntity(se);
                }
            }
        }
        DefaultHttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例

        //        X509TrustManager xtm = new X509TrustManager() {   //创建TrustManager
//            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//
//            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//        try {
//            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
//            SSLContext ctx = SSLContext.getInstance("TLS");
//
//            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
//            ctx.init(null, new TrustManager[]{xtm}, null);
//
//            //创建SSLSocketFactory
//            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
//
//            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
//            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
//
//        } catch (Exception e) {
//            logger.error("****************");
//        }
//        if (cookie != null) {
//            logger.info("cookie is " + cookie);
//            httpClient.setCookieStore(cookie);
//            logger.info("set cookie 成功");
//        }

        HttpResponse response = httpClient.execute(request);
        String resString = "";
        if (response.getEntity() != null)
            resString = EntityUtils.toString(response.getEntity(), "UTF-8");
        // 当没有可返回的JSONObject时，默认打印出Http响应码
        if (resString.isEmpty()) {
            int code = response.getStatusLine().getStatusCode();
            logger.info("INFO: Didn't get HTTP return entity, response code is: " + code);
            return null;
        } else {
            try {
                JSONObject responseJson = JSONObject.fromObject(resString);
                logger.info("   [Response] " + responseJson);
                return responseJson;
            } catch (JSONException e) {
                e.printStackTrace();
                logger.warn("INFO: Http response is NOT JSON format, it is " + resString);
                return null;
            }

        }
    }

    public static JSONObject sendRequest(Http httpRequest, String host, String path) throws Exception{
        HttpUriRequest request = null;
        String totalPath = path;
        URIBuilder builder = new URIBuilder();

        List<Parameter> paras = httpRequest.getParameters();
        if (paras != null) {
            for (Parameter p : paras) {
//                builder.setParameter(p.getName(), p.getValue().toString());
                totalPath = totalPath + "/" +p.getName() + "/" + p.getValue();
            }
        }
        System.out.println(totalPath);
        builder.setScheme("http").setHost(host).setPath(totalPath);
        String url = builder.build().toString();

        if (httpRequest.getConnection().equalsIgnoreCase("post")) {
            logger.info("   [  post  ] " + url);
            request = new HttpPost(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("get")) {
            logger.info("   [  get   ] " + url);
            request = new HttpGet(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("delete")) {
            logger.info("   [ delete ] " + url);
            request = new HttpDelete(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
            logger.info("   [  put   ] " + url);
            request = new HttpPut(url);
        } else
            Assert.fail("connection donnot support :" + httpRequest.getConnection());
        // header builder
        List<Parameter> headers = httpRequest.getHeaders();
        if (headers != null) {
            for (Parameter p : headers) {
                logger.info("   [ Header ] " + String.format("%1$-20s :     %2$s", p.getName(), p.getValue()));
                request.addHeader(p.getName(), p.getValue().toString());
            }
        }
        // entity builder
        List<Entity> entities = httpRequest.getEntity();
        if (entities != null) {
            for (Entity e : entities) {
                StringEntity se = new StringEntity(e.getValue(), "utf-8");
                if (httpRequest.getConnection().equalsIgnoreCase("post")) {
                    HttpPost post = (HttpPost) request;
                    post.setEntity(se);
                } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
                    HttpPut put = (HttpPut) request;
                    put.setEntity(se);
                }
            }
        }
        DefaultHttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例

        HttpResponse response = httpClient.execute(request);
        String resString = "";
        if (response.getEntity() != null)
            resString = EntityUtils.toString(response.getEntity(), "UTF-8");
        // 当没有可返回的JSONObject时，默认打印出Http响应码
        if (resString.isEmpty()) {
            int code = response.getStatusLine().getStatusCode();
            logger.info("INFO: Didn't get HTTP return entity, response code is: " + code);
            return null;
        } else {
            try {
                JSONObject responseJson = JSONObject.fromObject(resString);
                logger.info("   [Response] " + responseJson);
                return responseJson;
            } catch (JSONException e) {
                e.printStackTrace();
                logger.warn("INFO: Http response is NOT JSON format, it is " + resString);
                return null;
            }

        }
    }


    public static JSONObject sendRequest_EntityOrParas(Http httpRequest, String host, String path) throws Exception{
        HttpUriRequest request = null;
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(host).setPath(path);

        List<Parameter> paras = httpRequest.getParameters();
        if (paras != null) {
            for (Parameter p : paras) {
                builder.setParameter(p.getName(), p.getValue().toString());
            }
        }
        String url = builder.build().toString();
        System.out.println(url);

        if (httpRequest.getConnection().equalsIgnoreCase("post")) {
            logger.info("   [  post  ] " + url);
            request = new HttpPost(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("get")) {
            logger.info("   [  get   ] " + url);
            request = new HttpGet(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("delete")) {
            logger.info("   [ delete ] " + url);
            request = new HttpDelete(url);
        } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
            logger.info("   [  put   ] " + url);
            request = new HttpPut(url);
        } else
            Assert.fail("connection donnot support :" + httpRequest.getConnection());
        // header builder
        List<Parameter> headers = httpRequest.getHeaders();
        if (headers != null) {
            for (Parameter p : headers) {
                logger.info("   [ Header ] " + String.format("%1$-20s :     %2$s", p.getName(), p.getValue()));
                request.addHeader(p.getName(), p.getValue().toString());
            }
        }
        // entity builder
        List<Entity> entities = httpRequest.getEntity();
        if (entities != null) {
            for (Entity e : entities) {
                StringEntity se = new StringEntity(e.getValue(), "utf-8");
                if (httpRequest.getConnection().equalsIgnoreCase("post")) {
                    HttpPost post = (HttpPost) request;
                    post.setEntity(se);
                } else if (httpRequest.getConnection().equalsIgnoreCase("put")) {
                    HttpPut put = (HttpPut) request;
                    put.setEntity(se);
                }
            }
        }
        DefaultHttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例

        HttpResponse response = httpClient.execute(request);
        String resString = "";
        if (response.getEntity() != null)
            resString = EntityUtils.toString(response.getEntity(), "UTF-8");
        // 当没有可返回的JSONObject时，默认打印出Http响应码
        if (resString.isEmpty()) {
            int code = response.getStatusLine().getStatusCode();
            logger.info("INFO: Didn't get HTTP return entity, response code is: " + code);
            return null;
        } else {
            try {
                JSONObject responseJson = JSONObject.fromObject(resString);
                logger.info("   [Response] " + responseJson);
                return responseJson;
            } catch (JSONException e) {
                e.printStackTrace();
                logger.warn("INFO: Http response is NOT JSON format, it is " + resString);
                return null;
            }

        }
    }

}
