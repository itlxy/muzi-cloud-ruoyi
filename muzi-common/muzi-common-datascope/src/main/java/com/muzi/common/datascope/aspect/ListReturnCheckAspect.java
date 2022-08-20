package com.muzi.common.datascope.aspect;


import com.muzi.common.core.exception.ServiceException;
import com.muzi.common.core.web.domain.ResponseEnum;
import com.muzi.common.datascope.annotation.ListReturnCheck;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 动态条数操作数据库 更新 删除 新增 操作期望的修改条数 校验
 *
 * @author muzi
 * @date 2022/7/29 16:52
 */
@Aspect
@Component
@Slf4j
public class ListReturnCheckAspect {
    /**
     * 操作数据库影响行数判断切面(dao方法上标注@ListReturnCheck,通过dao方法参数列表的第一个参数设置期望返回值<Integer expect>,
     * 注解的属性info设置影响行数不为期望返回值时抛出异常的message信息)
     *
     * @param joinPoint 连接点
     * @param result    实际影响行数
     */
    @AfterReturning(returning = "result", pointcut = "@annotation(com.muzi.common.datascope.annotation.ListReturnCheck)")
    public void doAfter(JoinPoint joinPoint, int result) {
        Object[] args = joinPoint.getArgs();
        // 获取当前方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ListReturnCheck annotation = method.getDeclaredAnnotation(ListReturnCheck.class);
        if (Objects.nonNull(args[0]) && (args[0] instanceof Integer) && (int) args[0] != result) {
            throw new ServiceException(annotation.info(), ResponseEnum.ERROR.getCode());
        }
    }
}
