package com.springboot.smartspace.utils;

import com.springboot.smartspace.entity.Case;
import com.springboot.smartspace.config.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/6 09:23
 * @description: 从yml中获取数据
 */
@Component
public class ReadYmlUtils {

    @Autowired
    TestData testData;

    public Case readYml(String name) {
        Case testcase = testData.getList().stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .get();
//        System.out.println(testcase);
        return testcase;
    }


}
