package com.muzi.example.page.excel.handler;

import com.muzi.common.core.annotation.Excel;
import com.muzi.common.core.utils.poi.ExcelHandlerAdapter;
import com.muzi.example.constant.ExcelConvertConstant;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DictValueLabelConvertHandler
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/20 16:41
 * @Version: 1.0
 */
@Component("dictValueLabelConvertHandler")
public class AdultFlagConvertHandler implements ExcelHandlerAdapter {
    /**
     * @Author 11298
     * @Description //
     * @Date 17:09 2022/7/20
     * @param value 对象属性值
     * @param excel
     * @return java.lang.Object
     * @Version 1.0
     **/
    @Override
    public Object format(Object value, Excel excel) {
        if(Boolean.FALSE.equals(value)){
            return ExcelConvertConstant.AuditFlag.AUDIT_FALSE_TEXT;
        }
        return ExcelConvertConstant.AuditFlag.AUDIT_TRUE_TEXT;
    }

    @Override
    public Object parse(Object value, Excel excel) {
        if(ExcelConvertConstant.AuditFlag.AUDIT_FALSE_TEXT.equals(value)){
            return Boolean.FALSE;
        }else if(ExcelConvertConstant.AuditFlag.AUDIT_TRUE_TEXT.equals(value)){
            return Boolean.TRUE;
        }else {
            return null;
        }

    }
}
