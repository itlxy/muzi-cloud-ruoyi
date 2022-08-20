package com.muzi.file;

import com.muzi.common.core.app.MuziApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 * 
 * @author muzi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MuziFileApplication
{
    public static void main(String[] args)
    {
        MuziApplication.run("文件上传模块",MuziFileApplication.class,args);
    }
}
