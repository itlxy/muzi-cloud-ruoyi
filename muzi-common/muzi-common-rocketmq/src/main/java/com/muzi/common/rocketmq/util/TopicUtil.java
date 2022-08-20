package com.muzi.common.rocketmq.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TopicUtil
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/12 15:26
 * @Version: 1.0
 */
@Component
public class TopicUtil {
    @Value("${rocketmq.versions}")
    private String rocketVersion;

    public String getTopic(String topic){
        return rocketVersion+topic;
    }
    public String getTopicAndTag(String topic,String tag){
        StringBuilder sb=new StringBuilder();
        sb.append(rocketVersion)
                .append(topic)
                .append(":")
                .append(tag);

        return sb.toString();
    }
}
