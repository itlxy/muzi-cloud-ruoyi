package com.muzi.websocket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ClientMessage
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/7 11:06
 * @Version: 1.0
 */
@Data
public class ClientMessage implements Serializable {

    /**
     *  消息来源
     */
    private String fromUser;

    /**
     * 消息内容
     */
    private String msgInfo;

    /**
     *  消息发送方 （目前只在单聊中体现，【群聊暂时没有分组处理】）
     */
    private String toUser;
}
