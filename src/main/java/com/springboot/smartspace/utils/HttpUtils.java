package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/6 16:53
 * @description: get，post方法的实现
 */
@Slf4j
@Component
public class HttpUtils {
    //定义一个可关闭的httpclient对象
    private static CloseableHttpClient httpClient;

    @Autowired
    BearerToken token;

    public static void openHttpClient() {
        httpClient = HttpClients.createDefault();
    }

    public static void closeHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient = null;
    }
    /**
     * get方法的实现
     * @param url
     * @return
     * @throws IOException
     */
    public JSONObject get(String url) {
        JSONObject jsonObject = null;
        //定义httpget
        HttpGet httpGet = new HttpGet(url);

        //获取token
        String access_token = token.getBearerToken();

        //请求体
        httpGet.setHeader("Authorization", access_token);
//        httpGet.setParams();

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("响应码：" + httpResponse.getStatusLine()+ "响应内容" + httpResponse.getEntity());

                return jsonObject;
            }

            String resultEntity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(resultEntity);

        } catch (IOException e) {
            System.out.println("request url= " + url + ",exception, msg=" + e.getMessage());
            log.error("错误信息：" + e.toString());
        }
        return jsonObject;

    }

    /**
     * get方法的实现
     * 需要带参数
     * @param url
     * @return
     * @throws IOException
     */
    public JSONObject getParams(String url, List<NameValuePair> params) throws URISyntaxException {
        JSONObject jsonObject = null;

        URIBuilder newBuilder = new URIBuilder(url);
        for (NameValuePair p:
             params) {
            newBuilder.setParameters(params);
        }
        //定义httpget
        HttpGet httpGet = new HttpGet(newBuilder.build());

        //获取token
        String access_token = token.getBearerToken();

        //请求体
        httpGet.setHeader("Authorization", access_token);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("响应码：" + httpResponse.getStatusLine()+ "响应内容" + httpResponse.getEntity());

                return jsonObject;
            }

            String resultEntity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(resultEntity);

        } catch (IOException e) {
            System.out.println("request url= " + url + ",exception, msg=" + e.getMessage());
            log.error("错误信息：" + e.toString());
        }
        return jsonObject;

    }

    /**
     * 1.post方法实现
     * 2.传参格式为json
     * @param url
     * @param body
     * @return
     */
    public JSONObject postParamsByJson(String url, String body) {
        String access_token = token.getAccessToken();
        JSONObject jsonObject = null;

        //定义post方法
        HttpPost httpPost = new HttpPost(url);

        //设置请求头
        httpPost.setHeader("content-type", "application/json");
        httpPost.setHeader("Authorization", access_token);

        //请求体
        StringEntity entity = new StringEntity(body, "utf-8");
        httpPost.setEntity(entity);
        //执行请求
        CloseableHttpResponse httpResponse = null;
        try {
             httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("响应码：" + httpResponse.getStatusLine()+ "响应内容" + httpResponse.getEntity());

                return jsonObject;
            }

            //获取entity
            String resultEntity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(resultEntity);
            return jsonObject;

        } catch (IOException e) {
            System.out.println("request url= " + url + ",exception, msg=" + e.getMessage());
            log.error("错误信息：" + e.toString());
        }finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("错误信息" + e.toString());
                }
            }
        }
        return jsonObject;
    }

    /**
     * 1.post方法实现
     * 2.传参格式为form
     * @param url
     * @param body
     * @return
     */
    public JSONObject postParamsByForm(String url, String body) {
        String access_token = token.getAccessToken();
        JSONObject jsonObject = null;

        //定义post方法
        HttpPost httpPost = new HttpPost(url);

        //设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Authorization", access_token);

        //请求体
        StringEntity entity = new StringEntity(body, "utf-8");
        httpPost.setEntity(entity);
        //执行请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                log.error("响应码：" + httpResponse.getStatusLine()+ "响应内容" + httpResponse.getEntity());

                return jsonObject;
            }

            //获取entity
            String resultEntity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(resultEntity);
            return jsonObject;

        } catch (IOException e) {
            System.out.println("request url= " + url + ",exception, msg=" + e.getMessage());
            log.error("错误信息：" + e.toString());
        }finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("错误信息" + e.toString());
                }
            }
        }
        return jsonObject;
    }
}
