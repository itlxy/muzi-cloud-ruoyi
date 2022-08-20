package com.muzi.common.core.utils;

import com.muzi.common.core.constant.HttpStatus;
import com.muzi.common.core.web.page.TableDataInfo;
import javafx.util.Pair;

import java.util.List;

/**
 * @ClassName: ConversionPageResultUtil
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/27 17:38
 * @Version: 1.0
 */
public final class ConversionPageResultUtil {
    private ConversionPageResultUtil() {
    }

    /**
     * 分页结果转换
     *
     * @param pair 参数
     * @param <T>  实体
     * @return 结果
     */
    public static <T> TableDataInfo convertTableDataInfo(Pair<Long, List<T>> pair) {
        TableDataInfo result = new TableDataInfo();
        result.setCode(HttpStatus.SUCCESS);
        result.setMsg("查询成功");
        result.setTotal(pair.getKey());
        result.setRows(pair.getValue());
        return result;
    }
}
