package com.muzi.websocket.task;

import com.muzi.websocket.dto.ServerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @ClassName: WebsocketRunnable
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 11:12
 * @Version: 1.0
 */
@Slf4j
public class WebsocketRunnable implements Runnable {

    private Channel channel;

    private ServerMessage messageRequest;

    public WebsocketRunnable(Channel channel, ServerMessage messageRequest) {
        this.channel = channel;
        this.messageRequest = messageRequest;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"--"+ LocalDateTime.now().toString());
            channel.writeAndFlush(new TextWebSocketFrame(LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("websocket服务器推送消息发生错误：",e);
        }
    }
}
