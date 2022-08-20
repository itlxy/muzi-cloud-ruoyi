package com.muzi.common.nacos.environment;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.muzi.common.nacos.constants.NacosConfigNameConstant;
import com.muzi.common.nacos.constants.NacosConfigValueConstant;
import com.muzi.common.nacos.constants.SpringConfigNameConstant;
import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.util.stream.Collectors;

/**
 * @author 11298
 * 按照加载顺序读取配置，相同的配置名，只会保留第一个加载的，放弃后加载的
 * 相同隔离环境，会移除前一个后，再添加新的
 */
public class CustomApplicationProperties implements EnvironmentPostProcessor {
    private final String CUSTOM_CONFIG_FLAG="bootstrap";
    private final Log log;
    public CustomApplicationProperties(DeferredLogFactory deferredLogFactory){
        log=deferredLogFactory.getLog(CustomApplicationProperties.class);
    }


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        // 所有属性文件名
        String[] env = environment.getActiveProfiles();
        String activeProfile=env.length>0?env[0]:"";
        String appName=environment.getProperty(SpringConfigNameConstant.APPLICATION_NAME);
        List<PropertySource<?>> applicationConfig = propertySources.stream().filter(p -> p instanceof OriginTrackedMapPropertySource && p.getName().contains("Config resource")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(applicationConfig)){
            return;
        }

        log.info("================== 用户应用内定义配置加载开始 ==================");
        Map<String,Map<String, Object>> configSource=new HashMap<>(applicationConfig.size()+1);
        applicationConfig.forEach(ele->{
            int start=ele.getName().indexOf(CUSTOM_CONFIG_FLAG);
            int end=ele.getName().indexOf(".");
            String configName=ele.getName().substring(start,end);
            configSource.put(configName,((MapPropertySource)ele).getSource());
        });
        log.info("    加载成功的用户自定义的配置文件名："+configSource.keySet());
        log.info("    ================== 公共配置"+CUSTOM_CONFIG_FLAG+" ==================");
        configSource.get(CUSTOM_CONFIG_FLAG).forEach((k,v)->log.info("    "+k+":"+v.toString()));
        for(String curEnv:configSource.keySet()){
            if(curEnv.equals(CUSTOM_CONFIG_FLAG)){
                continue;
            }
            log.info("    ================== 激活环境配置"+curEnv+" ==================");
            configSource.getOrDefault(curEnv,new HashMap<>(1)).forEach((k,v)->log.info("    "+k+":"+v.toString()));
        }
        log.info("    ================== 额外默认配置 ==================");
        Properties props = new Properties();
        props.setProperty(NacosConfigNameConstant.DISCOVERY, "true");
        props.setProperty(NacosConfigNameConstant.DISCOVERY_ADDR, NacosConfigValueConstant.nacosAddr(activeProfile));
        //props.setProperty(NacosConfigNameConstant.DISCOVERY_NAMESPACE, NacosConstant.nacosNamespace(activeProfile));

        props.setProperty(NacosConfigNameConstant.CONFIG_ADDR, NacosConfigValueConstant.nacosAddr(activeProfile));
        //props.setProperty(NacosConfigNameConstant.CONFIG_NAMESPACE, NacosConstant.nacosNamespace(activeProfile));
        props.setProperty(NacosConfigNameConstant.CONFIG_FILE_EXTENSION, NacosConfigValueConstant.CONFIG_FORMAT_YML);
        props.setProperty(NacosConfigNameConstant.CONFIG_PREFIX, appName);
        props.setProperty(NacosConfigNameConstant.CONFIG_SHARE_0, NacosConfigValueConstant.shareConfig(activeProfile));
        props.setProperty(NacosConfigNameConstant.CONFIG_GROUP, NacosConfigValueConstant.nacosGroupe(activeProfile));
        props.setProperty(NacosConfigNameConstant.CONFIG_REFRESH, NacosConfigValueConstant.CONFIG_REFRESH_OPEN);

        for(String key:props.stringPropertyNames()){
            log.info("    "+key+":"+props.getProperty(key));
        }
        log.info("================== 用户应用内定义配置加载完成 ==================");
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("remoteConfigCenter", props);
        environment.getPropertySources().addLast(propertiesPropertySource);
    }

}
