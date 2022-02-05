package com.exam.demo.service;

import com.exam.demo.entity.ExamSelect;

import java.util.List;

public interface ExamSelectService {

    /**
     * 查询所有选择题目
     * @return
     */
    List<ExamSelect> findAll();

    /**
     * 根据题目ID查询选择题目
     * @param id
     * @return
     */
    ExamSelect findById(Integer id);

    /**
     * 根据条件查询选择题目
     * @param selectSearch
     * @return
     */
    List<ExamSelect> search(ExamSelect selectSearch);

    /**
     * 向题库添加选择题目
     * @param examSelect
     * @return
     */
    Integer saveExamSelect(ExamSelect examSelect);

    /**
     * 修改题库的选择题目
     * @param examSelect
     * @return
     */
    Integer updateExamSelect(ExamSelect examSelect);

    /**
     * 删除题库中的选择题目
     * @param id
     * @return
     */
    Integer deleteExamSelect(Integer id);
}
