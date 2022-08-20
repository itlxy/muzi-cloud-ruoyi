package com.muzi.gen;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.muzi.common.security.annotation.EnableCustomConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 代码生成
 * 
 * @author muzi
 */
@EnableCustomConfig
@EnableFeignClients(basePackages = {"com.muzi"})
@SpringBootApplication
public class MuziGenApplication
{
    public static void main(String[] args) {
        MuziApplication.run("代码生成模块",MuziGenApplication.class,args);
    }
}
