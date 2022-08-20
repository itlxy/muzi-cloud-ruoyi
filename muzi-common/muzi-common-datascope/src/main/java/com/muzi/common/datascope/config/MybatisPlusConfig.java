package com.muzi.common.datascope.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.muzi.common.core.constant.MybatisPlusConstants;
import com.muzi.common.core.utils.StringUtils;
import com.muzi.common.core.utils.ip.IpUtils;
import com.muzi.common.security.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName: MybatisPlusConfig
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/14 10:06
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁插件(不走全局限制，只针对重要资源进行控制)
        //interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防止全表更新或删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        String curUserName=getUserName();
        LocalDateTime curDate=LocalDateTime.now();

        // fieldName 是实体中的属性名称
        // 检查是否有这个属性
        boolean createTime = metaObject.hasSetter(MybatisPlusConstants.CREATE_TIME);
        boolean createBy = metaObject.hasSetter(MybatisPlusConstants.CREATE_BY);
        boolean updateTime = metaObject.hasSetter(MybatisPlusConstants.UPDATE_TIME);
        boolean updateBy = metaObject.hasSetter(MybatisPlusConstants.UPDATE_BY);
        boolean delFlag = metaObject.hasSetter(MybatisPlusConstants.DEL_FLAG);
        boolean recordVersion = metaObject.hasSetter(MybatisPlusConstants.RECORD_VERSION);

        //如果有创建时间
        if (createTime) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.CREATE_TIME, LocalDateTime.class, curDate);
        }
        //如果有更新时间
        if (updateTime) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.UPDATE_TIME, LocalDateTime.class, curDate);
        }
        //如果有创建人
        if (createBy) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.CREATE_BY, String.class, curUserName);
        }
        //如果有更新人
        if (updateBy) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.UPDATE_BY, String.class, curUserName);
        }
        //如果有删除标志
        if (delFlag) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.DEL_FLAG, Boolean.class, false);
        }
        //如果有版本记录
        if (recordVersion) {
            this.strictInsertFill(metaObject, MybatisPlusConstants.RECORD_VERSION, Integer.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String curUserName=getUserName();
        LocalDateTime curDate=LocalDateTime.now();

        // 先检查时候已经传入值了，若传入值了，则以传入值为准，就不再自动填充
        Object updateTime = getFieldValByName(MybatisPlusConstants.UPDATE_TIME, metaObject);
        Object updateBy = getFieldValByName(MybatisPlusConstants.UPDATE_BY, metaObject);
        // 若没有传入值才自动填充
        if (updateTime == null) {
            this.strictUpdateFill(metaObject, MybatisPlusConstants.UPDATE_TIME, LocalDateTime.class, curDate);
        }
        if (updateBy == null) {
            this.strictUpdateFill(metaObject, MybatisPlusConstants.UPDATE_BY, String.class, curUserName);
        }
    }

    private String getUserName() {
        String username = SecurityUtils.getUsername();
        if (StringUtils.isBlank(username)) {
            // TODO 暂时默认为sys
            username = "sys";
        }
        return username;
    }
}
