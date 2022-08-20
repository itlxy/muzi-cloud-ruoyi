package com.muzi.websocket.query;

import com.muzi.common.core.constant.DataValidateGroup;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @ClassName: MsgNoticeQuery
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/7 19:44
 * @Version: 1.0
 */
@Data
public class MsgNoticeQuery implements Serializable {
    /** 用户 */
    private String userName;

    /** 消息标题 */
    private String msgTitle;

    /** 消息类型（1审核 2异常） */
    private String msgType;

    /** 消息状态（0未读 1已读） */
    private String status;

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
