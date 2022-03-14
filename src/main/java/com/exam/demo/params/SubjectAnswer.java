package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "SubjectAnswer", description = "")
public class SubjectAnswer {

    private Integer id;

    private String userAnswer;

}
