package com.muzi.common.core.web.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @ClassName: BaseParam
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 19:42
 * @Version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求地址
     */
    private String applyUrl;
    /**
     * 请求头
     */
    private JSONObject requestHeader;

}
