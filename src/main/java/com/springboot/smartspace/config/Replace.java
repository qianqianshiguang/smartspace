package com.springboot.smartspace.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/12 15:22
 * @description: 替换的值
 */
@Component
@Data
public class Replace {
    List<String> list = new ArrayList<>();
}
