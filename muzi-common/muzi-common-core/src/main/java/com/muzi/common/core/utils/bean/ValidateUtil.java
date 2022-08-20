package com.muzi.common.core.utils.bean;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

public final class ValidateUtil {
    public static void validateWithException(Validator validator, Object object, Class<?>... groups)
            throws ConstraintViolationException
    {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty())
        {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * 获取校验信息
     * @param result
     * @return
     */
    public static String getValidationMsg(BindingResult result){
        StringBuffer stringBuffer = new StringBuffer();
        if(result.hasFieldErrors()){
            List<FieldError> errorList = result.getFieldErrors();
            for(int i=0;i< errorList.size();i++){
                FieldError item=errorList.get(i);
                stringBuffer.append(item.getDefaultMessage()).append(",");
            }
            stringBuffer.replace(stringBuffer.length()-1,stringBuffer.length(),";");
        }
        return stringBuffer.toString();
    }

    /**
     * 获取校验信息
     * @param errors
     * @return
     */
    public static <T> String getValidationMsg(Set<ConstraintViolation<T>> errors){
        StringBuffer errorSb = new StringBuffer();
        for (ConstraintViolation<T> error : errors) {
            errorSb.append(error.getMessage());
            errorSb.append(",");
        }
        errorSb.replace(errorSb.length() - 1, errorSb.length(), ";");
        return errorSb.toString();
    }

}
