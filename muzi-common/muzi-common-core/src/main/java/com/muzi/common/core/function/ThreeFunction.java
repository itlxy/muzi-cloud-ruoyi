package com.muzi.common.core.function;

/**
 * 扩展JDK function 包 三个参数 有返回值
 * <br/>
 * 节约封住实体 的一步
 *
 * @author muzi
 * @DateTime: 2022/7/27 19:42
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface ThreeFunction<T, U, I, R> {

    /**
     * 方法
     *
     * @param t 参数一
     * @param u 参数二
     * @param i 参数三
     * @return 结果
     */
    R apply(T t, U u, I i);
}
