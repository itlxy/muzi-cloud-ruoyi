package com.muzi.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.muzi.websocket.dto.ClientMessage;
import com.muzi.websocket.dto.ServerMessage;
import com.muzi.websocket.enums.MsgType;
import com.muzi.websocket.task.WebsocketRunnable;
import com.muzi.websocket.util.WsChannelGroup;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: WebSocketHandler
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 11:09
 * @Version: 1.0
 */
@Slf4j
@Component
@ChannelHandler.Sharable//保证处理器，在整个生命周期中就是以单例的形式存在，方便统计客户端的在线数量
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {



    /**
     * 客户端发送给服务端的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        try {
            //接受客户端发送的消息
            ClientMessage clientMessage = JSON.parseObject(msg.text(), ClientMessage.class);

            //每个channel都有id，asLongText是全局channel唯一id
            String key = ctx.channel().id().asLongText();
            //存储channel的id和用户的主键
            WsChannelGroup.channelUserMap.put(key, clientMessage.getFromUser());
            Set<Channel> channelSet=WsChannelGroup.userChannelMap.get(clientMessage.getFromUser());
            if(null==channelSet){
                channelSet=new HashSet<>();
                channelSet.add(ctx.channel());
            }
            WsChannelGroup.userChannelMap.put(clientMessage.getFromUser(),channelSet);
            log.info("接受客户端的消息......" + ctx.channel().remoteAddress() + "-参数[" + clientMessage.getFromUser() + "]");

            /*if (!WsChannelGroup.channelMap.containsKey(key)) {
                ServerMessage<String> serverMessage=new ServerMessage<>();
                serverMessage.setMessage("服务端定时任务推送");
                serverMessage.setMsgType(MsgType.MSG_TEXT.getCode());
                //使用channel中的任务队列，做周期循环推送客户端消息，解决问题二和问题五
                Future future = ctx.channel().eventLoop().scheduleAtFixedRate(new WebsocketRunnable(ctx.channel(), serverMessage), 0, 10, TimeUnit.SECONDS);
                //存储客户端和服务的通信的Chanel
                WsChannelGroup.channelMap.put(key, ctx.channel());
                //存储每个channel中的future，保证每个channel中有一个定时任务在执行
                WsChannelGroup.channelFutureMap.put(key, future);
            } else {
                //每次客户端和服务的主动通信，和服务端周期向客户端推送消息互不影响 解决问题一
                ctx.channel().writeAndFlush(new TextWebSocketFrame(Thread.currentThread().getName() + "服务器时间" + LocalDateTime.now() + "wdy"));
            }*/
            ctx.channel().writeAndFlush(new TextWebSocketFrame("websocket连接成功："+Thread.currentThread().getName() + "服务器时间" + LocalDateTime.now()));

        } catch (Exception e) {

            log.error("websocket服务器推送消息发生错误：", e);

        }
    }

    /**
     * 客户端连接时候的操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("一个客户端连接......" + ctx.channel().remoteAddress() + Thread.currentThread().getName());
    }

    /**
     * 客户端掉线时的操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        String key = ctx.channel().id().asLongText();
        //移除通信过的channel
        WsChannelGroup.channelMap.remove(key);
        //移除和用户绑定的channel
        WsChannelGroup.channelUserMap.remove(key);
        //关闭掉线客户端的future
        Optional.ofNullable(WsChannelGroup.channelFutureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            WsChannelGroup.channelFutureMap.remove(key);
        });
        log.info("一个客户端移除......" + ctx.channel().remoteAddress());
        ctx.close(); //关闭连接
    }

    /**
     * 发生异常时执行的操作
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String key = ctx.channel().id().asLongText();
        //移除通信过的channel
        WsChannelGroup.channelMap.remove(key);
        //移除和用户绑定的channel
        WsChannelGroup.channelUserMap.remove(key);
        //移除定时任务
        Optional.ofNullable(WsChannelGroup.channelFutureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            WsChannelGroup.channelFutureMap.remove(key);
        });
        //关闭长连接
        ctx.close();
        log.info("异常发生 " + cause.getMessage());
    }
}

