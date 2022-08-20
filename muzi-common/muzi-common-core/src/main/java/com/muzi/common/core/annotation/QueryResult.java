package com.muzi.common.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface QueryResult {
    // 数据库中字段名,默认为空字符串,则Query类中的字段要与数据库中字段一致
    String column() default "";
    // 是否忽略该字段
    //boolean ignore() default false;
    //是否使用下划线形式
    boolean underScoreCase() default true;
}
