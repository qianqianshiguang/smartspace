package com.springboot.smartspace.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/6 09:16
 * @description: 获取token需要用到的参数
 */
@Data
@Component
@ConfigurationProperties("token")
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "utf-8")

public class Token {

    private String url;

    private String username;

    private String password;

    private String scope;

    private String grant_type;

    private String content_type;

    private String authorization;

}
