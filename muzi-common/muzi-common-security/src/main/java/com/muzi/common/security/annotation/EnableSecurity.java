package com.muzi.common.security.annotation;


import com.muzi.common.security.advice.DecryptRequestBodyAdvice;
import com.muzi.common.security.advice.EncryptResponseBodyAdvice;
import com.muzi.common.security.config.SecretKeyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @Author: muzi
 * @DateTime: 2022/8/7 22:40
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({SecretKeyConfig.class,
        EncryptResponseBodyAdvice.class,
        DecryptRequestBodyAdvice.class})
public @interface EnableSecurity{

}
