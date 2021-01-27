package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONObject;
import com.springboot.smartspace.entity.Case;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    HttpUtils httpUtils;
    @Autowired
    ReadYmlUtils readYmlUtils;
    @Autowired
    JsonToKeyValueUtils jsonToKeyValueUtils;

    private String access_token = null;

    public String getAccessToken() {
        //判断access_token是否存在，如果存在，直接返回值
        if (access_token == null) {
            access_token = getBearerToken();
        }
        return access_token;
    }

    public String getBearerToken() {

        //获取数据
        Case testcase = readYmlUtils.readYml("gettoken");

        List<NameValuePair> list = jsonToKeyValueUtils.jsonToList(testcase.getBody());

        //获取响应体JsonObject格式
        JSONObject jsonObject = httpUtils.postNotAuthorization(testcase.getUrl(), list);

        access_token = "Bearer " + jsonObject.getString("access_token");

        return access_token;

    }


}
