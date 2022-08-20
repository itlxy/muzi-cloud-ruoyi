package com.muzi.example.transaction.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.mapper.mysql.DemoPersonMapper;
import com.muzi.example.transaction.DemoPersonTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DemoPersonTransactionServiceImpl
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/29 16:13
 * @Version: 1.0
 */
@Service
public class DemoPersonTransactionServiceImpl implements DemoPersonTransactionService {
    @Resource
    private DemoPersonMapper demoPersonMapper;

    @Override
    @Transactional
    public void moreUpdateByIdAndVersion(List<DemoPersonEntity> demoPersonList) {
        for(int i=0;i<demoPersonList.size();i++){
            LambdaUpdateWrapper<DemoPersonEntity> updateWrapper=new LambdaUpdateWrapper();
            DemoPersonEntity demoPerson=demoPersonList.get(i);
            updateWrapper.eq(DemoPersonEntity::getId,demoPerson.getId());
            updateWrapper.eq(DemoPersonEntity::getRecordVersion,demoPerson.getRecordVersion());
            //使用注解来判断是否更新成功，失败的话返回异常
            demoPersonMapper.updateByIdAndVersion(1,demoPerson);
        }
    }

    @Override
    @Transactional
    public void batchUpdateDelFlagByIdAndVersion(boolean delFlag,List<DemoPersonEntity> demoPersonList) {
        //使用注解判断是否更新成功，失败返回异常
        demoPersonMapper.batchUpdateDelFlagByIdAndVersion(demoPersonList.size(),delFlag,demoPersonList);
    }
}
