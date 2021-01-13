package com.springboot.smartspace.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: gq
 * @createtime: 2021/1/12 09:56
 * @description: 替换yaml文件中的占位符
 */
@Component
public class ReplaceUtils {
    public String replaceValue(String value, String replaceValue) {
        String newValue = null;

        //正则匹配内容
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
//            System.out.println(matcher.group(0));
            newValue = matcher.replaceAll(replaceValue);
        }
        return newValue;
    }
}
