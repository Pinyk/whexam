package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper extends BaseMapper<Exam> {

    /**
     * 根据试卷ID查询组成试卷的判断题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamJudgeByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的选择题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamSelectByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的主观题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamSubjectByTestPaperId(Integer testPaperId);

}
