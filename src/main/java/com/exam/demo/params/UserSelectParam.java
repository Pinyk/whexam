package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserSelectParam",description = "前端接收角色查询参数信息")
public class UserSelectParam {
    @ApiModelProperty(name="name",value="角色姓名")
    private  String name;
    @ApiModelProperty(name="nums",value="工作证号")
    private  String nums;
    @ApiModelProperty(name="department",value="员工部门")
    private  String department;
    @ApiModelProperty(name="address",value="员工地点")
    private  String address;
    @ApiModelProperty(name="currentPage",value="当前页码")
    private Integer currentPage;
    @ApiModelProperty(name="pageSize",value="页码大小")
    private Integer pageSize;
}
