package com.muzi.xxljob;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: MuziXxlJobApplication
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/12 15:46
 * @Version: 1.0
 */
@SpringBootApplication
public class MuziXxlJobApplication {
    public static void main(String[] args) {
        MuziApplication.run("xxljob定时任务模块",MuziXxlJobApplication.class,args);
    }
}
