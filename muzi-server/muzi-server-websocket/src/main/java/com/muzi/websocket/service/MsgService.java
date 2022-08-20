package com.muzi.websocket.service;

import cn.craccd.mongoHelper.bean.Page;
import com.muzi.websocket.dto.ServerMessage;
import com.muzi.websocket.entity.MsgNoticeInfo;
import com.muzi.websocket.query.MsgNoticeQuery;

import java.util.List;

public interface MsgService {
    /**
     * @Author 11298
     * @Description //发送消息
     * @Date 14:35 2022/8/7
     * @param userName
     * @param serverMessage
     * @Version 1.0
     **/
    public void sendMsg(String userName, ServerMessage serverMessage);

    /**
     * 保存消息
     *
     * @Date 19:21 2022/8/7
     * @param msgNoticeInfo
     **/
    void saveMsg(MsgNoticeInfo msgNoticeInfo);

    /**
     * 保存消息列表
     *
     * @Date 19:21 2022/8/7
     * @param msgNoticeList
     **/
    void batchSave(List<MsgNoticeInfo> msgNoticeList);

    /**
     * 分页查询
     *
     * @Date 19:21 2022/8/7
     **/
    Page pagelist(MsgNoticeQuery msgNoticeQuery);


}
