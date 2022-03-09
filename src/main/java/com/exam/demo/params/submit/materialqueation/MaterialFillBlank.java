package com.exam.demo.params.submit.materialqueation;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "材料题下的填空题")
public class MaterialFillBlank {

    private String context;

    private String answer;

    private Double score;

    private MultipartFile img;
}
