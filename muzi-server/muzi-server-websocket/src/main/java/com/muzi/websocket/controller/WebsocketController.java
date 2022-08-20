package com.muzi.websocket.controller;

import cn.craccd.mongoHelper.utils.MongoHelper;
import com.muzi.common.core.utils.bean.ValidateUtil;
import com.muzi.common.core.web.domain.AjaxResult;
import com.muzi.common.core.web.page.TableDataInfo;
import com.muzi.common.mongodb.util.MongoUtils;
import com.muzi.websocket.dto.NoticeMessage;
import com.muzi.websocket.dto.ServerMessage;
import com.muzi.websocket.entity.MsgNoticeInfo;
import com.muzi.websocket.enums.MsgType;
import com.muzi.websocket.handler.WebSocketHandler;
import com.muzi.websocket.query.MsgNoticeQuery;
import com.muzi.websocket.service.MsgService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: WebsocketController
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 11:17
 * @Version: 1.0
 */
@RequestMapping("websocket")
@RestController
public class WebsocketController {

    @Resource
    private MsgService msgService;

    @RequestMapping("testWeb")
    public String testWeb(){
        return "可访问页面";
    }

    /**
     * 消息查询列表
     *
     * @Author 1129
     * @Date 11:44 2022/8/7
     * @return java.lang.String
     * @Version 1.0
     **/
    @RequestMapping("msgList")
    public TableDataInfo msgList(MsgNoticeQuery msgNoticeQuery){
        return MongoUtils.toTableDataInfo(msgService.pagelist(msgNoticeQuery));
    }

    /**
     * 通知有未读消息
     *
     * @param noticeMessage 消息通知
     */
    @RequestMapping("sendClient")
    public AjaxResult sendClient(@RequestBody @Valid NoticeMessage noticeMessage, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return AjaxResult.error(ValidateUtil.getValidationMsg(bindingResult));
        }
        //群发用户
        List<String> userNameList= noticeMessage.getUserNameList();
        for(int i=0;i<userNameList.size();i++){
            String userName= userNameList.get(i);
            MsgNoticeInfo msgNoticeInfo=new MsgNoticeInfo();
            msgNoticeInfo.setUserName(userName);
            msgNoticeInfo.setMsgType(noticeMessage.getMsgType());
            msgNoticeInfo.setMsgContent(noticeMessage.getMsgContent());
            msgNoticeInfo.setMsgTitle(noticeMessage.getMsgTitle());
            //消息保存到数据库
            msgService.saveMsg(msgNoticeInfo);
            //保存完成后，统计属于用户的信息数量，返回给在线用户的客户端
            ServerMessage<Integer> serverMessage=new ServerMessage<>();
            serverMessage.setMessage("有新的未读消息😊");
            serverMessage.setMsgType(MsgType.MSG_COUNT_CHANGE.getCode());
            serverMessage.setData(1);
            msgService.sendMsg(userName,serverMessage);
        }

        return AjaxResult.success("发送成功");
    }



}

