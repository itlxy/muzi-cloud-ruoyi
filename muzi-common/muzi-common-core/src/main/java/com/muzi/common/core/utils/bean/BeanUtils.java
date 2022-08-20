package com.muzi.common.core.utils.bean;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import com.muzi.common.core.annotation.BeanToMapAlias;
import com.muzi.common.core.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 * 
 * @author muzi
 */
@Slf4j
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     * 
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     * 
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     * 
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * 
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * 寻找该类的父类和其祖宗十八代的属性
     *
     * @param clazz 类
     * @return 属性数组
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 将Java Bean 转成 Map
     *
     * @param obj Java Bean
     * @return Map
     */
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public static Map<String, Object> javaBeanToMap(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        Class<?> clazz = obj.getClass();
        Field[] allFields = getAllFields(clazz);
        Map<String, Object> map = new HashMap<>(ArrayUtils.getLength(allFields) << 2);
        try {
            for (Field field : allFields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                BeanToMapAlias annotation = field.getAnnotation(BeanToMapAlias.class);
                String def = Strings.EMPTY;
                String alias = field.getName();
                if (ObjectUtils.isNotEmpty(annotation)) {
                    alias = annotation.value();
                    alias = StringUtils.isBlank(alias) ? field.getName() : alias;
                    def = annotation.def();
                }
                Object fieldValue = BeanUtil.getFieldValue(obj, field.getName());
                if (ObjectUtils.isNotEmpty(fieldValue)) {
                    map.put(alias, fieldValue);
                } else if (StringUtils.isNotBlank(def)) {
                    map.put(alias, def);
                }
            }
        } catch (Exception e) {
            log.error("获取Bean字段值异常,{}", obj, e);
        }
        return map;
    }


    /**
     * Map转化为JavaBean
     *
     * @param map   map
     * @param clazz JavaBean.class
     * @param <T>   JavaBean类型
     * @return JavaBean
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
        try {
            Field[] allFields = getAllFields(clazz);
            Map<String, String> mapField = new HashMap<>(ArrayUtils.getLength(allFields) << 2);
            Map<String, String> defValueMap = new HashMap<>(ArrayUtils.getLength(allFields) << 2);
            Arrays.stream(allFields).forEach(item -> {
                String key = item.getName();
                BeanToMapAlias annotation = item.getAnnotation(BeanToMapAlias.class);
                if (ObjectUtils.isNotEmpty(annotation) && StringUtils.isNotBlank(annotation.value())) {
                    key = annotation.value();
                    String def = annotation.def();
                    if (StringUtils.isNotBlank(def)) {
                        defValueMap.put(key, def);
                    }
                }
                mapField.put(key, item.getName());
            });
            T t = clazz.newInstance();
            BeanDesc desc = BeanUtil.getBeanDesc(clazz);
            map.forEach((key, value) -> {
                String fieldName = mapField.get(key);
                if (StringUtils.isNotBlank(fieldName)) {
                    desc.getProp(fieldName).setValue(t, ObjectUtils.isEmpty(value) ? defValueMap.get(key) : value);
                }
            });
            defValueMap.forEach((key, value) -> {
                String fieldName = mapField.get(key);
                if (!map.containsKey(key)) {
                    desc.getProp(fieldName).setValue(t, defValueMap.get(key));
                }
            });
            return t;
        } catch (Exception e) {
            throw new UtilException("Map转换JavaBean异常");
        }
    }

    /**
     * Map转化为JavaBean (下划线转驼峰)
     *
     * @param map   map
     * @param clazz JavaBean.class
     * @param <T>   JavaBean类型
     * @return JavaBean
     */
    public static <T> T mapToBeanUnderscore(Map<String, Object> map, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Field[] allFields = getAllFields(clazz);
            Map<String, Field> fieldMap = Arrays.stream(allFields).collect(Collectors.toMap(Field::getName, Function.identity()));
            BeanDesc desc = BeanUtil.getBeanDesc(clazz);
            map.forEach((key, value) -> {
                String fieldName = lowerFirst(replaceUnderLineAndUpperCase(key));
                Field field = fieldMap.get(fieldName);
                if (ObjectUtils.isNotEmpty(field)) {
                    desc.getProp(fieldName).setValue(t, value);
                }
            });
            return t;
        } catch (Exception e) {
            throw new UtilException("Map转换JavaBean异常");
        }
    }

    /**
     * 下划线转驼峰
     *
     * @param str 下划线字符串
     * @return 驼峰字符串
     */
    public static String replaceUnderLineAndUpperCase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, String.valueOf(ia));
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return StringUtils.capitalize(result);
    }

    /**
     * 实现首字母小写
     *
     * @param name 首字母小写前字符串
     * @return 首字母小写后字符串
     */
    public static String lowerFirst(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
