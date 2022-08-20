package com.muzi.common.core.annotation;


import com.muzi.common.core.enums.MatchType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface QueryCondition {

    // 数据库中字段名,默认为空字符串,则Query类中的字段要与数据库中字段一致
    String column() default "";

    // equal, like, gt, lt...
    MatchType func() default MatchType.EQ;

    // object是否可以为null
    boolean nullable() default false;

    // 是否忽略该字段
    //boolean ignore() default false;

    // 字符串是否可为空
    boolean emptyable() default false;

    //是否转驼峰
    boolean camelCase() default false;

}
