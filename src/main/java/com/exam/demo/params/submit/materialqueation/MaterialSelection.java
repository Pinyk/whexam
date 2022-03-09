package com.exam.demo.params.submit.materialqueation;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "材料题下的选择题")
public class MaterialSelection {

    private String context;

    private String selectionA;

    private String selectionB;

    private String selectionC;

    private String selectionD;

    private String answer;

    private Double score;

    private MultipartFile img;

}
