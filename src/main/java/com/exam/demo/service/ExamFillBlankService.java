package com.exam.demo.service;

import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.params.submit.FillBlankSubmitParam;
import com.exam.demo.results.vo.ExamFillBlankVo;
import com.exam.demo.results.vo.PageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    ExamFillBlankVo findById(Integer id);

    /**
     * 根据科目ID查询填空题
     */
    List<ExamFillBlank> findByBlankId(Integer subjectId);

    /**
     * 根据条件查询填空题
     */
    PageVo<ExamFillBlankVo> search(Integer current, Integer pageSize, Integer id, String context, String subject, Integer materialQuestion);

    /**
     * 向题库添加填空题
     */
    Map<String,Object> saveExamFillBlank(String context, Integer subjectId, String answer, Double score, MultipartFile image, boolean isMaterialProblem);

    /**
     * 修改题库的填空题
     */
    Integer updateExamFillBlank(ExamFillBlank examFillBlank);

    /**
     * 删除题库中的填空题
     */
    Integer deleteExamFillBlank(Integer id);
}