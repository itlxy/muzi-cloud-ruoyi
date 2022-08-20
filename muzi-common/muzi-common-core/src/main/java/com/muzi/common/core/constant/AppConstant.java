package com.muzi.common.core.constant;

public interface AppConstant {
    /**
     * 应用版本
     */
    String APPLICATION_VERSION = "1.0.0";
    /**
     * 基础报名前缀
     */
    String BASE_PACKAGES = "com.muzi";
    /**
     * 项目名称
     */
    String PROJECT_NAME = "demo-cloud";
    /**
     * 项目全名称
     */
    String PROJECT_FULL_NAME = "demo-cloud";
    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = PROJECT_NAME + "-";
    /**
     * 网关
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
    /**
     * 健康检查模块名称
     */
    String APPLICATION_HEALTH_CHECK_NAME = APPLICATION_NAME_PREFIX + "health-check";
    /**
     * 定时任务模块名称
     */
    String APPLICATION_JOB_NAME = APPLICATION_NAME_PREFIX + "xxljob";
    /**
     * 系统管理模块名称
     */
    String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
    /**
     * 附件模块名称
     */
    String APPLICATION_FILE_NAME = APPLICATION_NAME_PREFIX + "file";
    /**
     * 代码生成模块名称
     */
    String APPLICATION_GEN_NAME = APPLICATION_NAME_PREFIX + "gen";
    /**
     * 认证中心
     */
    String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
    /**
     * 接口文档聚合
     */
    String APPLICATION_KNIFE4J_NAME = APPLICATION_NAME_PREFIX + "knife4j";

    /**
     * 基础数据
     */
    String APPLICATION_BASE_NAME = APPLICATION_NAME_PREFIX + "base";

    /**
     * websocket客户端
     */
    String APPLICATION_WEBSOCKET_NAME = APPLICATION_NAME_PREFIX + "websocket";


    /**
     * 开发环境
     */
    String DEV_CODE = "dev";
    /**
     * 预发环境
     */
    String UAT_CODE = "uat";
    /**
     * 测试环境
     */
    String TEST_CODE = "test";
    /**
     * 生产环境
     */
    String PROD_CODE = "prod";

}
