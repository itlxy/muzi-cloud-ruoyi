package com.muzi.websocket.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @ClassName: WsChannelGroup
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/6 23:27
 * @Version: 1.0
 */
public class WsChannelGroup {
    //通道map，存储channel，用于群发消息，以及统计客户端的在线数量，解决问题问题三，如果是集群环境使用redis的hash数据类型存储即可
    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    //任务map，存储future，用于停止队列任务
    public static Map<String, Future> channelFutureMap = new ConcurrentHashMap<>();
    //存储channel的id和用户主键的映射，客户端保证用户主键传入的是唯一值，解决问题四，如果是集群中需要换成redis的hash数据类型存储即可
    public static Map<String, String> channelUserMap = new ConcurrentHashMap<>();
    //存储channel的id和用户主键的映射，客户端保证用户主键传入的是唯一值，解决问题四，如果是集群中需要换成redis的hash数据类型存储即可
    public static Map<String, Set<Channel>> userChannelMap = new ConcurrentHashMap<>();
}
