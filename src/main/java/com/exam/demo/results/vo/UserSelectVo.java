package com.exam.demo.results.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询员工接口返回实体类")
public class UserSelectVo {
    @ApiModelProperty(value="姓名")
    private  String name;
    @ApiModelProperty(value="性别")
    private  String gender;
    @ApiModelProperty(value="角色")
    private  String role;
    @ApiModelProperty(value="身份证")
    private  String identity;
    @ApiModelProperty(value="电话")
    private  String tele;
    @ApiModelProperty(value="电子邮箱")
    private  String email;
    @ApiModelProperty(value="部门")
    private  String department;
    @ApiModelProperty(value="地址")
    private  String address;

}
