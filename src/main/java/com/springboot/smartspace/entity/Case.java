package com.springboot.smartspace.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/6 16:27
 * @description: 定义用例需要的元素
 */
@Data
@Component
public class Case {

    //自定义的接口名称，通过此名称获取测试数据
    private String name;

    //接口url
    private String url;

    //接口方法
    private String method;

    //传参格式
    private String type;

    //请求体
    private String body;

    //接口描述
    private String description;


}
