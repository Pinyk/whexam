package com.exam.demo.params;


import com.exam.demo.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel(description = "答案提交——接收前端参数的实体类")
public class AnswerParam {

    @ApiModelProperty(value = "接收单选题集合")
    List<ExamSelect> singleSelections;

    @ApiModelProperty(value = "接收多选题集合")
    List<ExamSelect> multipleSelections;

    @ApiModelProperty(value = "接收填空题集合")
    List<ExamFillBlank> examFillBlanks;

    @ApiModelProperty(value = "接收判断题集合")
    List<ExamJudge> examJudges;

    @ApiModelProperty(value = "接收主观题集合")
    List<ExamSubject> examSubjects;

    @ApiModelProperty(value = "接收材料题集合")
    List examMaterials;

}
