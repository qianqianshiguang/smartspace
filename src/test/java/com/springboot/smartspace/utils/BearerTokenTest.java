package com.springboot.smartspace.utils;

import com.springboot.smartspace.SmartspaceApplication;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author: gq
 * @createtime: 2021/1/5 20:51
 * @description: 测试bearerToken
 */
@SpringBootTest(classes = {SmartspaceApplication.class})
@Slf4j
public class BearerTokenTest extends AbstractTestNGSpringContextTests {
    @Autowired
    BearerToken bearerToken;

    @Description("Description注解：验证token是否获取成功")
    @Story("工具类测试")
    @Test(description = "测试token是否获取成功")
    public void testGetBearerToken() {

        System.out.println(bearerToken.getAccessToken());
    }
}