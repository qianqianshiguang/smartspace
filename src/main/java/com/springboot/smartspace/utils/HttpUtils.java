package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONObject;
import com.springboot.smartspace.config.HeaderConstant;
import com.springboot.smartspace.entity.Case;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
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

    private CloseableHttpClient httpClient;

    @Autowired
    BearerToken bearerToken;
    @Autowired
    HttpClientUtils httpClientUtils;
    @Autowired
    HeaderConstant headerConstant;
    @Autowired
    JsonToKeyValueUtils jsonToKeyValueUtils;

    /**
     * get方法的实现
     *
     * @param url
     * @return
     * @throws IOException
     */
    public JSONObject get(String url) {
        httpClient = httpClientUtils.getHttpClient();
        JSONObject jsonObject = null;
        //定义httpget
        HttpGet httpGet = new HttpGet(url);

        //获取token
        String access_token = bearerToken.getBearerToken();

        //请求体
        httpGet.setHeader("Authorization", access_token);
//        httpGet.setParams();

        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpGet);
            jsonObject = getJsonObject(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    /**
     * get方法的实现
     * 需要带参数
     *
     * @param testcase
     * @param body
     * @return
     */
    public JSONObject get(Case testcase, String body) {
        httpClient = httpClientUtils.getHttpClient();
        String url = testcase.getUrl();
        JSONObject jsonObject = null;
        CloseableHttpResponse httpResponse = null;
        //获取token
        String access_token = bearerToken.getBearerToken();
        //json格式转换为list格式
        List<NameValuePair> params = jsonToKeyValueUtils.jsonToList(body);
        try {
            URIBuilder newBuilder = new URIBuilder(url);
            for (NameValuePair p :
                    params) {
                newBuilder.setParameters(p);
            }
            //定义httpget
            HttpGet httpGet = new HttpGet(newBuilder.build());

            //请求体
            httpGet.setHeader("Authorization", access_token);

            httpResponse = httpClient.execute(httpGet);

            jsonObject = getJsonObject(httpResponse);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }


    /**
     * 1.post方法实现
     * 2.判断传参格式form，json
     *
     * @param testcase
     * @param body
     * @return
     */

    public JSONObject post(Case testcase, String body) {

        //定义httpclient
        httpClient = httpClientUtils.getHttpClient();
        String url = testcase.getUrl();

        //获取token
        String access_token = bearerToken.getAccessToken();

        //定义jsonobject保存返回结果
        JSONObject jsonObject = null;

        //执行请求
        CloseableHttpResponse httpResponse = null;
        StringEntity entity;
        try {
            //定义post方法
            HttpPost httpPost = new HttpPost(url);

            //判断传参格式为json或form
            if (testcase.getType().equals(headerConstant.getREQ_HEADER_TYPE_VALUE_FORM())) {
                //设置请求头
                httpPost.setHeader(headerConstant.getREQ_HEADER_TYPE_NAME(), headerConstant.getREQ_HEADER_TYPE_VALUE_FORM());
                httpPost.setHeader(headerConstant.getREQ_HEADER_AUTHORIZATION_NAME(), access_token);

                //将json格式的请求体转换为list格式
                List<NameValuePair> list = jsonToKeyValueUtils.jsonToList(body);
                entity = new UrlEncodedFormEntity(list, "utf-8");

            } else {
                //设置请求头
                httpPost.setHeader(headerConstant.getREQ_HEADER_TYPE_NAME(), headerConstant.getREQ_HEADER_TYPE_VALUE_JSON());
                httpPost.setHeader(headerConstant.getREQ_HEADER_AUTHORIZATION_NAME(), access_token);

                //请求体
                entity = new StringEntity(body, "utf-8");
            }

            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            jsonObject = getJsonObject(httpResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 1.post方法实现
     * 2.传参格式为form
     *
     * @param url
     * @param params
     * @return
     */

    public JSONObject postNotAuthorization(String url, List<NameValuePair> params) {
        httpClient = httpClientUtils.getHttpClient();
        JSONObject jsonObject = null;
        //执行请求
        CloseableHttpResponse httpResponse = null;

//        try {
        //定义post方法
        HttpPost httpPost = new HttpPost(url);

        //设置请求头
        httpPost.setHeader(headerConstant.getREQ_HEADER_TYPE_NAME(), headerConstant.getREQ_HEADER_TYPE_VALUE_FORM());
        httpPost.setHeader(headerConstant.getREQ_HEADER_AUTHORIZATION_NAME(), headerConstant.getREQ_HEADER_AUTHORIZATION_TOKEN_VALUE());

        try {
            //请求体
            StringEntity entity = null;

            entity = new UrlEncodedFormEntity(params, "utf-8");

            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            jsonObject = getJsonObject(httpResponse);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getJsonObject(CloseableHttpResponse httpResponse) {
        JSONObject jsonObject = null;
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String resultEntity = null;
            try {
                resultEntity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                jsonObject = JSONObject.parseObject(resultEntity);

            } catch (IOException e) {
                log.error("错误信息" + e.toString());
            } finally {
                if (httpResponse != null) {
                    try {
                        httpResponse.close();
                    } catch (IOException e) {
                        log.error("错误信息" + e.toString());
                    }
                }
            }

        } else {
            log.error("响应码：" + statusCode + "响应内容" + httpResponse.getEntity());
        }
        return jsonObject;
    }
}
