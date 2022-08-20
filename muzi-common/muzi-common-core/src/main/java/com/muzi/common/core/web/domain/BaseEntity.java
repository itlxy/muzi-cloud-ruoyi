package com.muzi.common.core.web.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.muzi.common.core.annotation.QueryCondition;
import com.muzi.common.core.constant.DataValidateGroup;
import com.muzi.common.core.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Entity基类
 *
 * @author muzi
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
                                        DataValidateGroup.FormUpdate.class})
    @TableField(fill = FieldFill.INSERT)
    @QueryCondition
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
            DataValidateGroup.FormUpdate.class})
    @QueryCondition
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
            DataValidateGroup.FormUpdate.class})
    @QueryCondition
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
            DataValidateGroup.FormUpdate.class})
    @QueryCondition
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "删除标志")
    @TableField(fill = FieldFill.INSERT)
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
            DataValidateGroup.FormUpdate.class})
    @QueryCondition
    private Boolean delFlag;

    @ApiModelProperty(value = "版本号乐观锁")
    @TableField(fill = FieldFill.INSERT)
    @Null(message = "非法参数", groups = {DataValidateGroup.FormSave.class,
            DataValidateGroup.FormUpdate.class})
    @QueryCondition
    private Integer recordVersion;


    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    @TableField(exist = false)
    private Map<String, Object> params;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void autoFill(String userName){
        userName= StringUtils.trimToNull(userName);
        setRecordVersion(0);
        setDelFlag(false);
        setCreateTime(LocalDateTime.now());
        setUpdateTime(LocalDateTime.now());
        setCreateBy(Optional.ofNullable(userName).orElse("sys"));
        setUpdateBy(Optional.ofNullable(userName).orElse("sys"));
    }
}
