package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "examJudge")
public class ExamJudge extends ExamObject{

    @ApiModelProperty(value = "判断题主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目内容")
    private String context;

    @ApiModelProperty(value = "答案")
    private int answer;

    @ApiModelProperty(value = "所属科目ID")
    private Integer subjectId;

    @ApiModelProperty(value = "难度")
    private Integer difficulty;

    @ApiModelProperty(value = "分数")
    private Double score;

    @ApiModelProperty(value = "图片URL")
    private String imgUrl;

    @ApiModelProperty(value = "是否为材料题中的题, 0:不是 1:是")
    private Integer materialQuestion;

}
