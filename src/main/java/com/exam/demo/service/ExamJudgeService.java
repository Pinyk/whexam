package com.exam.demo.service;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.QueryQuestion;

import java.util.List;

public interface ExamJudgeService {

    /**
     * 查询所有判断题目
     * @return
     */
    List<ExamJudge> findAll();

    /**
     * 分页查询判断题目
     * @param current
     * @param pageSize
     * @return
     */
    List<ExamJudge> findPage(int current, int pageSize);

    /**
     * 根据题目ID查询判断题目
     * @param id
     * @return
     */
    ExamJudge findById(Integer id);

    /**
     * 根据条件查询判断题目
     * @param current
     * @param pageSize
     * @param queryQuestion
     * @return
     */
    List<ExamJudge> search(Integer current, Integer pageSize, QueryQuestion queryQuestion);

    /**
     * 向题库添加判断题目
     * @param examJudge
     * @return
     */
    Integer saveExamJudge(ExamJudge examJudge);

    /**
     * 修改题库的判断题目
     * @param examJudge
     * @return
     */
    Integer updateExamJudge(ExamJudge examJudge);

    /**
     * 删除题库中的判断题目
     * @param id
     * @return
     */
    Integer deleteExamJudge(Integer id);
}
