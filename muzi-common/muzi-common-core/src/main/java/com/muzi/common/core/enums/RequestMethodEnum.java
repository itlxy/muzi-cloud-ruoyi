package com.muzi.common.core.enums;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.muzi.common.core.function.ThreeFunction;
import com.muzi.common.core.utils.http.HuHttpUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: RequestMethodEnum
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 19:42
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnum {
    /**
     * Get请求
     */
    GET(HuHttpUtils::get),
    /**
     * form表单POST请求
     */
    POST(HuHttpUtils::post),
    /**
     * JSON流POST请求
     */
    POST_JSON(HuHttpUtils::postJson);

    /**
     * 执行请求的方法
     */
    private final ThreeFunction<String, Object, JSONObject, HttpResponse> executeRequestFunction;


}

