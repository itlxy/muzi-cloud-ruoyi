package com.muzi.gateway;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 网关启动程序
 * 
 * @author muzi
 */
@RefreshScope
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MuziGatewayApplication
{
    public static void main(String[] args)
    {
        MuziApplication.run("网关模块",MuziGatewayApplication.class,args);
    }

}
