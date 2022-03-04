package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.ExamSubject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSubjectMapper extends BaseMapper<ExamSubject> {

    @Select("select id,context,subject_id,score from exam_subject where material_question = 1 and id = #{id}")
    ExamSubject findByIdAndMaterialQuestion(Integer id);

}
