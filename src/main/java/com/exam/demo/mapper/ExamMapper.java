package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper extends BaseMapper<Exam> {

    List<Object> findExamJudgeByTestPaperId(Integer testPaperId);

    List<Object> findExamSelectByTestPaperId(Integer testPaperId);

    List<Object> findExamSubjectByTestPaperId(Integer testPaperId);

}
