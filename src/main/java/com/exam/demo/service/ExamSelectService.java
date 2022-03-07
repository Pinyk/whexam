package com.exam.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.params.SelectSubmitParam;
import com.exam.demo.results.vo.ExamSelectVo;
import com.exam.demo.results.vo.PageVo;

import java.util.List;

public interface ExamSelectService {

    /**
     * 查询所有选择题目
     * @return
     */
    List<SelectQuestionVo> findAll();

    /**
     * 查询所有单选/多选题目
     * @return
     */
    List<SelectQuestionVo> findSingleOrMultipleSelection(int type);

    /**
     * 分页查询单选/多选题目
     * @param current
     * @param pageSize
     * @return
     */
    List<SelectQuestionVo> findPage(int current, int pageSize, int type);

    /**
     * 根据题目ID查询选择题目
     * @param id
     * @return
     */
    SelectQuestionVo findById(Integer id);

    ExamSelectVo selectById(Integer id);

    /**
     * 根据科目ID查询所有选择题目
     * @param subjectId
     * @return
     */
    List<ExamSelect> findBySubjectId(Integer subjectId, Integer type);

    /**
     * 根据条件查询选择题目
     * @return
     */
    PageVo<ExamSelectVo> search(Integer id, String name, String subject, Integer currentPage, Integer pageSize, Integer type, Integer materialQuestion);


    ExamSelect findByIdAndMaterialQuestion(Integer id, Integer materialQuestion);

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

    /**
     * 新增单选题
     * @param selectSubmitParam
     * @return
     */
    Integer saveSingleSelection(SelectSubmitParam selectSubmitParam);

    /**
     * 新增多选题
     * @param selectSubmitParam
     * @return
     */
    Integer saveMultipleSelection(SelectSubmitParam selectSubmitParam);
}
