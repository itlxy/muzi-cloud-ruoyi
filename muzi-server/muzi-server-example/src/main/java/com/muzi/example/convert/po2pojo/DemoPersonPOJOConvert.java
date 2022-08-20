package com.muzi.example.convert.po2pojo;

import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.page.excel.DemoPersonExcel;
import com.muzi.example.page.form.DemoPersonForm;
import com.muzi.example.page.vo.DemoPersonVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemoPersonPOJOConvert {
    /** 生成转换实例 */
    DemoPersonPOJOConvert INSTANCE= Mappers.getMapper(DemoPersonPOJOConvert.class);

    /**
     * 实体转vo
     **/
    DemoPersonVO toDemoPersonVO(DemoPersonEntity demoPersonEntity);

    /**
     * 表单对象转实体
     **/
    DemoPersonEntity toDemoPersonEntity(DemoPersonForm demoPersonForm);

    /**
     * excel对象转实体
     **/
    DemoPersonEntity toDemoPersonEntity(DemoPersonExcel demoPersonExcel);

}