package com.muzi.example.page.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muzi.common.core.annotation.Excel;
import com.muzi.common.core.annotation.QueryResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;




/**
 * 案例-人员VO对象
 *
 * @author muzi
 *
 */
@Data
@ApiModel(value = "案例-人员对象 demo_person", description = "案例-人员对象 demo_person")
public class DemoPersonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @QueryResult
    @Excel(name = "主键")
    private Long  id;

    @ApiModelProperty(value = "姓名")
    @QueryResult
    @Excel(name = "姓名")
    private String  name;

    @ApiModelProperty(value = "年龄")
    @QueryResult
    @Excel(name = "年龄",suffix="岁")
    private Integer  age;

    @ApiModelProperty(value = "开始时间")
    @QueryResult
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  startTime;

    @ApiModelProperty(value = "是否已成年（true=成年,false=未成年）")
    @QueryResult
    @Excel(name = "是否已成年", readConverterExp = "true=成年,false=未成年")
    private Boolean  adultFlag;

    @ApiModelProperty(value = "金钱")
    @QueryResult
    @Excel(name = "金钱",scale=2,suffix="元")
    private BigDecimal  money;

    @ApiModelProperty(value = "出生日期")
    @QueryResult
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate  birDate;

    @ApiModelProperty(value = "出生时间")
    @QueryResult
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "出生时间", width = 30, dateFormat = "HH:mm:ss")
    private LocalTime  birTime;

    @ApiModelProperty(value = "备注")
    @QueryResult
    @Excel(name = "备注")
    private String  remark;

    @ApiModelProperty(value = "创建时间")
    @QueryResult
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  createTime;

    @ApiModelProperty(value = "创建人")
    @QueryResult
    @Excel(name = "创建人")
    private String  createBy;

    @ApiModelProperty(value = "更新时间")
    @QueryResult
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  updateTime;

    @ApiModelProperty(value = "更新人")
    @QueryResult
    @Excel(name = "更新人")
    private String  updateBy;

    @ApiModelProperty(value = "是否删除")
    @QueryResult
    @Excel(name = "是否删除")
    private Boolean  delFlag;

    @ApiModelProperty(value = "版本号")
    @QueryResult
    @Excel(name = "版本号")
    private Integer  recordVersion;


}