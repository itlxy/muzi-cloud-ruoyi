package com.muzi.common.core.utils.bean;

import cn.hutool.core.collection.CollectionUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

/**
 * validation方法 (没有IOC容器的地方使用本类)
 *
 * @author muzi
 * @DateTime: 2022/7/27 19:42
 */
@SuppressWarnings("unused")
public final class BeanValidateUtil {

    private static final Validator VALIDATOR;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 校验Bean
     *
     * @param object 建议的Bean
     * @param groups 校验分组
     * @param <T>    Bean类型
     * @return 校验结果
     */
    public static <T> String validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(object, groups);
        if (CollectionUtil.isEmpty(validate)) {
            return null;
        }
        return new ArrayList<>(validate).get(0).getMessage();
    }

}
