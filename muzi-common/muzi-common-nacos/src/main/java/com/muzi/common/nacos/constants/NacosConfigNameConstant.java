package com.muzi.common.nacos.constants;

/**
 * @ClassName: NacosConfigNameConstant
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/19 10:57
 * @Version: 1.0
 */
public interface NacosConfigNameConstant {

    /*--------------------nacos config 配置属性名-----------------------*/
    /**
     * 配置中心是否开启
     */
    String CONFIG="spring.cloud.nacos.config.enabled";
    /**
     * 配置中心地址
     */
    String CONFIG_ADDR="spring.cloud.nacos.config.server-addr";
    /**
     * 配置中心命名空间
     */
    String CONFIG_NAMESPACE="spring.cloud.nacos.config.namespace";
    /**
     * 配置中心分组
     */
    String CONFIG_GROUP="spring.cloud.nacos.config.group";
    /**
     * 配置中心文件名
     */
    String CONFIG_PREFIX="spring.cloud.nacos.config.prefix";
    /**
     * 配置中心配置文件后缀
     */
    String CONFIG_FILE_EXTENSION="spring.cloud.nacos.config.file-extension";
    /**
     * 配置中心配置刷新
     */
    String CONFIG_REFRESH="spring.cloud.nacos.config.refresh-enabled";

    /**
     * 配置中心共享配置
     */
    String CONFIG_SHARE_0="spring.cloud.nacos.config.shared-configs[0]";
    /**
     * 配置中心共享配置
     */
    String CONFIG_SHARE_1="spring.cloud.nacos.config.shared-configs[1]";
    /**
     * 配置中心共享配置
     */
    String CONFIG_SHARE_2="spring.cloud.nacos.config.shared-configs[2]";
    /**
     * 配置中心共享配置
     */
    String CONFIG_SHARE_3="spring.cloud.nacos.config.shared-configs[3]";

    /*--------------------nacos discovery 服务注册属性名-----------------------*/

    /**
     * 服务注册地址
     */
    String DISCOVERY_ADDR="spring.cloud.nacos.discovery.server-addr";

    /**
     * 服务注册命名空间
     */
    String DISCOVERY_NAMESPACE="spring.cloud.nacos.discovery.namespace";

    /**
     * 服务注册分组
     */
    String DISCOVERY_GROUP="spring.cloud.nacos.discovery.group";
    /**
     * 服务注册是否开启
     */
    String DISCOVERY="spring.cloud.nacos.discovery.enabled";

}
