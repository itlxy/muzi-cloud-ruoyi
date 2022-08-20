package com.muzi.common.core.utils.http;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.muzi.common.core.utils.bean.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName: HuHttpUtils
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 17:08
 * @Version: 1.0
 */
public class HuHttpUtils {
    private static final int MAX_TIME_OUT=30000;
    /**
     * http get 请求
     *
     * @param url 请求地址
     * @return http响应
     */
    public static HttpResponse get(String url) {
        return get(url, null);
    }

    /**
     * http get 请求
     *
     * @param url   请求地址
     * @param param 请求参数 1. JSONObject 2. 实体
     * @return http响应
     */
    public static HttpResponse get(String url, Object param) {
        return get(url, param, null);
    }

    /**
     * http get 请求
     *
     * @param url    请求地址
     * @param param  请求参数 1. JSONObject 2. 实体
     * @param header 请求头
     * @return http响应
     */
    public static HttpResponse get(String url, Object param, JSONObject header) {
        return get(url, param, header, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * http get 请求
     *
     * @param url     请求地址
     * @param param   请求参数 1. JSONObject 2. 实体
     * @param header  请求头
     * @param charset 字符集
     * @return http响应
     */
    public static HttpResponse get(String url, Object param, JSONObject header, Charset charset) {
        return get(url, param, header, charset, MAX_TIME_OUT, false);
    }

    /**
     * http get 请求
     *
     * @param url       请求地址
     * @param param     请求参数 1. JSONObject 2. 实体
     * @param header    请求头
     * @param charset   字符集 UTF-8
     * @param timeout   设置超时，单位：毫秒<br> 默认 3秒
     * @param dynamicIp 是否动态Ip  true: 动态
     * @return http响应
     */
    public static HttpResponse get(String url, Object param, JSONObject header, Charset charset, int timeout, boolean dynamicIp) {
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(url), "GET请求url不能为空");
        HttpRequest request = HttpRequest.get(url).charset(Optional.ofNullable(charset).orElse(CharsetUtil.CHARSET_UTF_8)).timeout(timeout >= MAX_TIME_OUT ? MAX_TIME_OUT : timeout);
        setParam(param, header, dynamicIp, request);
        setRequestAndHeader(header, dynamicIp, request);
        return request.execute();
    }

    /**
     * http post 表单请求
     *
     * @param url 请求地址
     * @return http响应
     */
    public static HttpResponse post(String url) {
        return post(url, null);
    }

    /**
     * http post 表单请求
     *
     * @param url   请求地址
     * @param param 请求参数 1. JSONObject 2. 实体
     * @return http响应
     */
    public static HttpResponse post(String url, Object param) {
        return post(url, param, null);
    }

    /**
     * http post 表单请求
     *
     * @param url    请求地址
     * @param param  请求参数 1. JSONObject 2. 实体
     * @param header 请求头
     * @return http响应
     */
    public static HttpResponse post(String url, Object param, JSONObject header) {
        return post(url, param, header, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * http post 表单请求
     *
     * @param url     请求地址
     * @param param   请求参数 1. JSONObject 2. 实体
     * @param header  请求头
     * @param charset 字符集
     * @return http响应
     */
    public static HttpResponse post(String url, Object param, JSONObject header, Charset charset) {
        return post(url, param, header, charset, MAX_TIME_OUT, false);
    }

    /**
     * http post 表单请求
     *
     * @param url       请求地址
     * @param param     参数
     * @param header    请求头
     * @param charset   字符集 默认 UTF-8
     * @param timeout   超时时间 单位：毫秒<br> 默认 3秒
     * @param dynamicIp 是否动态Ip  true: 动态
     * @return http响应
     */
    public static HttpResponse post(String url, Object param, JSONObject header, Charset charset, int timeout, boolean dynamicIp) {
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(url), "POST请求url不能为空");
        // 设置默认字符集
        charset = Optional.ofNullable(charset).orElse(CharsetUtil.CHARSET_UTF_8);
        // 设置超时时间
        timeout = timeout >= MAX_TIME_OUT ? MAX_TIME_OUT : timeout;
        HttpRequest request = HttpRequest.post(url).charset(charset).timeout(timeout);
        setParam(param, header, dynamicIp, request);
        setRequestAndHeader(header, dynamicIp, request);
        return request.execute();
    }

    /**
     * http post Json流请求
     *
     * @param url 请求地址
     * @return http响应
     */
    public static HttpResponse postJson(String url) {
        return postJson(url, null);
    }

    /**
     * http post Json流请求
     *
     * @param url   请求地址
     * @param param 请求参数 1. JSONObject 2. 实体
     * @return http响应
     */
    public static HttpResponse postJson(String url, Object param) {
        return postJson(url, param, null);
    }

    /**
     * http post Json流请求
     *
     * @param url    请求地址
     * @param param  请求参数 1. JSONObject 2. 实体
     * @param header 请求头
     * @return http响应
     */
    public static HttpResponse postJson(String url, Object param, JSONObject header) {
        return postJson(url, param, header, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * http post Json流请求
     *
     * @param url     请求地址
     * @param param   请求参数 1. JSONObject 2. 实体
     * @param header  请求头
     * @param charset 字符集
     * @return http响应
     */
    public static HttpResponse postJson(String url, Object param, JSONObject header, Charset charset) {
        return postJson(url, param, header, charset, MAX_TIME_OUT, false);
    }

    /**
     * http post Json流请求
     *
     * @param url       请求地址
     * @param param     参数
     * @param header    请求头
     * @param charset   字符集 默认 UTF-8
     * @param timeout   超时时间 单位：毫秒<br> 默认 3秒
     * @param dynamicIp 是否动态Ip  true: 动态
     * @return http响应
     */
    public static HttpResponse postJson(String url, Object param, JSONObject header, Charset charset, int timeout, boolean dynamicIp) {
        Assert.isTrue(StringUtils.isNotBlank(url), "POST json 请求url不能为空");
        // 设置默认字符集
        charset = Optional.ofNullable(charset).orElse(CharsetUtil.CHARSET_UTF_8);
        // 设置超时时间
        timeout = timeout >= MAX_TIME_OUT ? MAX_TIME_OUT : timeout;
        HttpRequest request = HttpRequest.post(url).charset(charset).timeout(timeout);
        if (ObjectUtils.isNotEmpty(param)) {
            String bodyJson = JSONObject.toJSONString(param);
            request.body(bodyJson);
        }
        setRequestAndHeader(header, dynamicIp, request);
        return request.execute();
    }

    /**
     * 设置请求信息
     *
     * @param param     参数
     * @param header    请求头
     * @param dynamicIp 是否动态Ip  true: 动态
     * @param request   请求
     */
    private static void setParam(Object param, JSONObject header, boolean dynamicIp, HttpRequest request) {
        if (param != null) {
            // 判断参数类型
            if (param instanceof JSONObject) {
                request.form((JSONObject) param);
            } else {
                request.form(BeanUtil.beanToMap(param));
            }
        }
    }

    /**
     * 设置请求IP 和 请求头
     *
     * @param header    请求头
     * @param dynamicIp 字符集 默认 UTF-8
     * @param request   请求
     */
    private static void setRequestAndHeader(JSONObject header, boolean dynamicIp, HttpRequest request) {
        // 是否动态IP请求
        if (BooleanUtils.isTrue(dynamicIp)) {
            header = Optional.ofNullable(header).orElse(new JSONObject());
            String ip = randIp();
            header.fluentPut("X-Forwarded-For", ip).fluentPut("HTTP_X_FORWARDED_FOR", ip).fluentPut("HTTP_CLIENT_IP", ip).fluentPut("REMOTE_ADDR", ip);
        }
        // 拼接请求头
        if (MapUtil.isNotEmpty(header)) {
            Set<Map.Entry<String, Object>> entries = header.entrySet();
            entries.forEach(item -> request.header(item.getKey(), String.valueOf(item.getValue())));
        }
    }

    /**
     * 生成随机IP
     *
     * @return 随机IP
     */
    private static String randIp() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1);
    }
}
