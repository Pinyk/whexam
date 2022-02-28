package com.exam.demo.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: LBX
 */
@Data
@ApiModel(value = "组合查询试卷详情", description = "组合查询试卷返回对象" )
public class TestpaperVo {

    @ApiModelProperty(value = "试卷id",
            dataType = "Integer",
            name = "id",
            example = "1")
    private Integer id;

    @ApiModelProperty(value = "试卷所属学科",
            dataType = "String",
            name = "subject",
            example = "安全生产法律法规")
    private String subject;

    @ApiModelProperty(value = "试卷名称",
            dataType = "String",
            name = "name",
            example = "物资管理卷1")
    private String name;

    @ApiModelProperty(value = "满分",
            dataType = "Double",
            name = "score",
            example = "100")
    private Double totalscore;

    @ApiModelProperty(value = "及格分",
            dataType = "Double",
            name = "passScore",
            example = "60")
    private Double passscore;

    @ApiModelProperty(value = "考试开始时间",
            dataType = "Date",
            name = "startTime",
            example = "2022-03-01 00:00:00")
    private Date startTime;

    @ApiModelProperty(value = "考试结束时间",
            dataType = "Date",
            name = "deadTime",
            example = "2022-03-04 10:00:00")
    private Date deadTime;

    @ApiModelProperty(value = "考试总时长",
            dataType = "Integer",
            name = "time",
            example = "120")
    private Integer time;

    @ApiModelProperty(value = "试卷发布者ID",
            dataType = "Integer",
            name = "userId",
            example = "1")
    private Integer userId;

    @ApiModelProperty(value = "试卷所属部门",
            dataType = "String",
            name = "department",
            example = "1")
    private String department;

    @ApiModelProperty(value = "试题是否打乱",
            dataType = "Integer",
            name = "shuffle",
            example = "0")
    private Integer shuffle;

}