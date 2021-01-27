package com.springboot.smartspace.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/15 09:28
 * @description: 定义header请求头的常量
 */
@Data
@Component
@ConfigurationProperties("testcase.header")
//@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "utf-8")
public class HeaderConstant {
    //type类型名
    private String REQ_HEADER_TYPE_NAME;
    //json格式值
    private String REQ_HEADER_TYPE_VALUE_JSON;
    //form格式值
    private String REQ_HEADER_TYPE_VALUE_FORM;
    //Authorization名
    private String REQ_HEADER_AUTHORIZATION_NAME;

    //Authorization值
    private String REQ_HEADER_AUTHORIZATION_TOKEN_VALUE;


}
