package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "JudgeAnswer", description = "判断题答案接收对象")
public class JudgeAnswer {

    private int id;

    private String userAnswer;
}
