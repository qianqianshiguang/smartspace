package com.springboot.smartspace.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/7 12:20
 * @description: json解析工具类，断言json内容
 */
@Component
public class JsonpathUtils {
    /**
     * 1.获取json格式数据非第一层的数据
     * 2.此数据非数组
     * @param responseEntity 响应结果
     * @param jsonpath 用户想要查询json对象的值的路径写法
     * jsonpath写法举例 "data/name":获取data下的name
     * @return 返回name的值
     */
    public String getValueByJsonPath(JSONObject responseEntity, String jsonpath) {
        Object obj = responseEntity;
        for (String s:jsonpath.split("/")
             ) {
            if (!s.isEmpty()) {
                if (!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else {
                    obj = ((JSONObject)obj).get(s.split("\\[")[0]);
                }
            }
        }
        return obj.toString();
    }
    /**
     * 1.获取json格式数据非第一层的数据
     * 2.此数据为数组
     * @param responseEntity 响应结果
     * @param jsonpath 用户想要查询json对象的值的路径写法
     * jsonpath写法举例 "data[0]/name":获取data下第一个元素的name
     * @return 返回name的值
     */
    public String getValueByJsonPathArray(JSONObject responseEntity, String jsonpath) {
        Object obj = responseEntity;
        for (String s:jsonpath.split("/")
        ) {
            if (!s.isEmpty()) {
                if (!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else {
                    obj =((JSONArray)((JSONObject)obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));

                }
            }
        }
        return obj.toString();
    }
}
