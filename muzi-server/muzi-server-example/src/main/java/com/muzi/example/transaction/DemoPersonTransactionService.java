package com.muzi.example.transaction;

import com.muzi.example.entity.mysql.DemoPersonEntity;

import java.util.List;

public interface DemoPersonTransactionService {

    /**
     * @Author 11298
     * @Description //事务多条更新案例人员
     * @Date 16:43 2022/7/29
     * @param demoPersonList
     * @Version 1.0
     **/
    void moreUpdateByIdAndVersion(List<DemoPersonEntity> demoPersonList);

    /**
     * @Author 11298
     * @Description //事务批量更新案例人员
     * @Date 16:43 2022/7/29
     * @param demoPersonList
     * @Version 1.0
     **/
    void batchUpdateDelFlagByIdAndVersion(boolean delFlag,List<DemoPersonEntity> demoPersonList);
}
