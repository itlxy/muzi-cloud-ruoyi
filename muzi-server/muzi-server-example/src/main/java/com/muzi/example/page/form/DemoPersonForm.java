package com.muzi.example.page.form;


import com.muzi.example.entity.mysql.DemoPersonEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "案例-人员增加，修改修表单对象", description = "案例-人员增加，修改修表单对象")
public class DemoPersonForm extends DemoPersonEntity implements Serializable {

}