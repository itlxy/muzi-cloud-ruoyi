package com.muzi.common.core.web.domain;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: XlsxVO
 * @Description: ,
 * @Author: 11298
 * @DateTime: 2022/7/30 14:32
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XlsxVO<T> implements Serializable {
    private LinkedHashMap<String, String> titleMap;
    private List<T> dataList;
}
