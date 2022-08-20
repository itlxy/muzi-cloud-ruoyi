package com.muzi.websocket.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ServerMessage
 * @Description: 群发消息封装对象
 * @Author: 11298
 * @DateTime: 2022/8/7 11:06
 * @Version: 1.0
 */
@Data
public class NoticeMessage implements Serializable {

    /**
     *
     * 群发用户
     */
    private List<String> userNameList;

    /**
     *
     * 消息类型
     */
    private String msgType;

    /** 消息标题 */
    private String msgTitle;

    /** 消息内容 */
    private String msgContent;
}
