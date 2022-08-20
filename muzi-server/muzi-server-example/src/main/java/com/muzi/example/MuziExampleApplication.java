package com.muzi.example;

import com.muzi.common.core.app.MuziApplication;
import com.muzi.common.security.annotation.EnableCustomConfig;
import com.muzi.common.security.annotation.EnableRyFeignClients;
import com.muzi.common.security.annotation.EnableSecurity;
import com.muzi.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: MuziExampleApplication
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/13 10:39
 * @Version: 1.0
 */
@EnableCustomConfig
@EnableSecurity
@EnableRyFeignClients
@SpringBootApplication
public class MuziExampleApplication {
    public static void main(String[] args) {
        MuziApplication.run("案例参考模块",MuziExampleApplication.class,args);
    }
}
