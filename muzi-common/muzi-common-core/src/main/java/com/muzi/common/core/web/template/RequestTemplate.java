package com.muzi.common.core.web.template;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muzi.common.core.utils.bean.BeanValidateUtil;
import com.muzi.common.core.enums.RequestMethodEnum;
import com.muzi.common.core.exception.ServiceException;
import com.muzi.common.core.web.domain.BaseParam;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * 请求三方系统 模版
 *
 * @author muzi
 * @DateTime: 2022/7/27 19:42
 */
@FunctionalInterface
public interface RequestTemplate<T extends BaseParam, R> {
    /**
     * 日志
     */
    Logger log = LoggerFactory.getLogger(RequestTemplate.class);

    /**
     * 处理响应结果的方法
     *
     * @param resultJson 响应体字符串
     * @return 结果
     */
    R apply(String resultJson);

    /**
     * 执行方法
     *
     * @param clazz         验证类型 为null  则不校验参数
     * @param param         请求参数
     * @param requestMethod 请求方法
     * @return 结果
     */
    default R execute(RequestMethodEnum requestMethod, T param, Class<?>... clazz) {
        Assert.notNull(requestMethod, "请求方法不能为空");
        if (ObjectUtils.isNotEmpty(clazz)) {
            // 校验参数
            String checkMessage = BeanValidateUtil.validate(param, clazz);
            if (StringUtils.isNotBlank(checkMessage)) {
                throw new ServiceException("请求三方系统 参数不合法! [" + checkMessage + "]");
            }
        }
        param = Optional.ofNullable(param).orElseThrow(() -> new ServiceException("请求参数不能为空"));
        // 请求地址
        String url = param.getApplyUrl();
        // 请求头
        JSONObject requestHeader = Optional.ofNullable(param.getRequestHeader()).orElse(new JSONObject());
        param.setApplyUrl(null);
        param.setRequestHeader(null);
        String paramJson = JSON.toJSONString(param);
        log.info("请求三方系统 地址: [{}] 参数: [{}] 请求头: [{}]", url, paramJson, requestHeader.toJSONString());
        HttpResponse httpResponse = requestMethod.getExecuteRequestFunction().apply(url, param, requestHeader);
        if (!httpResponse.isOk()) {
            log.warn("请求三方系统Http状态码非200  地址: [{}] 参数: [{}] 响应: [{}]", url, paramJson, JSONObject.toJSONString(httpResponse));
            throw new ServiceException("请求三方系统Http状态码非200");
        }
        // 响应体
        String resultJson = httpResponse.body();
        if (StringUtils.isBlank(resultJson)) {
            log.warn("请求: 地址: [{}] 参数: [{}] 响应为空", url, paramJson);
            throw new ServiceException("请求失败, 响应为空");
        }
        log.info("请求三方系统 地址: [{}] 参数: [{}] 响应: [{}]", url, paramJson, resultJson);
        // 解析响应结果
        try {
            return this.apply(resultJson);
        } catch (Exception e) {
            log.error("解析响应结果失败", e);
            throw new RuntimeException("解析三方响应结果失败", e);
        }
    }
}

