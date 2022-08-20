package com.muzi.websocket;

import com.muzi.common.core.app.MuziApplication;
import com.muzi.websocket.config.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName: WebsocketApplication
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 11:15
 * @Version: 1.0
 */
@Slf4j
@SpringBootApplication
@EnableAsync
public class MuziWebsocketApplication {

    @Resource
    private WebsocketServer websocketServer;

    @PostConstruct
    public void start() {
        try {
            log.info(Thread.currentThread().getName() + ":websocket启动中......");
            //netty启动方法要新开线程执行，否则socket在等待接收消息时，会阻塞当前线程
            websocketServer.init();
            log.info(Thread.currentThread().getName() + ":websocket启动成功！！！");
        } catch (Exception e) {
            log.error("websocket发生错误：",e);
        }
    }

    public static void main(String[] args) {
        MuziApplication.run("通信模块", MuziWebsocketApplication.class,args);
    }
}

