package com.muzi.auth;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.muzi.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author muzi
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MuziAuthApplication
{
    public static void main(String[] args)
    {
        MuziApplication.run("认证授权中心",MuziAuthApplication.class,args);
    }
}
