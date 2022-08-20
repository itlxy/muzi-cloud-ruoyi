package com.muzi.common.core.annotation;

import java.lang.annotation.*;

/**
 * AutoFill 用于取属性名。 为空时仅对类型为Date/TimeStrap/LocalDateTime等属性有效。
 * 必须和 @TableField 且 fill值为 FieldFill.INSERT、FieldFill.UPDATE、FieldFill.INSERT_UPDATE 配合使用。
 * 例如：
 * 配合 @TableField(fill = FieldFill.INSERT)  在插入时填充该字段的值。
 * 配合 @TableField(fill = FieldFill.UPDATE)  在更新时填充该字段的值。
 * 配合 @TableField(fill = FieldFill.INSERT_UPDATE) 在插入和更新时填充该字段。
 * <p>
 * klass 为  要调用目标类
 * method 为  klass类中要调用的目标方法。
 *
 * @author muzi
 * @date 2022/7/29 16:52
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AutoFill {
    /**
     * 要调用目标类
     *
     * @return 类 class
     */
    Class<?> klass() default Object.class;

    /**
     * 调用的目标方法
     *
     * @return 方法名
     */
    String method() default "";

}
