package com.springboot.smartspace.config;

import com.springboot.smartspace.entity.Case;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/6 16:30
 * @description: 从yml中读取测试数据
 */
@Data
@Component
@ConfigurationProperties("testcase")
//@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "utf-8")
public class TestData {

    //获取到list格式到测试数据
    List<Case> list = new ArrayList<>();


}
