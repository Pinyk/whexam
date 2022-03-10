package com.exam.demo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "MaterialAnswer")
public class MaterialAnswer {

    private int id;

    private ProblemsParam question;


}
