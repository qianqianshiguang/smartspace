package com.springboot.smartspace.service.smartspace;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.springboot.smartspace.SmartspaceApplication;
import com.springboot.smartspace.entity.Case;
import com.springboot.smartspace.utils.HttpUtils;
import com.springboot.smartspace.utils.JsonpathUtils;
import com.springboot.smartspace.utils.ReadYmlUtils;
import com.springboot.smartspace.utils.ReplaceUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/6 17:41
 * @description: 会议室增删改查测试
 */
@Slf4j
@SpringBootTest(classes = {SmartspaceApplication.class})
public class MeetingRoomServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    MeetingRoomService meetingRoomService;
    @Autowired
    JsonpathUtils jsonpathUtils;
    @Autowired
    ReadYmlUtils readYmlUtils;
    @Autowired
    ReplaceUtils replaceUtils;

    private String createBuildLabelId;
    private String createFloorLabelId;
    private List<String> createMeetingRoomId = new ArrayList<>();
    private List<String> createLocationId = new ArrayList<>();

    @BeforeClass
    public void beforeClass() {
        HttpUtils.openHttpClient();
    }

    @AfterClass
    public void afterClass() {
        HttpUtils.closeHttpClient();
    }

    @Description("Description注解：添加建筑")
    @Story("会议室测试")
    @Test(description = "添加建筑")
    public void testAddBuild() throws IOException {
        //数据准备
        Case testcase = readYmlUtils.readYml("addBuild");

        JSONObject jsonObject = meetingRoomService.addBuild(testcase);
        log.info("结果：" + jsonObject.toString());

        //获取buildLabelId
        createBuildLabelId = JsonPath.read(jsonObject, "$.data.labelId");

        //断言结果
        Assert.assertEquals(jsonObject.get("code"), 0, "code返回值不为0");
        Assert.assertEquals(jsonObject.get("msg"), "success","msg不为success");
    }


    @Test(dependsOnMethods = {"testAddBuild"}, description = "添加楼层")
    public void testAddFloor() throws IOException {
        //数据准备
        Case testcase = readYmlUtils.readYml("addFloor");
        String body = testcase.getBody();
        String newBody = replaceUtils.replaceValue(body, createBuildLabelId);

        JSONObject jsonObject = meetingRoomService.addFloor(testcase, newBody);
        log.info("结果：" + jsonObject.toString());

        createFloorLabelId = JsonPath.read(jsonObject, "$.data.labelId");

        //断言结果
        int code = JsonPath.read(jsonObject, "$.code");
        String msg = JsonPath.read(jsonObject, "$.msg");
        Assert.assertEquals(code, 0, "code返回值不为0");
        Assert.assertEquals(msg, "success", "msg不为success");
    }

    @Test(dependsOnMethods = {"testAddBuild","testAddFloor"}, description = "添加会议室")
    public void testAddMeetingRoom() throws IOException {
        //数据准备
        Case testcase = readYmlUtils.readYml("addMeetingRoom");
        String body = testcase.getBody();
        String newBody = replaceUtils.replaceValue(body, createFloorLabelId);

        JSONObject jsonObject = meetingRoomService.addMeetingRoom(testcase, newBody);
        log.info("结果：" + jsonObject.toString());

        //断言结果
        int code = JsonPath.read(jsonObject, "$.code");
        Boolean data = JsonPath.read(jsonObject, "$.data");
        Assert.assertEquals(code, 0, "code返回值不为0");
        Assert.assertEquals(data, (Boolean) true, "data不为true");
    }

    @Test(dependsOnMethods = {"testAddBuild","testAddFloor","testAddMeetingRoom"},description = "修改会议室")
    public void testUpdateMeetingRoom() throws IOException {
        //数据准备
        Case testcase = readYmlUtils.readYml("updateMeetingRoom");
        String body = testcase.getBody();

        JSONObject jsonObject = JSONObject.parseObject(body);
        log.info("结果：" + jsonObject.toString());

        jsonObject.put("locationId", createFloorLabelId);
        jsonObject.put("meetingRoomId", createMeetingRoomId);

        String newBody = jsonObject.toString();
        System.out.println(newBody);

        meetingRoomService.updateMeetingRoom(testcase, newBody);

        //断言结果
    }

    @Test
    public void testGetMeetingRoomList() {
        //数据准备
        Case testcase = readYmlUtils.readYml("getMeetingRoomList");

        JSONObject jsonObject = meetingRoomService.getMeetingRoomList(testcase);
        log.info("结果：" + jsonObject.toString());

        //获取MeetingRoomId
        createMeetingRoomId = JsonPath.read(jsonObject, "$..meetingRoomId");

        int code = JsonPath.read(jsonObject, "$.code");
        //断言数据
        Assert.assertEquals(code, 0, "code返回值不为0");

    }

    @Test(dependsOnMethods = {"testGetMeetingRoomList"}, description = "删除会议室")
    public void testDeleteMeetingRoom() {
        //数据准备
        Case testcase = readYmlUtils.readYml("deleteMeetingRoom");
        for (String id :
                createMeetingRoomId) {
            String url = "https://gateway-beta.mingwork.com/meet/meeting-room/remove/" + id;
            JSONObject jsonObject = meetingRoomService.deleteMeetingRoom(url);
            Boolean data = JsonPath.read(jsonObject, "$.data");
            log.info("结果：" + jsonObject.toString());
            //断言数据
            Assert.assertEquals(data, (Boolean) true, "data不为true");
        }


    }

    @BeforeMethod
    public void setUp() {

    }

    @AfterMethod
    public void tearDown() {
    }

    @Test(description = "获取地理位置标签")
    public void testGetMeetingRoomLocation() {
        //数据准备
        Case testcase = readYmlUtils.readYml("getMeetingRoomLocation");

        JSONObject jsonObject = meetingRoomService.getMeetingRoomLocation(testcase);
        log.info("结果：" + jsonObject.toString());

        createLocationId = JsonPath.read(jsonObject, "$.data[?(@.id)].id");

        //获取locationId

        //断言结果
        int code = JsonPath.read(jsonObject, "$.code");
        String msg = JsonPath.read(jsonObject, "$.msg");
        Assert.assertEquals(code, 0, "code返回值不为0");
        Assert.assertEquals(msg, "success", "msg不为success");

    }

    @Test(dependsOnMethods = {"testGetMeetingRoomLocation"})
    public void testDeleteCity() throws URISyntaxException {
        //数据准备
        Case testcase = readYmlUtils.readYml("deleteCity");



        for (String id:
            createLocationId ) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("locationId", id));
            JSONObject jsonObject = meetingRoomService.deleteCity(testcase, params);
            log.info("结果：" + jsonObject.toString());

            //断言结果
            int code = JsonPath.read(jsonObject, "$.code");
            String msg = JsonPath.read(jsonObject, "$.msg");
            Assert.assertEquals(code, 0, "code返回值不为0");
            Assert.assertEquals(msg, "success", "msg不为success");

        }

    }
}