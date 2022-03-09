package com.exam.demo.service;

import com.exam.demo.entity.ExamJudge;
import com.exam.demo.params.submit.JudgeSubmitParam;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.PageVo;

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
     * 根据科目ID查询判断题目
     * @param subjectId
     * @return
     */
    List<ExamJudge> findBySubjectId(Integer subjectId);

    /**
     * 组合查询——分页——根据条件查询判断题目
     * @param current
     * @param pageSize
     * @return
     */
    PageVo<ExamJudgeVo> search(Integer current, Integer pageSize, Integer id, String context, String subject, Integer materialQuestion);

    /**
     * 向题库添加判断题目
     * @param judgeSubmitParam
     * @return
     */
    Integer saveExamJudge(JudgeSubmitParam judgeSubmitParam);

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
