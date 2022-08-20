package com.muzi.common.core.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.common.core.enums.MatchType;
import com.muzi.common.core.enums.MatchType.*;
import com.muzi.common.core.exception.CheckedException;
import com.muzi.common.core.utils.StringUtils;
import com.muzi.common.core.annotation.QueryCondition;
import com.muzi.common.core.annotation.QueryResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * @ClassName: QueryWrapperUtils
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/5/2 0:53
 * @Version: 1.0
 */
public class QueryWrapperUtils {

    /**
     * @Author 11298
     * @Description //根据表构建查询条件，表对应的class强制必填
     * @Date 23:37 2022/5/2
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ENTITY>
     * @Version 1.0
     **/
    public static <R,Q> QueryWrapper getQueryWrapper(Class<R> resultClass,Q condition) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(null!=resultClass){
            queryWrapper.select(getResultColumn(resultClass));
        }

        if (condition == null) {
            return queryWrapper;
        }
        Class clazz = condition.getClass();

        //获取查询类Query的所有字段,包括父类字段
        List<Field> fields = getAllFieldsWithRoot(clazz);

        for (Field field : fields) {

            //获取字段上的@QueryCondition注解
            QueryCondition qw = field.getAnnotation(QueryCondition.class);
            if (qw == null) {
                continue;
            }

            // 获取字段名
            String column = qw.column();

            //如果主注解上colume为默认值"",则以field为准
            if (column.equals("")) {
                column = field.getName();
            }

            field.setAccessible(true);

            try {
                Object value = field.get(condition);
                //如果值为null,注解未标注nullable,跳过
                if (value == null && !qw.nullable()) {
                    continue;
                }

                // can be empty
                if (value != null && String.class.isAssignableFrom(value.getClass())) {
                    String s = (String) value;
                    //如果值为"",且注解未标注emptyable,跳过
                    if (s.equals("") && !qw.emptyable()) {
                        continue;
                    }
                }

                //通过注解上func属性,构建路径表达式
                //如果需要驼峰
                if(qw.camelCase()){
                    //下划线转驼峰
                    column = StringUtils.toCamelCase(column);
                }else {
                    //驼峰转下划线
                    column= StringUtils.toUnderScoreCase(column);
                }

                switch (qw.func()) {
                    case EQ:
                        queryWrapper.eq(column, value);
                        break;
                    case LIKE:
                        queryWrapper.like(column, value);
                        break;
                    case GT:
                        queryWrapper.gt(column, value);
                        break;
                    case LT:
                        queryWrapper.lt(column, value);
                        break;
                    case GTE:
                        queryWrapper.ge(column, value);
                        break;
                    case LTE:
                        queryWrapper.le(column, value);
                        break;
                    case NE:
                        queryWrapper.ne(column, value);
                        break;
                    case NOT_LIKE:
                        queryWrapper.notLike(column,value);
                        break;
                    case IN:
                        if(!(value instanceof Collection) && !(value instanceof Object[])){
                            //IN 只能加在集合或数组元素上
                            throw new CheckedException("IN 只能加在集合或数组元素上");
                        }
                        if(( value instanceof Collection)){
                            queryWrapper.in(column, ((Collection)value).toArray());
                        }else {
                            queryWrapper.in(column,(Object[])value);
                        }
                        break;
                    case NOT_IN:
                        if(!(value instanceof Collection) && !(value instanceof Object[])){
                            //NOT_IN 只能加在集合或数组元素上
                            throw new CheckedException("NOT_IN 只能加在集合或数组元素上");
                        }
                        if(( value instanceof Collection)){
                            queryWrapper.notIn(column, ((Collection)value).toArray());
                        }else {
                            queryWrapper.notIn(column,(Object[])value);
                        }
                        break;
                    case BETWEEN:
                        if(!(value instanceof Collection) && !(value instanceof Object[])){
                            //BETWEEN 只能加在集合或数组元素上
                            throw new CheckedException("BETWEEN 只能加在集合或数组元素上");
                        }
                        if(( value instanceof Collection)){
                            value=((Collection)value).toArray();
                        }
                        if(((Object[])value).length>1){
                            queryWrapper.between(column,((Object[])value)[0],((Object[])value)[1]);
                        }else{
                            //BETWEEN 只能加在集合或数组元素上
                            throw new CheckedException("BETWEEN 注解的集合或数组里的元素必须大于或等于2个，实际只取前两个");
                        }
                        break;
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return queryWrapper;
    }
    private static <RESULT> String[] getResultColumn(Class<RESULT> resultClass){

        //获取查询类Query的所有字段,包括父类字段
        List<Field> fields = getAllFieldsWithRoot(resultClass);

        List<String> columnList=new ArrayList<>(fields.size());
        for (Field field : fields) {
            //获取字段上的@QueryResult注解
            QueryResult qr = field.getAnnotation(QueryResult.class);
            if (qr == null) {
                continue;
            }
            // 获取字段名
            String column = qr.column();
            String fieldName=field.getName();
            //如果主注解上colume为默认值"",则以field为准
            if (column.equals("")) {
                column=fieldName;
                //如果需要转成下划线
                if(qr.underScoreCase()){
                    //驼峰转下划线
                    column= StringUtils.toUnderScoreCase(column);
                }
            }

            column=column+" "+fieldName;

            field.setAccessible(true);


            columnList.add(column);
        }
        if(columnList.size()==0){
            //如果没有查询字段，则查询全部
            //columnList.add("*");
            //由于规范要求，不允许使用select * ，则如果没有指定查询字段，抛出异常,此处使用断言字段列表不是null，是的话，抛出异常
            throw new CheckedException("由于规范要求，不允许使用select *");
        }
        return columnList.toArray(new String[columnList.size()]);
    }

    //获取类clazz的所有Field，包括其父类的Field
    private static List<Field> getAllFieldsWithRoot(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Field[] dFields = clazz.getDeclaredFields();//获取本类所有字段
        if (null != dFields && dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }

        // 若父类是Object，则直接返回当前Field列表
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) {
            return Arrays.asList(dFields);
        }

        // 递归查询父类的field列表
        List<Field> superFields = getAllFieldsWithRoot(superClass);

        if (null != superFields && !superFields.isEmpty()) {
            superFields.stream().
                    filter(field -> !fieldList.contains(field)).//不重复字段
                    forEach(field -> fieldList.add(field));
        }
        return fieldList;
    }
}
