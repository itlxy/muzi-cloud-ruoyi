package com.muzi.common.core.utils.poi;

import com.muzi.common.core.annotation.Excel;

/**
 * Excel数据格式处理适配器
 * 
 * @author muzi
 */
public interface ExcelHandlerAdapter
{
    /**
     * 导出时对象值格式化
     * 
     * @param value 单元格数据值
     * @param excel excel注解
     *
     * @return 处理后的值
     */
    Object format(Object value, Excel excel);

    /**
     * 导入时对象值格式化
     *
     * @param value 单元格数据值
     * @param excel excel注解
     *
     * @return 处理后的值
     */
    Object parse(Object value, Excel excel);
}
