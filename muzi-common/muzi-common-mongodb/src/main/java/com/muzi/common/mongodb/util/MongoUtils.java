package com.muzi.common.mongodb.util;

import cn.craccd.mongoHelper.bean.Page;
import com.github.pagehelper.PageInfo;
import com.muzi.common.core.constant.HttpStatus;
import com.muzi.common.core.web.page.TableDataInfo;

/**
 * @ClassName: MongoUtils
 * @Description: TODO
 * @Author: 11298
 * @DateTime: 2022/8/7 19:55
 * @Version: 1.0
 */
public class MongoUtils {
    public static TableDataInfo toTableDataInfo(Page page){
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(page.getList());
        rspData.setMsg("查询成功");
        rspData.setTotal(page.getCount());
        return rspData;
    }
}
