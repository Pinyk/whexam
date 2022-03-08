package com.exam.demo.params.submit.materialqueation;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "材料题下的判断题")
public class MaterialJudge {

    private String context;

    private Integer answer;

    private Double score;

    private MultipartFile img;

}
