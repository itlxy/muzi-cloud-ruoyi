package com.muzi.common.core.web.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.muzi.common.core.exception.ServiceException;
import com.muzi.common.core.utils.bean.BeanUtils;
import com.muzi.common.core.web.domain.ResponseEnum;
import com.muzi.common.core.annotation.AutoFill;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据自动填充(暂未使用)
 *
 * @author muzi
 * @DateTime: 2022/7/27 19:42
 */
@SuppressWarnings("unused")
@Slf4j
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 数据自动填充类型
     */
    private static final Map<Class<?>, Object> TYPE_MAP = new HashMap<Class<?>, Object>() {
        private static final long serialVersionUID = 6336288607166360591L;

        {
            put(Date.class, new Date());
            put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
            put(LocalDateTime.class, LocalDateTime.now());
            put(LocalDate.class, LocalDate.now());
            put(LocalTime.class, LocalTime.now());
        }
    };

    /**
     * 插入时自动填充数据
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            this.autoInsertFill(metaObject);
        } catch (Exception e) {
            log.error("插入是自动填充数据失败", e);
            throw new ServiceException("插入是自动填充数据失败");
        }
    }

    /**
     * 更新时自动填充数据
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            this.autoUpdateFill(metaObject);
        } catch (Exception e) {
            log.error("更新时自动填充数据失败", e);
            throw new ServiceException("更新时自动填充数据失败");
        }
    }

    /**
     * 插入自动填充
     *
     * @param metaObject 元对象
     * @throws ServiceException 异常
     */
    private void autoInsertFill(MetaObject metaObject) throws ServiceException {
        // 插入时元对象 getOriginalObject 就是操作的实体
        Class<?> clazz = metaObject.getOriginalObject().getClass();
        Field[] declaredFields = BeanUtils.getAllFields(clazz);
        // 获取所有需要插入填充的属性
        for (Field field : declaredFields) {
            TableField tableField = field.getAnnotation(TableField.class);
            AutoFill autoFill = field.getAnnotation(AutoFill.class);
            // 该属性存在以上两个注解
            if (tableField == null || autoFill == null) {
                continue;
            }
            // 字段填充类型是插入。
            if (tableField.fill() == FieldFill.INSERT || tableField.fill() == FieldFill.INSERT_UPDATE) {
                this.setAutoFillValue(metaObject, field);
            }
        }
    }

    /**
     * 更新自动填充
     *
     * @param metaObject 元对象
     * @throws ServiceException 异常
     */
    @SuppressWarnings("rawtypes")
    private void autoUpdateFill(MetaObject metaObject) throws ServiceException {
        // 在Service中使用baseMapper对对象进行更新时
        // metaObject元对象的getOriginalObject 是一个Map
        // 需要取得Map的 et或param 所对应的字段才是操作的实体
        Class<?> clazz;
        try {
            clazz = ((HashMap) metaObject.getOriginalObject()).get("et").getClass();
        } catch (ClassCastException e) {
            clazz = metaObject.getOriginalObject().getClass();
        }
        Field[] declaredFields = BeanUtils.getAllFields(clazz);
        // 获取所有需要更新插入填充的属性
        for (Field field : declaredFields) {
            TableField tableField = field.getAnnotation(TableField.class);
            AutoFill autoFill = field.getAnnotation(AutoFill.class);
            // 该属性存在以上两个注解
            if (tableField == null || autoFill == null) {
                continue;
            }
            // 字段填充类型是更新。
            if (tableField.fill() == FieldFill.UPDATE || tableField.fill() == FieldFill.INSERT_UPDATE) {
                this.setAutoFillValue(metaObject, field);
            }
        }
    }

    /**
     * 设置自动填充值。
     *
     * @param metaObject 元对象
     * @param field      需要填充的字段
     * @throws ServiceException 异常
     */
    private void setAutoFillValue(MetaObject metaObject, Field field) throws ServiceException {
        AutoFill autoFill = field.getAnnotation(AutoFill.class);
        if (StringUtils.isBlank(autoFill.method())) {
            Class<?> type = field.getType();
            Object o = TYPE_MAP.get(type);
            this.setFieldValByName(field.getName(), o, metaObject);
            return;
        }
        // 获取方法名
        String methodName = autoFill.method();
        // autoFill 中的值不为空 调用所指定类的指定方法
        try {
            // 类名
            Class<?> clz = autoFill.klass();
            // 方法名
            Method method = clz.getMethod(methodName);
            // 调用方法。
            Object invoke = method.invoke(clz.newInstance());
            // 将调用后的返回结果填充到该属性上。
            this.setFieldValByName(field.getName(), invoke, metaObject);
        } catch (NoSuchMethodException e) {
            throw new ServiceException("找不到方法：" + methodName);
        } catch (InvocationTargetException ignored) {
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.ERROR.getMessage());
        }
    }
}

