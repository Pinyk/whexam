package com.exam.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.ExamJudge;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamJudgeMapper extends BaseMapper<ExamJudge> {

    /**
     * 材料题模块下专属查询方法
     * 根据Id和MaterialQuestion查询
     * @param id
     * @return
     */
    @Select("select id,context,subject_id,score from exam_judge where material_question = 1 and id = #{id}")
    ExamJudge findByIdAndMaterialQuestion(Integer id);

}
