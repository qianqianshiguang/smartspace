package com.springboot.smartspace.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/14 15:26
 * @description: 定义接口的超时时间
 */
@Data
@Component
@ConfigurationProperties("http")
//@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "utf-8")
public class HttpClientConfigData {

    //连接池最大叔
    private int max_total;
    private int validate_after_inactivity;
    //请求超时时间
    private int connection_requests_timeout;
    //连接超时时间
    private int connect_timeout;
    //响应超时时间
    private int socket_timeout;


}
