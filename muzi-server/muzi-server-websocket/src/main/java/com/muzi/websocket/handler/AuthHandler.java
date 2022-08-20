package com.muzi.websocket.handler;

import cn.hutool.core.util.StrUtil;
import com.muzi.common.core.constant.SecurityConstants;
import com.muzi.websocket.util.WsChannelGroup;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AuthHandler
 * @Description: https://www.ngui.cc/article/show-351255.html
 * @Author: 11298
 * @DateTime: 2022/8/6 23:21
 * @Version: 1.0
 */
@Component
@ChannelHandler.Sharable//保证处理器，在整个生命周期中就是以单例的形式存在，方便统计客户端的在线数量
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            //根据请求头的 auth-token 进行鉴权操作
            String authToken = request.headers().get(SecurityConstants.AUTHORIZATION_HEADER);
            System.out.println(">>>>>>>>>>>>鉴权操作");
            if (StrUtil.isEmpty(authToken)) {
                refuseChannel(ctx);
                return;
            }
            //查询数据库是否存在该用户,不存在则拒绝连接
            /*TestUser testUser = userMapper.selectById(authToken);
            if (testUser == null) {
                refuseChannel(ctx);
            }*/
            //鉴权成功，添加channel用户组（移到通信处理那去了）
            /*WsChannelGroup.channelUserMap.put();
            WsChannelGroup.userChannelMap.put(, ctx.channel());*/
        }
        ctx.fireChannelRead(msg);
    }

    private void refuseChannel(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED));
        ctx.channel().close();
    }
}
