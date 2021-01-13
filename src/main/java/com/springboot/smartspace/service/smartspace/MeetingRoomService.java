package com.springboot.smartspace.service.smartspace;

import com.alibaba.fastjson.JSONObject;
import com.springboot.smartspace.entity.Case;
import com.springboot.smartspace.utils.HttpUtils;
import com.springboot.smartspace.utils.ReadYmlUtils;
import com.springboot.smartspace.utils.ReplaceUtils;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author: gq
 * @createtime: 2021/1/6 17:33
 * @description: 会议室等删改查
 */
@Service
public class MeetingRoomService {
    @Autowired
    ReadYmlUtils readYmlUtils;
    @Autowired
    HttpUtils httpUtils;
    @Autowired
    ReplaceUtils replaceUtils;

    /**
     * 添加建筑
     * 
     * @return
     * @throws IOException
     */
    public JSONObject addBuild(Case testcase) throws IOException {
        JSONObject jsonObject =httpUtils.postParamsByJson(testcase.getUrl(), testcase.getBody());
//        System.out.println(jsonObject);
        return jsonObject;

    }

    /**
     * 添加楼层
     *
     * @return
     * @throws IOException
     */
    public JSONObject addFloor(Case testcase, String newBody) throws IOException {

        JSONObject jsonObject = httpUtils.postParamsByJson(testcase.getUrl(), newBody);
        return jsonObject;
    }

    /**
     * 添加会议室
     *
     * @return
     * @throws IOException
     */
    public JSONObject addMeetingRoom(Case testcase, String newBody) throws IOException {

        JSONObject jsonObject = httpUtils.postParamsByJson(testcase.getUrl(), newBody);
        return jsonObject;
    }

    /**
     * 修改会议室（X）
     *
     * @return
     * @throws IOException
     */
    public JSONObject updateMeetingRoom(Case testcase, String newBody) throws IOException {

        JSONObject jsonObject = httpUtils.postParamsByJson(testcase.getUrl(), newBody);
        System.out.println(jsonObject);
        return jsonObject;
    }

    /**
     * 获取会议室列表（X）
     * @param testcase
     * @return
     */
    public JSONObject getMeetingRoomList(Case testcase) {
        JSONObject jsonObject = httpUtils.postParamsByJson(testcase.getUrl(), testcase.getBody());
        System.out.println(jsonObject);
        return jsonObject;
    }

    /**
     * 删除会议室（X）
     * @param url
     * @return
     */
    public JSONObject deleteMeetingRoom(String url) {
        JSONObject jsonObject = httpUtils.get(url);
        return jsonObject;
    }

    /**
     * 获取城市地理标签
     * @param testcase
     * @return
     */
    public JSONObject getMeetingRoomLocation(Case testcase) {

        JSONObject jsonObject = httpUtils.get(testcase.getUrl());
        return jsonObject;
    }

    /**
     * 删除城市
     */
    public JSONObject deleteCity(Case testcase, List<NameValuePair> params) throws URISyntaxException {
        JSONObject jsonObject = httpUtils.getParams(testcase.getUrl(),params);
        return jsonObject;
    }
}
