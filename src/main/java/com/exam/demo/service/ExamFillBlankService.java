/**
 * Created by 小机灵鬼儿
 * 2022/2/28 13:48
 */

package com.exam.demo.service;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.entity.QueryQuestion;

import java.util.List;

public interface ExamFillBlankService {
    /**
     * 查询所有填空题
     */
    List<ExamFillBlank> findAll();

    /**
     * 分页查询所有填空题
     */
    List<ExamFillBlank> findPage(int current, int pageSize);

    /**
     * 根据题目ID查询填空题
     */
    ExamFillBlank findById(Integer id);

    /**
     * 根据科目ID查询填空题
     */
    List<ExamFillBlank> findByBlankId(Integer subjectId);

    /**
     * 根据条件查询填空题
     */
    List<ExamFillBlank> search(Integer current, Integer pageSize, QueryQuestion queryQuestion);

    /**
     * 向题库添加填空题
     */
    Integer saveExamFillBlank(ExamFillBlank examFillBlank);

    /**
     * 修改题库的填空题
     */
    Integer updateExamFillBlank(ExamFillBlank examFillBlank);

    /**
     * 删除题库中的填空题
     */
    Integer deleteExamFillBlank(Integer id);
}
