package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gaoyk
 * @Date: 2022/3/1 18:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "userwx")
public class Userwx {
    @ApiModelProperty(value = "主键")
    private int id;

    @ApiModelProperty(value = "微信标识")
    private String openid;

    @ApiModelProperty(value = "工作证号")
    private String nums;

    @ApiModelProperty(value = "性别")
    private int gender;

    @ApiModelProperty(value = "用户头像")
    private String image;

    @ApiModelProperty(value = "用户昵称")
    private String wxname;

    public Userwx(String openid, int gender, String image, String wxname) {
        this.openid = openid;
        this.gender = gender;
        this.image = image;
        this.wxname = wxname;
    }
}
