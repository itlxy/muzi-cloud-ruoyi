package com.muzi.example.page.excel;


import com.muzi.common.core.annotation.Excel;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 案例-人员Excel对象
 *
 * @author muzi
 * @date 2022-07-22
 */
@Data
@ApiModel(value = "案例-人员excel导入", description = "案例-人员excel导入对象")
public class DemoPersonExcel extends DemoPersonEntity implements Serializable {

    /**
     * 是否已成年
     */
    @ApiModelProperty(value = "是否已成年")
    @Excel(name = "是否已成年",sort=5,type = Excel.Type.EXPORT)
    private String adultFlagName;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    @Excel(name = "错误信息",type = Excel.Type.EXPORT)
    private String errorRemark;

}
