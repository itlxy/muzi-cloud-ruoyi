package com.muzi.example.convert.impl;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TypeConversionWorker
 * @Description: 使用：转换方法上添加：@Mapping(source = "delFlag", target = "delFlag",qualifiedByName = "booleanToString")
 * @Author: 11298
 * @DateTime: 2022/7/25 14:27
 * @Version: 1.0
 */
@Component
@Named("TypeConversionWorker")
public class TypeConversionWorker {
    /**
     * @Author 11298
     * @Description //将Boolean转为字符串
     * @param param
     * @return java.lang.String
     * @Version 1.0
     **/
    @Named("booleanToString")
    public String booleanToString(Boolean param) {
        if (null==param) {
            return null;
        }else{
            return param.toString();
        }
    }
}
