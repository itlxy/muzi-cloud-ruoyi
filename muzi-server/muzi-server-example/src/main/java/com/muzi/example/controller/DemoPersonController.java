package com.muzi.example.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.muzi.common.core.web.domain.XlsxVO;
import com.muzi.common.core.utils.StringUtils;
import com.muzi.common.security.annotation.RepeatSubmit;
import com.muzi.common.core.utils.bean.ValidateUtil;
import com.muzi.common.core.exception.ServiceException;
import com.muzi.common.security.annotation.RequiresPermissions;
import com.muzi.common.security.utils.SecurityUtils;
import com.muzi.common.core.constant.DataValidateGroup;
import com.muzi.example.enums.DelFlag;
import io.swagger.annotations.Api;
import org.springframework.util.ObjectUtils;
import com.muzi.example.page.excel.DemoPersonExcel;
import com.muzi.example.page.form.DemoPersonForm;
import com.muzi.example.page.query.DemoPersonQuery;
import com.muzi.example.page.vo.DemoPersonVO;
import javafx.util.Pair;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.muzi.common.log.annotation.Log;
import com.muzi.common.log.enums.BusinessType;
import com.muzi.example.service.DemoPersonService;
import com.muzi.common.core.web.controller.BaseController;
import com.muzi.common.core.web.domain.AjaxResult;
import com.muzi.common.core.utils.poi.ExcelUtil;
import com.muzi.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 案例-人员Controller
 *
 * @author muzi
 * @date 2022-07-31
 */
@RestController
@RequestMapping("/person")
@Api(tags = "案例-人员Controller")
public class DemoPersonController extends BaseController
{
    @Resource
    private DemoPersonService demoPersonService;

    /**
     * 查询案例-人员列表
     *
     * @param demoPersonQuery 查询条件实体
     * @return 表单数据
     */
    @ApiOperation("获取案例-人员数据列表")
    @RequiresPermissions("example:person:list")
    //@Encrypt
    @GetMapping("/list")
    public TableDataInfo list( DemoPersonQuery demoPersonQuery, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ValidateUtil.getValidationMsg(bindingResult));
        }
        startPage();
        List<DemoPersonVO> dataTable = demoPersonService.getDataTable(demoPersonQuery);
        return getDataTable(dataTable);
    }

    /**
     * 导出案例-人员列表
     *
     * @param demoPersonQuery 查询条件实体
     * @return
     */
    @ApiOperation("导出案例-人员数据列表")
    @RequiresPermissions("example:person:export")
    @Log(title = "案例-人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DemoPersonQuery demoPersonQuery)
    {
        List<DemoPersonVO> list = demoPersonService.findByQuery(demoPersonQuery);
        ExcelUtil<DemoPersonVO> util = new ExcelUtil<DemoPersonVO>(DemoPersonVO.class);
        util.exportExcel(response, list, "案例-人员数据");
    }

    /**
     * 获取案例-人员导入模板
     */
    @ApiOperation("获取案例-人员导入模板")
    @PostMapping("/importTemplate")
    @RequiresPermissions("example:person:import")
    @Log(title = "案例-人员", businessType = BusinessType.IMPORT)
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<DemoPersonExcel> util = new ExcelUtil<>(DemoPersonExcel.class);
        util.importTemplateExcel(response, "案例-人员数据");
    }

    /**
     * 案例-人员数据导入
     *
     * @param file 上传文件
     * @param updateSupport 覆盖更新
     * @return 返回结果
     */
    @ApiOperation("案例-人员数据导入")
    @Log(title = "案例-人员数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("example:person:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<DemoPersonExcel> util = new ExcelUtil<>(DemoPersonExcel.class);
        Pair<String,List<DemoPersonExcel>> pair=demoPersonService.importData(util.importExcel(file.getInputStream()), updateSupport, SecurityUtils.getUsername());
        if(StringUtils.isNotEmpty(pair.getKey())){
            List<DemoPersonExcel> errorDataList=pair.getValue();
            //此处可以对数据进行转化处理
            errorDataList.stream().forEach(ele->{
                if(null!=ele.getAdultFlag()){
                    if(ele.getAdultFlag()){
                        ele.setAdultFlagName("成年");
                    }else{
                        ele.setAdultFlagName("未成年");
                    }
                }
            });
            return AjaxResult.error(pair.getKey(),new XlsxVO<DemoPersonExcel>(util.getHeadAndFieldName(),errorDataList));
        }
        return AjaxResult.success("数据导入成功");
    }

    /**
     * 获取案例-人员详细信息
     *
     * @param id 主键
     * @return 返回结果
     */
    @ApiOperation("获取案例-人员详细信息")
    @RequiresPermissions("example:person:query")
    @GetMapping(value = "/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        if (ObjectUtils.isEmpty(id)) {
            return AjaxResult.error("参数异常");
        }
        return AjaxResult.success(demoPersonService.getInfoById(id));
    }

    /**
     * 新增案例-人员
     *
     * @param demoPersonForm 表单数据
     * @return 返回结果
     */
    @ApiOperation("新增案例-人员")
    @RequiresPermissions("example:person:add")
    @Log(title = "案例-人员", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("add")
    public AjaxResult add(@RequestBody @Validated(DataValidateGroup.FormSave.class) DemoPersonForm demoPersonForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return AjaxResult.error(ValidateUtil.getValidationMsg(bindingResult));
        }
        return toAjax(demoPersonService.add(demoPersonForm));
    }

    /**
     * 修改案例-人员
     *
     * @param demoPersonForm 表单数据
     * @return 返回结果
     */
    @ApiOperation("修改案例-人员")
    @RequiresPermissions("example:person:edit")
    @Log(title = "案例-人员", businessType = BusinessType.UPDATE)
    @PutMapping("edit")
    public AjaxResult edit(@RequestBody @Validated({DataValidateGroup.FormUpdate.class})  DemoPersonForm demoPersonForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return AjaxResult.error(ValidateUtil.getValidationMsg(bindingResult));
        }
        return toAjax(demoPersonService.edit(demoPersonForm));
    }

    /**
     * 失效案例-人员
     *
     * @param ids 主键列表
     * @return 返回结果
     */
    @ApiOperation("失效案例-人员")
    @RequiresPermissions("example:person:lapse")
    @Log(title = "案例-人员", businessType = BusinessType.LAPSE)
    @PutMapping("/lapse/{ids}")
    public AjaxResult lapse(@PathVariable Long[] ids)
    {
        if(StringUtils.isEmpty(ids)){
            return AjaxResult.error("参数异常");
        }
        return toAjax(demoPersonService.brandChangeStatus(Arrays.asList(ids), DelFlag.Lapse));
    }

    /**
     * 生效案例-人员
     *
     * @param ids 主键列表
     * @return 返回结果
     */
    @ApiOperation("生效案例-人员")
    @RequiresPermissions("example:person:effect")
    @Log(title = "案例-人员", businessType = BusinessType.EFFECT)
    @PutMapping("/effect/{ids}")
    public AjaxResult effect(@PathVariable Long[] ids)
    {
        if(StringUtils.isEmpty(ids)){
            return AjaxResult.error("参数异常");
        }
        return toAjax(demoPersonService.brandChangeStatus(Arrays.asList(ids), DelFlag.Effect));
    }

    /**
     * 物理删除案例-人员
     *
     * @param ids 主键列表
     * @return 返回结果
     */
    @ApiOperation("物理删除案例-人员")
    @RequiresPermissions("example:person:remove")
    @Log(title = "案例-人员", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if(StringUtils.isEmpty(ids)){
            return AjaxResult.error("参数异常");
        }
        return toAjax(demoPersonService.removeBatchByIds(Arrays.asList(ids)));
    }
}