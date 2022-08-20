package com.muzi.common.security.config;

import com.muzi.common.security.interceptor.RepeatSubmitInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.muzi.common.security.interceptor.HeaderInterceptor;

/**
 * 拦截器配置
 *
 * @author muzi
 */
public class WebMvcConfig implements WebMvcConfigurer
{
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = { "/login", "/logout", "/refresh" };

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);

        registry.addInterceptor(getRepeatSubmitInterceptor())
                .addPathPatterns("/**")
                //.excludePathPatterns(excludeUrls)
                .order(-9);
    }

    /**
     * 自定义请求头拦截器
     */
    public HeaderInterceptor getHeaderInterceptor()
    {
        return new HeaderInterceptor();
    }

    /**
     * 自定义请求头拦截器
     */
    public RepeatSubmitInterceptor getRepeatSubmitInterceptor()
    {
        return new RepeatSubmitInterceptor();
    }
}
