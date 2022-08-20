package com.muzi.common.nacos.constants;


import com.alibaba.nacos.common.utils.StringUtils;

/**
 * nacos 配置文件：name-profile.format
 */
public interface NacosConfigValueConstant {

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

    /**
     * nacos 共享配置前缀
     */
    String CONFIG_SHARE_NAME = "muzi-application";

    /**
     * nacos 配置中心文件后缀
     */
    String CONFIG_FORMAT_YML = "yml";

    /**
     * nacos 配置刷新开启
     */
    String CONFIG_REFRESH_OPEN="true";
    /**
     * nacos 配置刷新关闭
     */
    String CONFIG_REFRESH_CLOSE="false";

    /**
     * nacos 配置中心启用
     */
    String CONFIG_OPEN="true";

    /**
     * nacos 配置中心禁用
     */
    String CONFIG_CLOSE="false";

    /**
     * nacos dev group 名称
     */
    String DEV_GROUP="DEFAULT_GROUP";

    /**
     * nacos test group 名称
     */
    String TEST_GROUP="DEFAULT_GROUP";

    /**
     * nacos uat group 名称
     */
    String UAT_GROUP="DEFAULT_GROUP";

    /**
     * nacos prop group 名称
     */
    String PROD_GROUP="DEFAULT_GROUP";



    /**
     * nacos dev 地址
     */
    String DEV_ADDR = "127.0.0.1:8848";

    /**
     * nacos prod 地址
     */
    String PROD_ADDR = "127.0.0.1:8848";

    /**
     * nacos test 地址
     */
    String TEST_ADDR = "127.0.0.1:8848";

    /**
     * nacos uat 地址
     */
    String UAT_ADDR = "127.0.0.1:8848";

    /**
     * nacos dev namespaces
     */
    String DEV_NAMESPACE = "public";

    /**
     * nacos prod namespaces
     */
    String PROD_NAMESPACE = "cloud-dev-lxy";

    /**
     * nacos test namespaces
     */
    String TEST_NAMESPACE = "";

    /**
     * nacos uat namespaces
     */
    String UAT_NAMESPACE = "";

    /**
     * 动态获取nacos地址
     *
     * @param profile 环境变量
     * @return addr
     */
    static String nacosAddr(String profile) {
        switch (profile) {
            case (PROD_CODE):
                return NacosConfigValueConstant.PROD_ADDR;
            case (TEST_CODE):
                return NacosConfigValueConstant.TEST_ADDR;
            case (UAT_CODE):
                return NacosConfigValueConstant.UAT_ADDR;
            default:
                return NacosConfigValueConstant.DEV_ADDR;
        }
    }

    /**
     * 动态获取nacos namespace
     *
     * @param profile 环境变量
     * @return namespace
     */
    static String nacosNamespace(String profile) {
        switch (profile) {
            case (PROD_CODE):
                return NacosConfigValueConstant.PROD_NAMESPACE;
            case (TEST_CODE):
                return NacosConfigValueConstant.TEST_NAMESPACE;
            case (UAT_CODE):
                return NacosConfigValueConstant.UAT_NAMESPACE;
            default:
                return NacosConfigValueConstant.DEV_NAMESPACE;
        }
    }
    /**
     * 动态获取nacos group
     *
     * @param profile 环境变量
     * @return namespace
     */
    static String nacosGroupe(String profile) {
        switch (profile) {
            case (PROD_CODE):
                return NacosConfigValueConstant.PROD_GROUP;
            case (TEST_CODE):
                return NacosConfigValueConstant.TEST_GROUP;
            case (UAT_CODE):
                return NacosConfigValueConstant.UAT_GROUP;
            default:
                return NacosConfigValueConstant.DEV_GROUP;
        }
    }

    static String shareConfig(String profile){
        return dataId(CONFIG_SHARE_NAME,profile);
    }
    static String dataId(String appName,String profile){
        return appName + (StringUtils.isNotEmpty(profile)? "-" + profile:"") + "." + CONFIG_FORMAT_YML;
    }

}
