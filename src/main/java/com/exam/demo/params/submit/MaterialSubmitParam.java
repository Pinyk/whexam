package com.exam.demo.params.submit;

import com.exam.demo.params.submit.materialqueation.MaterialFillBlank;
import com.exam.demo.params.submit.materialqueation.MaterialJudge;
import com.exam.demo.params.submit.materialqueation.MaterialSelection;
import com.exam.demo.params.submit.materialqueation.MaterialSubject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

@Data
@ApiModel(value = "MaterialSubmitParam", description = "新增材料题实体类")
public class MaterialSubmitParam {

    private String context;

    private Integer subjectId;

    private List<MaterialSelection> singleSelections;

    private List<MaterialSelection> multipleSelections;

    private List<MaterialFillBlank> examFillBlanks;

    private List<MaterialJudge> examJudges;

    private List<MaterialSubject> examSubjects;
}
