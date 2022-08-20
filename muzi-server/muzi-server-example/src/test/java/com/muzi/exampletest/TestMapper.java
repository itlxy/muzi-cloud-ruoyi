package com.muzi.exampletest;

import com.muzi.example.MuziExampleApplication;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.mapper.mysql.DemoPersonMapper;
import com.muzi.example.transaction.DemoPersonTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestMapper
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/7/29 16:55
 * @Version: 1.0
 */
@SpringBootTest(classes = MuziExampleApplication.class)
public class TestMapper {
    @Resource
    private DemoPersonMapper demoPersonMapper;
    @Resource
    private DemoPersonTransactionService demoPersonTransactionService;
    @Test
    public void testUpdateCheck(){
        DemoPersonEntity demoPerson=new DemoPersonEntity();
        demoPerson.setRecordVersion(1);
        demoPerson.setId(1L);
        int i=demoPersonMapper.updateByIdAndVersion(1,demoPerson);
        System.out.println(i);
    }

    @Test
    public void testUpdateCheckList(){
        DemoPersonEntity demoPerson=new DemoPersonEntity();
        demoPerson.setRecordVersion(1);
        demoPerson.setId(1L);
        List<DemoPersonEntity> demoPersonList=new ArrayList<>();
        demoPersonList.add(demoPerson);
        int i=demoPersonMapper.batchUpdateDelFlagByIdAndVersion(demoPersonList.size(), false,demoPersonList);
        System.out.println(i);
    }

    @Test
    public void testUpdateCheckListEntity(){
        DemoPersonEntity demoPerson=new DemoPersonEntity();
        demoPerson.setRecordVersion(1);
        demoPerson.setId(1L);
        List<DemoPersonEntity> demoPersonList=new ArrayList<>();
        demoPersonList.add(demoPerson);
        int i=demoPersonMapper.batchUpdateByIdAndVersion(demoPersonList.size(), demoPerson,demoPersonList);
        System.out.println(i);
    }

    @Test
    public void testMoreUpdateByIdAndVersion(){
        List<DemoPersonEntity> demoPersonList=new ArrayList<>();
        for(int i=0;i<5;i++){
            DemoPersonEntity demoPerson=new DemoPersonEntity();
            demoPerson.setRecordVersion(1);
            demoPerson.setId(1L);
            demoPersonList.add(demoPerson);
        }
        Boolean i=demoPersonMapper.moreUpdateByIdAndVersion(demoPersonList);
        System.out.println(i);
    }

    @Test
    public void testDemoPersonTransactionService(){
        List<DemoPersonEntity> demoPersonList=new ArrayList<>();
        for(int i=0;i<5;i++){
            DemoPersonEntity demoPerson=new DemoPersonEntity();
            demoPerson.setRecordVersion(1);
            demoPerson.setId(1L);
            demoPersonList.add(demoPerson);
        }
        demoPersonTransactionService.moreUpdateByIdAndVersion(demoPersonList);
    }

}
