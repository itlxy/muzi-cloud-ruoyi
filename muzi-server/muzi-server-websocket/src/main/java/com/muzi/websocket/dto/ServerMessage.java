package com.muzi.websocket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: MessageRequest
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 10:13
 * @Version: 1.0
 */
@Data
public class ServerMessage<T> implements Serializable {
    /**
     *
     * 消息类型
     */
    private String msgType;

    /**
     *
     * 消息内容
     */
    private String message;

    /**
     *
     * 消息数据
     */
    private T data;
}
