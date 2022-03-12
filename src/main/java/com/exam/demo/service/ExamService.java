package com.exam.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.*;
import com.exam.demo.otherEntity.UserAnswer;
import com.exam.demo.results.vo.TestpaperVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ExamService {

    /**
     * 根据试卷ID查询试卷的所有试题
     * @param testPaperId
     * @return
     */
    Map<String, List<Object>> findByTestPaperId(Integer testPaperId);

    /**
     * 提交试卷，将用户作答保留到数据库，并为客观题目评分
     * @return
     */
    Integer submitTest(Integer testPaperId, Integer userId, UserAnswer userAnswer);

    /**
     * 添加试卷试题
     * @param exam
     * @return
     */
    Integer addProblem(Exam exam);



    /**
     * 删除试卷试题
     * @param id
     * @return
     */
    Integer deleteProblem(Integer id);

    /**
     * 组合查询试卷
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<TestpaperVo> combinedQueryTestPaper(Integer testPaperId, String testPaperName, String departmentName, String subject);

    /**
     * 根据用户ID和试卷ID修改试卷总分
     * @param testPaperId
     * @param userId
     * @return
     */
    Integer updateScoreByUserId(Double score, Integer testPaperId, Integer userId);

    /**
     * 根据用户ID和试卷ID查询考试明细接口
     * @param testPaperId
     * @param userId
     * @return
     */
    List<Map<String, Object>> findScoreDetailByUIdAndTPId(Integer testPaperId, Integer userId);
}
