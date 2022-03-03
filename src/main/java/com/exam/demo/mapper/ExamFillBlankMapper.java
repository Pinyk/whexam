package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.entity.ExamJudge;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamFillBlankMapper extends BaseMapper<ExamFillBlank> {

    @Select("select id,context,subject_id,score from exam_fb where material_question = 1 and id = #{id}")
    ExamFillBlank findByIdAndMaterialQuestion(Integer id);
}
