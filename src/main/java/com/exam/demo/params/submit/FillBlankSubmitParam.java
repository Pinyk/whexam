package com.exam.demo.params.submit;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(value = "FillBlankSubmitParam", description = "填空题新增实体类")
public class FillBlankSubmitParam {

    private String context;

    private Integer subjectId;

    private String answer;

    private Double score;

}
