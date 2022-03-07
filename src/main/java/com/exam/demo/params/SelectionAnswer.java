package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "SelectionAnswer", description = "答案提交——选择题答案接收对象")
public class SelectionAnswer {

    private int id;

    private String userAnswer;

}
