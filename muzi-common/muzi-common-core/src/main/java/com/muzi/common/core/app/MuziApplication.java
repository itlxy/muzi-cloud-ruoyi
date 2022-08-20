package com.muzi.common.core.app;

import cn.hutool.core.net.NetUtil;
import com.muzi.common.core.utils.ip.IpUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: MuziApplication
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/29 15:03
 * @Version: 1.0
 */
@Log4j2
public class MuziApplication {
    public static void run(String appName,Class appClass,String[] args){
        ConfigurableApplicationContext application= SpringApplication.run(appClass, args);
        Environment env = application.getEnvironment();
        //获取本地ip
        String host= IpUtils.getHostIp();
        //获取本机外网地址
        //String netIp=IpUtils.getOutIPV4();
        //获取本地mac地址
        //String macAddr= NetUtil.getLocalMacAddress();
        String springApplicationName=env.getProperty("spring.application.name");
        String port=env.getProperty("server.port");
        String contextPath=env.getProperty("server.servlet.context-path");
        String portAndContextPath = null;
        String applicationName = "未获取服务名称";
        if (null != applicationName){
            applicationName = springApplicationName;
        }

        if (null == contextPath){
            portAndContextPath = port;
        }else {
            portAndContextPath = port+contextPath;
        }
        log.info("\n\t----------------------------------------------------------\n\t" +
                        "Application '{}' is Running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t"+
                        //"Public: \thttp://{}:{}\n\t"+
                        //"MacAddr: \t{}\n\t"+
                        "Doc: \thttp://{}:{}/doc.html\n\t"+
                        "----------------------------------------------------------\n\t"+
                        "(♥◠‿◠)ﾉﾞ  {}启动成功   ლ(´ڡ`ლ)ﾞ！\n\t"+
                        "温馨提示：代码千万行，注释第一行，命名不规范，同事泪两行\n\t"+
                        "----------------------------------------------------------",
                applicationName,
                portAndContextPath,
                host,portAndContextPath,
                //netIp,portAndContextPath,
                //macAddr,
                host,portAndContextPath,
                appName);
    }
}
