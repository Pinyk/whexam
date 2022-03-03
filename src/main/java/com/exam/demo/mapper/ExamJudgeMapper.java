package com.exam.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.ExamJudge;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamJudgeMapper extends BaseMapper<ExamJudge> {

    /**
     * 材料题模块下专属查询方法
     * 根据Id和MaterialQuestion查询
     * @param id
     * @return
     */
    ExamJudge findByIdAndMaterialQuestion(Integer id);

}
