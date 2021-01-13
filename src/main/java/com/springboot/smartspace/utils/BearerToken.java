package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONObject;
import com.springboot.smartspace.config.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/5 20:39
 * @description: 获取token
 */
@Component
@Slf4j
public class BearerToken {
    @Autowired
    Token token;

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String access_token = null;

    public String getAccessToken() {
        //判断access_token是否存在，如果存在，直接返回值
        if (access_token == null) {
            access_token = getBearerToken();
        }
        return access_token;
    }
    public String getBearerToken() {

        HttpPost httpPost = new HttpPost(token.getUrl());

        //添加请求头
        httpPost.setHeader("Content-Type", token.getContent_type());
        httpPost.setHeader("Authorization", token.getAuthorization());

        //请求参数
        List<NameValuePair> params = new ArrayList();

        //执行httppost请求
        CloseableHttpResponse response = null;

        try {

            params.add(new BasicNameValuePair("username", token.getUsername()));
            params.add(new BasicNameValuePair("password", token.getPassword()));
            params.add(new BasicNameValuePair("scope", token.getScope()));
            params.add(new BasicNameValuePair("grant_type", token.getGrant_type()));

            StringEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("access_token获取失败，响应码：" + response.getStatusLine().getStatusCode() + " 响应内容" + response.getEntity());
                return "access_token获取失败，失败信息" + response.getStatusLine() + response.getEntity();
            }
            String result = EntityUtils.toString(response.getEntity());
            //获取access_token
            JSONObject jsonObject = JSONObject.parseObject(result);
            access_token = "Bearer " + jsonObject.getString("access_token");
//            System.out.println(access_token);
            return access_token;
        } catch (IOException e) {
            System.out.println("request url= " + token.getUrl() + ",exception, msg=" + e.getMessage());
            log.error("错误信息：" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("错误信息" + e.toString());
                }
            }
        }

        return access_token;

    }

}
