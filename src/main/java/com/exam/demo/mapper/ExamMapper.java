package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper extends BaseMapper<Exam> {

    /**
     * 根据试卷ID查询组成试卷的判断题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamJudgeByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的选择题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamSelectByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的单选题目
     * @param testPaperId
     * @return
     */
    List<Object> findSingleSelectionByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的多选题目
     * @param testPaperId
     * @return
     */
    List<Object> findMultipleSelectionByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的主观题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamSubjectByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID查询组成试卷的填空题目
     * @param testPaperId
     * @return
     */
    List<Object> findExamFillBlankByTestPaperId(Integer testPaperId);


    /**
     * 根据试卷id查询组成试卷的材料题目
     * @param testPaperId
     * @return
     */
    List<ExamMaterial> findExamMaterialByTestPaperId(Integer testPaperId);

    /**
     * 根据材料题id查询组成材料题的单选题目
     */
    List<Object> findSingleSelectionByExamMaterialId(Integer emId);


    /**
     * 根据材料题id查询组成材料题的多选题目
     */
    List<Object> findMultipulSelectionByExamMaterialId(Integer emId);

    /**
     * 根据材料题id查询组成材料题的判断题目
     * @param emId
     * @return
     */
    List<Object> findExamJudgeByExamMaterialId(Integer emId);

    /**
     * 根据材料题ID查询组成试卷的主观题目
     * @param emId
     * @return
     */
    List<Object> findExamSubjectByExamMaterialId(Integer emId);

    /**
     * 根据材料题ID查询组成试卷的填空题目
     * @param emId
     * @return
     */
    List<Object> findExamFillBlankByExamMaterialId(Integer emId);
}
