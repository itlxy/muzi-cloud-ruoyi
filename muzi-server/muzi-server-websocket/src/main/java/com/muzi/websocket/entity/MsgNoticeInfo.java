package com.muzi.websocket.entity;

import cn.craccd.mongoHelper.bean.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: MsgNoticeInfo
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/7 11:51
 * @Version: 1.0
 */
@Document
@Data
public class MsgNoticeInfo extends BaseModel {

    /** 用户 */
    private String userName;

    /** 消息标题 */
    private String msgTitle;

    /** 消息类型（1审核 2异常） */
    private String msgType;

    /** 消息内容 */
    private String msgContent;

    /** 消息状态（0未读 1已读） */
    private String status;

    /** 消息备注 */
    private String remark;
}
