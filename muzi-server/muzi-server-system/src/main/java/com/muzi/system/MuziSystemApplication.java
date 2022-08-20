package com.muzi.system;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.muzi.common.security.annotation.EnableCustomConfig;
import com.muzi.common.security.annotation.EnableRyFeignClients;

/**
 * 系统模块
 * 
 * @author muzi
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class MuziSystemApplication
{
    public static void main(String[] args)
    {
        MuziApplication.run("系统模块",MuziSystemApplication.class,args);
    }
}
