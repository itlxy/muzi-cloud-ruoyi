package com.muzi.example.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muzi.common.datascope.annotation.ListReturnCheck;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.page.query.DemoPersonQuery;
import com.muzi.example.page.vo.DemoPersonVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案例-人员Mapper接口
 *
 * @author muzi
 * @date 2022-07-29
 */
public interface DemoPersonMapper extends BaseMapper<DemoPersonEntity> {
    /**
     * 查询案例-人员
     *
     * @param demoPersonQuery 查询参数
     * @return 结果
     */
    List<DemoPersonVO> getDataTable(DemoPersonQuery demoPersonQuery);

    /**
     * 获取案例-人员详情
     *
     * @param id 主键
     * @return 详情
     */
    DemoPersonVO getInfoById(@Param("id") Long id);

    /**
     * 批量插入案例-人员列表
     *
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    int batchInsert(@Param("list") List<DemoPersonEntity> demoPersonList);

    /**
     * 批量插入案例-人员列表，忽略唯一索引
     *
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    int batchInsertIgnoreUnique(@Param("list") List<DemoPersonEntity> demoPersonList);

    /**
     * 批量插入案例-人员列表，遇到唯一索引更新
     *
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    int batchInsertUpdateUnique(@Param("list") List<DemoPersonEntity> demoPersonList);

    /**
     * 根据id和版本号更新案例-人员
     *
     * @param expectCount 预计更新条数
     * @param demoPerson  案例-人员
     * @return 行数
     */
    @ListReturnCheck(info = "根据id和版本号更新案例-人员失败,结果与预期不符")
    int updateByIdAndVersion(@Param("expectCount") int expectCount, @Param("entity") DemoPersonEntity demoPerson);

    /**
     * 根据id和版本号多条更新案例-人员
     *
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    Boolean moreUpdateByIdAndVersion(@Param("list") List<DemoPersonEntity> demoPersonList);

    /**
     * 根据id和版本号批量更新案例-人员
     *
     * @param expectCount    预计更新条数
     * @param demoPerson     更新的实际值
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    @ListReturnCheck(info = "根据id和版本号更新案例-人员失败,结果与预期不符")
    int batchUpdateByIdAndVersion(@Param("expectCount") int expectCount, @Param("entity") DemoPersonEntity demoPerson, @Param("list") List<DemoPersonEntity> demoPersonList);

    /**
     * 根据id和版本号批量更新案例-人员
     *
     * @param expectCount    预计更新条数
     * @param delFlag        删除标识
     * @param demoPersonList 案例-人员列表
     * @return 行数
     */
    @ListReturnCheck(info = "根据id和版本号更新案例-人员失败,结果与预期不符")
    int batchUpdateDelFlagByIdAndVersion(@Param("expectCount") int expectCount, @Param("delFlag") boolean delFlag, @Param("list") List<DemoPersonEntity> demoPersonList);
}