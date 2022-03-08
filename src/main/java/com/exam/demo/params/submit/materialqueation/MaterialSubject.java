package com.exam.demo.params.submit.materialqueation;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(value = "MaterialSubject", description = "新增材料题下的问答题")
public class MaterialSubject {

    private String context;

    private String answer;

    private Double score;

    private MultipartFile img;
}
