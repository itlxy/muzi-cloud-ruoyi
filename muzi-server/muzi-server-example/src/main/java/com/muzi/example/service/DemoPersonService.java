package com.muzi.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.enums.DelFlag;
import com.muzi.example.page.excel.DemoPersonExcel;
import com.muzi.example.page.form.DemoPersonForm;
import com.muzi.example.page.query.DemoPersonQuery;
import com.muzi.example.page.vo.DemoPersonVO;
import javafx.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


/**
 * 案例-人员Service接口
 *
 * @author muzi
 * @date 2022-07-31
 */
public interface DemoPersonService extends IService<DemoPersonEntity>
{

    /**
     * 根据查询条件返回查询的分页数据表
     *
     * @param demoPersonQuery 查询条件
     * @return 查询结果
     **/
    List<DemoPersonVO> getDataTable(DemoPersonQuery demoPersonQuery);

    /**
     * 根据查询条件查询数据
     *
     * @param demoPersonQuery 查询条件
     * @return 查询结果
     **/
    List<DemoPersonVO> findByQuery(DemoPersonQuery demoPersonQuery);

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     **/
    DemoPersonVO getInfoById(Long id);

    /**
     * 添加
     *
     * @param demoPersonForm 表单数据
     * @return 添加成功与否
     **/
    boolean add(DemoPersonForm demoPersonForm);

    /**
     * 修改
     *
     * @param demoPersonForm 表单数据
     * @return 修改成功与否
     **/
    boolean edit(DemoPersonForm demoPersonForm);

    /**
     * 导入
     *
     * @param importExcel excel导入数据
     * @param updateSupport 是否覆盖更新
     * @param username 用户名
     * @return 导入成功与否
     **/
    Pair<String,List<DemoPersonExcel>> importData(List<DemoPersonExcel> importExcel, boolean updateSupport, String username);

    /**
     * 删除标志更新
     *
     * @param idList 主键列表
     * @param delFlag 删除标志
     * @return 更新成功与否
     **/
    boolean brandChangeStatus(List<Long> idList, DelFlag delFlag);

    /**
     * 根据查询构造条件批量更新
     *
     * @param entityList excel导入数据
     * @param queryWrapperFunction 查询构造条件
     **/
    boolean updateBatchByQueryWrapper(Collection<DemoPersonEntity> entityList, Function<DemoPersonEntity, QueryWrapper> queryWrapperFunction);


}