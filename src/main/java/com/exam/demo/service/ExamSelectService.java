package com.exam.demo.service;

import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.otherEntity.SelectQuestion;

import java.util.List;

public interface ExamSelectService {

    /**
     * 查询所有选择题目
     * @return
     */
    List<SelectQuestion> findAll();

    /**
     * 查询所有单选/多选题目
     * @return
     */
    List<SelectQuestion> findSingleOrMultipleSelection(int type);

    /**
     * 分页查询单选/多选题目
     * @param current
     * @param pageSize
     * @return
     */
    List<SelectQuestion> findPage(int current, int pageSize, int type);

    /**
     * 根据题目ID查询选择题目
     * @param id
     * @return
     */
    SelectQuestion findById(Integer id);

    /**
     * 根据科目ID查询所有选择题目
     * @param subjectId
     * @return
     */
    List<ExamSelect> findBySubjectId(Integer subjectId, Integer type);

    /**
     * 根据条件查询选择题目
     * @param current
     * @param pageSize
     * @param queryQuestion
     * @return
     */
    List<SelectQuestion> search(Integer current, Integer pageSize, QueryQuestion queryQuestion, Integer type);

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
