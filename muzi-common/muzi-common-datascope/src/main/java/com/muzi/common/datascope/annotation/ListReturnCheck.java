package com.muzi.common.datascope.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果不是期望的返回值 抛出异常
 * 第一个参数是期望的修改条数
 *
 * @author muzi
 * @date 2022/7/29 16:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ListReturnCheck {
    /**
     * 不为期望返回值时返回信息(期望访问值为标注此注解方法列表的第一个参数)
     */
    String info() default "处理失败";
}
