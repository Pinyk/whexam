package com.exam.demo.otherEntity;

import com.exam.demo.params.ProblemsParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
@Data
@ApiModel(value = "userMaterialAnswer")
public class UserMaterialAnswer {

    private int id;

    private String context;


}
