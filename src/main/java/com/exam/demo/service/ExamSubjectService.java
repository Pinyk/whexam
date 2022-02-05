package com.exam.demo.service;

import com.exam.demo.entity.ExamSubject;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExamSubjectService {

    /**
     * 查询所有主观题目
     * @return
     */
    List<ExamSubject> findAll();

    /**
     * 根据题目ID查询主观题目
     * @param id
     * @return
     */
    ExamSubject findById(Integer id);

    /**
     * 根据条件查询主观题目
     * @param examSubject
     * @return
     */
    List<ExamSubject> search(ExamSubject examSubject);

    /**
     * 向题库添加主观题目
     * @param examSubject
     * @return
     */
    Integer saveExamSubject(ExamSubject examSubject);

    /**
     * 修改题库的主观题目
     * @param examSubject
     * @return
     */
    Integer updateExamSubject(ExamSubject examSubject);

    /**
     * 删除题库中的主观题目
     * @param id
     * @return
     */
    Integer deleteExamSubject(Integer id);
}
