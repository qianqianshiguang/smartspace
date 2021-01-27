package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author: gq
 * @createtime: 2021/1/14 15:45
 * @description: 1.json格式参数类型转换成key=value的格式，便于form方式提交带参数的请求地址中进行转换
 *
 */
@Component
public class JsonToKeyValueUtils {
    public String jsonToKeyValue(String jsonParams) {
        //将json转成map
        Map<String, String> map = JSONObject.parseObject(jsonParams, HashMap.class);

        //键保存到Set集合中
        Set<String> keySet = map.keySet();

        //定义一个参数结果
        String params = null;

        for (String key :
                keySet) {
            String value = map.get(key);
            //最后的请求参数
            params += key + "=" + value + "&";
        }
        //最后的请求参数，将最后的&去掉
        params = params.substring(0, params.length() - 1);
        return params;
    }


    /**
     * 将json格式转换成list格式
     * @param jsonParams
     * @return
     */
    public List<NameValuePair> jsonToList(String jsonParams) {
        //将json转成list
        Map<String, String> map = JSONObject.parseObject(jsonParams, HashMap.class);
        //键保存到Set集合中
        Set<String> keySet = map.keySet();

        List<NameValuePair> list = new ArrayList<>();
        for (String key:
            keySet ) {
            list.add(new BasicNameValuePair(key, map.get(key)));

        }
        return list;
    }
}
