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

    private String name;

    private String url;

    private String method;

    private String body;

    private String description;


}
