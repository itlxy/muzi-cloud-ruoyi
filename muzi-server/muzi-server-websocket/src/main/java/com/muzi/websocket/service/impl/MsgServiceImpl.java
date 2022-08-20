package com.muzi.websocket.service.impl;

import cn.craccd.mongoHelper.bean.Page;
import cn.craccd.mongoHelper.bean.SortBuilder;
import cn.craccd.mongoHelper.utils.CriteriaAndWrapper;
import cn.craccd.mongoHelper.utils.CriteriaOrWrapper;
import cn.craccd.mongoHelper.utils.MongoHelper;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.muzi.websocket.dto.ServerMessage;
import com.muzi.websocket.entity.MsgNoticeInfo;
import com.muzi.websocket.query.MsgNoticeQuery;
import com.muzi.websocket.service.MsgService;
import com.muzi.websocket.util.WsChannelGroup;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MsgServiceImpl
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/7 14:36
 * @Version: 1.0
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    private MongoHelper mongoHelper;

    @Override
    public void sendMsg(String userName, ServerMessage serverMessage) {
        Set<Channel> channelSet=WsChannelGroup.userChannelMap.get(userName);
        if(CollectionUtils.isNotEmpty(channelSet)){
            for(Channel channel:channelSet){
                channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(serverMessage)));
            }
        }
    }

    @Override
    public void saveMsg(MsgNoticeInfo msgNoticeInfo) {
        mongoHelper.insert(msgNoticeInfo);
    }

    @Override
    public void batchSave(List<MsgNoticeInfo> msgNoticeList) {
        mongoHelper.insertAll(msgNoticeList);
    }

    @Override
    public Page pagelist(MsgNoticeQuery msgNoticeQuery) {
        Page page=new Page<>();
        page.setCurr(msgNoticeQuery.getPageNum());
        page.setLimit(msgNoticeQuery.getPageSize());

        CriteriaAndWrapper criteriaAndWrapper = new CriteriaAndWrapper();
        if (StrUtil.isNotEmpty(msgNoticeQuery.getUserName())) {
            criteriaAndWrapper.eq(MsgNoticeInfo::getUserName,msgNoticeQuery.getUserName());
        }
        if (StrUtil.isNotEmpty(msgNoticeQuery.getMsgType())) {
            criteriaAndWrapper.eq(MsgNoticeInfo::getMsgType, msgNoticeQuery.getMsgType());
        }
        if (StrUtil.isNotEmpty(msgNoticeQuery.getStatus())) {
            criteriaAndWrapper.eq(MsgNoticeInfo::getStatus, msgNoticeQuery.getStatus());
        }
        page = mongoHelper.findPage(criteriaAndWrapper, new SortBuilder(MsgNoticeInfo::getCreateTime, Sort.Direction.DESC), page, MsgNoticeInfo.class);
        return page;
    }
}
