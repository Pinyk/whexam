package com.exam.demo.params;

import com.exam.demo.otherEntity.UserOtherAnswer;
import com.exam.demo.otherEntity.UserSelectionAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 导出数据的材料题类
 */
@Data
@ApiModel(description = "接收前端传入材料题中的四种子题")
public class UserProblemParam {

    @ApiModelProperty(value = "材料题中的单选题")
    private List<UserSelectionAnswer> singleSelections;

    @ApiModelProperty(value = "材料中的多选题")
    private List<UserSelectionAnswer> multipleSelections;

    @ApiModelProperty(value = "材料题中的填空题")
    private List<UserOtherAnswer> examFillBlank;

    @ApiModelProperty(value = "材料题中的判断题")
    private List<UserOtherAnswer> examJudge;

    @ApiModelProperty(value = "材料题中的问答题")
    private List<UserOtherAnswer> examSubject;

}

