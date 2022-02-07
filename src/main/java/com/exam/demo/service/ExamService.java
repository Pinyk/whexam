package com.exam.demo.service;

import com.exam.demo.entity.Exam;

import java.util.List;
import java.util.Map;

public interface ExamService {

    /**
     * 根据试卷ID查询试卷的所有试题
     * @param testPaperId
     * @return
     */
//    List<Exam> findByTestPaperId(Integer testPaperId);
    Map<String, List<Object>> findByTestPaperId(Integer testPaperId);

    /**
     * 添加试卷试题
     * @param exam
     * @return
     */
    Integer addProblem(Exam exam);

    /**
     * 删除试卷试题
     * @param id
     * @return
     */
    Integer deleteProblem(Integer id);
}
