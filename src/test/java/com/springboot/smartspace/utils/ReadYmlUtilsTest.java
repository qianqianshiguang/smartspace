package com.springboot.smartspace.utils;

import com.springboot.smartspace.SmartspaceApplication;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author: gq
 * @createtime: 2021/1/5 20:51
 * @description: 测试bearerToken
 */
@SpringBootTest(classes = {SmartspaceApplication.class})

public class ReadYmlUtilsTest extends AbstractTestNGSpringContextTests {
    @Autowired
    ReadYmlUtils readYmlUtils;

    @Description("Description注解：测试是否能从yml中读取数据")
    @Story("工具类测试")
    @Test(description = "测试是否能从yml中读取数据")
    public void testReadYmlUtils() throws IOException {
        readYmlUtils.readYml("addSchedule");
    }
}