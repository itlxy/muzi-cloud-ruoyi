package com.muzi.common.log.service;

import cn.craccd.mongoHelper.utils.MongoHelper;
import com.muzi.common.log.entity.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 * 
 * @author muzi
 */
@Service
public class AsyncLogService
{

    @Autowired
    private MongoHelper mongoHelper;

    /**
     * 异步保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog)
    {
        mongoHelper.insert(sysOperLog);
    }
}
