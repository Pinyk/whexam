package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "score")
public class Score {

    @ApiModelProperty(value = "成绩主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "试卷ID")
    private Integer testpaperId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "得分")
    private Double scorenum;

    @ApiModelProperty(value = "试卷状态")
    private String status;
}
