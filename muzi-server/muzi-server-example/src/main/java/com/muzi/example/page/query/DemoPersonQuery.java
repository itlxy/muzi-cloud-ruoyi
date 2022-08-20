package com.muzi.example.page.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muzi.common.core.annotation.QueryCondition;
import com.muzi.common.core.constant.DataValidateGroup;
import com.muzi.common.core.enums.MatchType;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案例-人员查询对象
 *
 * @author muzi
 * @date 2022-07-29
 */
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案例-人员对象 demo_person" , description = "案例-人员查询对象 demo_person")
public class DemoPersonQuery extends DemoPersonEntity implements Serializable {

    /**
     * 主键 列表
     */
    @ApiModelProperty(value = "主键 列表")
    @QueryCondition(column = "id" , func = MatchType.IN)
    private List<Long> idList;

    /**
     * 开始时间 范围
     */
    @ApiModelProperty(value = "开始时间 范围")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @QueryCondition(column = "startTime" , func = MatchType.BETWEEN)
    private List<LocalDateTime> startTimeRange;

    /**
     * 页码
     */
    @Min(value = 1, message = "页码最小为 1" , groups = {DataValidateGroup.PageQuery.class})
    @Max(value = 1000, message = "页码最大为 10000" , groups = {DataValidateGroup.PageQuery.class})
    private Integer pageNum;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页条数 最小为 1" , groups = {DataValidateGroup.PageQuery.class})
    @Max(value = 1000, message = "每页条数 最大为 10000" , groups = {DataValidateGroup.PageQuery.class})
    private Integer pageSize;
}