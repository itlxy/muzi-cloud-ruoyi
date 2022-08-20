package com.muzi.common.core.annotation;

import java.lang.annotation.*;

/**
 * JavaBean 转 Map 字段别名
 * Map 转 JavaBean字段别名
 *
 * @author zhangqi
 * @date 2022/06/27 13:57
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanToMapAlias {
    /**
     * 别名
     *
     * @return 别名
     */
    String value() default "";

    /**
     * 默认值
     *
     * @return 默认值
     */
    String def() default "";
}
