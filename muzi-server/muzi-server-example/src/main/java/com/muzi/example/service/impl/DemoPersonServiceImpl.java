package com.muzi.example.service.impl;

import java.util.*;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.common.core.exception.ServiceException;
import com.muzi.common.core.utils.CommonUtils;
import com.muzi.common.core.utils.bean.ValidateUtil;
import com.muzi.common.core.utils.StringUtils;
import com.muzi.common.core.utils.QueryWrapperUtils;
import com.muzi.common.core.constant.DataValidateGroup;
import com.muzi.example.convert.po2pojo.DemoPersonPOJOConvert;
import com.muzi.example.transaction.DemoPersonTransactionService;
import com.muzi.example.entity.mysql.DemoPersonEntity;
import com.muzi.example.enums.DelFlag;
import javafx.util.Pair;
import com.muzi.example.page.excel.DemoPersonExcel;
import com.muzi.example.page.form.DemoPersonForm;
import com.muzi.example.page.query.DemoPersonQuery;
import com.muzi.example.service.DemoPersonService;
import com.muzi.example.page.vo.DemoPersonVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;
import com.muzi.example.mapper.mysql.DemoPersonMapper;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;


/**
 * 案例-人员Service业务层处理
 *
 * @author muzi
 * @date 2022-07-31
 */
@Service
public class DemoPersonServiceImpl extends ServiceImpl<DemoPersonMapper, DemoPersonEntity> implements DemoPersonService
{
    @Resource
    private DemoPersonMapper demoPersonMapper;
    @Resource
    private DemoPersonTransactionService demoPersonTransactionService;
    @Resource
    private Validator validator;
    @Resource
    private DemoPersonPOJOConvert demoPersonPOJOConvert;


    @Override
    public List<DemoPersonVO> getDataTable(DemoPersonQuery demoPersonQuery){
        List<DemoPersonVO> demoPersonVOList=demoPersonMapper.getDataTable(demoPersonQuery);
        return demoPersonVOList;
    }

    @Override
    public List<DemoPersonVO> findByQuery(DemoPersonQuery demoPersonQuery) {
        QueryWrapper queryWrapper=QueryWrapperUtils.getQueryWrapper(DemoPersonVO.class,demoPersonQuery);
        List<Map<String,Object>> demoPersonVOMaps=demoPersonMapper.selectMaps(queryWrapper);
        try {
            //将map对象转为实体类
            List<DemoPersonVO> demoPersonVOList= CommonUtils.mapToObj(demoPersonVOMaps,DemoPersonVO.class);
            return demoPersonVOList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DemoPersonVO getInfoById(Long id){
        return demoPersonMapper.getInfoById(id);
    }

    @Override
    public boolean add(DemoPersonForm demoPersonForm) {
        DemoPersonEntity demoPersonEntity=demoPersonPOJOConvert.toDemoPersonEntity(demoPersonForm);
        return this.save(demoPersonEntity);
    }

    @Override
    public boolean edit(DemoPersonForm demoPersonForm) {
        DemoPersonEntity demoPersonEntity=demoPersonPOJOConvert.toDemoPersonEntity(demoPersonForm);
        return this.updateById(demoPersonEntity);
    }

    @Override
    public Pair<String,List<DemoPersonExcel>> importData(List<DemoPersonExcel> importExcel, boolean updateSupport, String username) {
        if (StringUtils.isNull(importExcel) || importExcel.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        StringBuilder errorSb = new StringBuilder();
        List<DemoPersonExcel> failList = new ArrayList<>();
        List<DemoPersonEntity> successList = new ArrayList<>();
        for (int i = 0; i < importExcel.size(); i++) {
            DemoPersonExcel excel = importExcel.get(i);
            Set<ConstraintViolation<DemoPersonExcel>> errors = validator.validate(excel, DataValidateGroup.ExcelSave.class);
            if (errors == null || errors.isEmpty()) {
                //如果自动校验通过，判断手动校验是否出错
                /*if(true) {
                    successList.add(excel);
                }else {
                    errorSb.append("第").append(i+1).append("条:")
                            .append("数据有问题,请仔细检查;");
                }*/
                successList.add(excel);
            } else {
                errorSb.append("第").append(i + 1).append("条:");
                String error= ValidateUtil.getValidationMsg(errors);
                errorSb.append(error);
                excel.setErrorRemark(error);
                failList.add(excel);
            }

        }
        if(successList.size()>0){
            if(updateSupport){
                demoPersonMapper.batchInsertUpdateUnique(successList);
            }else{
                demoPersonMapper.batchInsertIgnoreUnique(successList);
            }
        }
        return new Pair(errorSb.toString(),failList);
    }

    @Override
    public boolean brandChangeStatus(List<Long> idList, DelFlag delFlag) {
        //如果参数校验失败，则返回失败
        if(CollectionUtils.isEmpty(idList)||null==delFlag){
            throw new ServiceException("状态更改失败,数据列表或状态标志为空");
        }
        LambdaQueryWrapper<DemoPersonEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(DemoPersonEntity::getId,DemoPersonEntity::getRecordVersion,DemoPersonEntity::getDelFlag);
        queryWrapper.in(DemoPersonEntity::getId,idList);
        List<DemoPersonEntity> demoPersonList=demoPersonMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(demoPersonList)){
            throw new ServiceException("状态更改失败,查询数据条数为0");
        }
        //事务方法更新
        demoPersonTransactionService.batchUpdateDelFlagByIdAndVersion(delFlag.getCode(),demoPersonList);
        return true;
    }

    //返回的true或false仅代表该sql语句是否成功执行，并不代表结果已成功更新
    @Override
    public boolean updateBatchByQueryWrapper(Collection<DemoPersonEntity> entityList, Function<DemoPersonEntity, QueryWrapper> queryWrapperFunction) {
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        return this.executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> {
            MapperMethod.ParamMap param = new MapperMethod.ParamMap();
            param.put(Constants.ENTITY, entity);
            param.put(Constants.WRAPPER, queryWrapperFunction.apply(entity));
            sqlSession.update(sqlStatement, param);
        });
    }
}