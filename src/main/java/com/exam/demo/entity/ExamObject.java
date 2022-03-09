package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "所有题目类的基类")
public abstract class ExamObject {

    private Integer id;

    private String context;

    private Integer difficulty;

    private Integer subjectId;

}
