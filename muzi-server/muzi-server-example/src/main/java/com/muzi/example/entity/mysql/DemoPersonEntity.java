package com.muzi.example.entity.mysql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

import com.muzi.common.core.annotation.Excel;
import com.muzi.common.core.constant.DataValidateGroup;
import com.muzi.common.core.annotation.QueryCondition;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.muzi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import com.muzi.common.core.enums.MatchType;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 案例-人员对象 demo_person
 *
 * @author muzi
 * @date 2022-07-31
 */
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
@TableName("demo_person")
@ApiModel(value = "案例-人员对象 demo_person", description = "案例-人员对象 demo_person")
public class DemoPersonEntity extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @NotNull(message = "主键 不能为空",groups = {DataValidateGroup.FormUpdate.class})
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class})
    @QueryCondition(column = "id",func= MatchType.EQ)
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Size(max = 15 ,message = "姓名 的最大长度不能超过 15 位",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.PageQuery.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Excel(name = "姓名",sort=2, prompt="必填", type = Excel.Type.ALL)
    @QueryCondition(column = "name",func= MatchType.LIKE)
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    @NotNull(message = "年龄 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @PositiveOrZero(message = "年龄 必须为大于或等于0的整数",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.PageQuery.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Excel(name = "年龄",sort=3, type = Excel.Type.ALL)
    @QueryCondition(column = "age",func= MatchType.EQ)
    private Integer age;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "开始时间", sort=4, width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", prompt="必填", type = Excel.Type.ALL)
    @QueryCondition(column = "startTime",func= MatchType.EQ)
    private LocalDateTime startTime;

    /**
     * 是否已成年（true=成年,false=未成年）
     */
    @ApiModelProperty(value = "是否已成年（true=成年,false=未成年）")
    @NotNull(message = "是否已成年 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Excel(name = "是否已成年", sort=5, readConverterExp = "true=成年,false=未成年", combo = { "成年","未成年" }, type = Excel.Type.IMPORT)
    @QueryCondition(column = "adultFlag",func= MatchType.EQ)
    private Boolean adultFlag;

    /**
     * 金钱
     */
    @ApiModelProperty(value = "金钱")
    @NotNull(message = "金钱 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Min(value = 0,message ="金钱 必须大于或等于0" ,groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.PageQuery.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Digits(integer = 10, fraction = 5, message = "金钱 的整数位数不能超过 10 位，小数位数不能超过 5 位",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.PageQuery.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Excel(name = "金钱",sort=6, prompt="必填", type = Excel.Type.ALL)
    @QueryCondition(column = "money",func= MatchType.EQ)
    private BigDecimal money;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @NotNull(message = "出生日期 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", sort=7, width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.ALL)
    @QueryCondition(column = "birDate",func= MatchType.EQ)
    private LocalDate birDate;

    /**
     * 出生时间
     */
    @ApiModelProperty(value = "出生时间")
    @NotNull(message = "出生时间 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "出生时间", sort=8, width = 30, dateFormat = "HH:mm:ss", type = Excel.Type.ALL)
    @QueryCondition(column = "birTime",func= MatchType.EQ)
    private LocalTime birTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @NotBlank(message = "备注 不能为空",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Size(max = 255 ,message = "备注 的最大长度不能超过 255 位",groups = {DataValidateGroup.FormUpdate.class,
            DataValidateGroup.PageQuery.class,
            DataValidateGroup.FormSave.class,
            DataValidateGroup.ExcelSave.class})
    @Excel(name = "备注",sort=9, type = Excel.Type.ALL)
    @QueryCondition(column = "remark",func= MatchType.EQ)
    private String remark;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }
    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }
    public void setAdultFlag(Boolean adultFlag)
    {
        this.adultFlag = adultFlag;
    }

    public Boolean getAdultFlag()
    {
        return adultFlag;
    }
    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }
    public void setBirDate(LocalDate birDate)
    {
        this.birDate = birDate;
    }

    public LocalDate getBirDate()
    {
        return birDate;
    }
    public void setBirTime(LocalTime birTime)
    {
        this.birTime = birTime;
    }

    public LocalTime getBirTime()
    {
        return birTime;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("age", getAge())
                .append("startTime", getStartTime())
                .append("adultFlag", getAdultFlag())
                .append("money", getMoney())
                .append("birDate", getBirDate())
                .append("birTime", getBirTime())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("delFlag", getDelFlag())
                .append("recordVersion", getRecordVersion())
                .toString();
    }
}