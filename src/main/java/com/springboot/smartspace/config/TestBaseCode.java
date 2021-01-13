package com.springboot.smartspace.config;

import lombok.Data;

import java.util.Properties;

/**
 * @author: gq
 * @createtime: 2021/1/7 12:00
 * @description: 定义常用的状态码
 */
@Data
public class TestBaseCode {
    public Properties properties;
    public int RESPONSE_STATUS_CODE_200 = 200;
    public int RESPONSE_STATUS_CODE_201 = 201;
    public int RESPONSE_STATUS_CODE_404 = 404;
    public int RESPONSE_STATUS_CODE_500 = 500;

//    //写一个构造函数
//    public TestBaseCode() {
//        try{
//            prop= new Properties();
//            FileInputStreamfis = new FileInputStream(System.getProperty("user.dir")+
//                    "/src/main/java/com/qa/config/config.properties");
//            prop.load(fis);
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
