package com.muzi.common.core.utils;

import com.muzi.common.core.constant.Constants;
import com.muzi.common.core.constant.GenConstants;
import com.muzi.common.core.exception.ServiceException;
import org.apache.commons.compress.utils.Lists;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CommonUtils
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/5/3 1:13
 * @Version: 1.0
 */
public class CommonUtils {
    /**
     * 把source转为target
     * @param source source
     * @param target target
     * @param <T> 返回值类型
     * @return 返回值
     * @throws Exception newInstance可能会抛出的异常
     */
    public static <T> T mapToObj(Map<String,Object> source, Class<T> target) throws Exception {
        Field[] fields = target.getDeclaredFields();
        T object = target.newInstance();
        for(Field field:fields){
            Object val;
            if((val=source.get(field.getName()))!=null){
                field.setAccessible(true);
                field.set(object,val);
            }
        }
        return object;
    }

    /**
     * @Author 11298
     * @Description //TODO
     * @Date 12:51 2022/5/3
     * @param sourceList
     * @param target
     * @return java.util.List<T>
     * @Version 1.0
     **/
    public static <T> List<T> mapToObj(List<Map<String,Object>> sourceList, Class<T> target) throws Exception {
        //Field[] fields = target.getDeclaredFields();
        //获取当前及父级字段
        List<Field> fields=getFields(target);
        List<T> targetObjList=new ArrayList<>();
        for(int i=0;i<sourceList.size();i++){
            Map<String,Object> source =sourceList.get(i);
            T object = target.newInstance();
            for(Field field:fields){
                Object val;
                if((val=source.get(field.getName()))!=null){
                    field.setAccessible(true);
                    if(val instanceof java.sql.Date){
                        val=((java.sql.Date)val).toLocalDate();
                    }
                    else if(val instanceof java.sql.Time){
                        val=((java.sql.Time)val).toLocalTime();
                    }
                    field.set(object,val);
                }
            }
            targetObjList.add(object);
        }

        return targetObjList;
    }

    /**
     * 数字转换成字母
     */
    public static String numToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);

        return letter;
    }
    private static List<Field> getFields(Class clazz){
        Field[] fields = null;
        List<Field> fieldList = Lists.newArrayList();
        while (true) {
            if (clazz == null) {
                break;
            } else {
                fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fieldList.add(fields[i]);
                }
                clazz = clazz.getSuperclass();
            }
        }
        return fieldList;
    }
    /**
     * 数字转换成boolean
     */
    public static boolean numToBoolean(int num) {
        if (num <= 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 数字转换成boolean
     */
    public static boolean numStrToBoolean(String num) {
        if (Integer.parseInt(num) <= 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 数字转换成boolean
     */
    public static boolean strToBoolean(String booleanStr) {
        if (arraysContains(Constants.FALSE_STR, booleanStr)) {
            return false;
        } else if(arraysContains(Constants.TRUE_STR, booleanStr)) {
            return true;
        }else {
            throw new ServiceException("boolean 变量转换失败");
        }
    }

    public static boolean arraysContains(String[] arr, String targetValue)
    {
        return Arrays.asList(arr).contains(targetValue);
    }
}
