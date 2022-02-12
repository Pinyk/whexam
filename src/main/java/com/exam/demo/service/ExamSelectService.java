package com.exam.demo.service;

import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.rtEntity.SelectQuestion;

import java.util.List;

public interface ExamSelectService {

    /**
     * 查询所有选择题目
     * @return
     */
    List<SelectQuestion> findAll();

    /**
     * 分页查询选择题目
     * @param current
     * @param pageSize
     * @return
     */
    List<SelectQuestion> findPage(int current, int pageSize);

    /**
     * 根据题目ID查询选择题目
     * @param id
     * @return
     */
    SelectQuestion findById(Integer id);

    /**
     * 根据条件查询选择题目
     * @param current
     * @param pageSize
     * @param queryQuestion
     * @return
     */
    List<SelectQuestion> search(Integer current, Integer pageSize, QueryQuestion queryQuestion);

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
