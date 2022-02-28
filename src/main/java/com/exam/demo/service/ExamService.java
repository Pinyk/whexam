package com.exam.demo.service;

import com.exam.demo.entity.*;
import com.exam.demo.utils.TestpaperVo;

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
    Integer submitTest(Integer testPaperId, Integer userId, List<ExamJudge> examJudges, List<ExamSelect> examSelects, List<ExamSubject> examSubjects);

    /**
     * 添加试卷试题
     * @param exam
     * @return
     */
    Integer addProblem(Exam exam);

    /**
     * 随机组建试卷
     * @return
     */
    Integer randomComponentPaper(Integer testPaperId, Integer subjectId, Integer judgeCount, Integer singleCount, Integer multipleCount, Integer subjectCOunt);

    /**
     * 删除试卷试题
     * @param id
     * @return
     */
    Integer deleteProblem(Integer id);

    /**
     * 正在考试——组合查询
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<TestpaperVo> findCurrentExam(Integer testPaperId, String testPaperName, String departmentName, String subject);

    /**
     * 历史考试——组合查询
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<TestpaperVo> findHistoricalExam(Integer testPaperId, String testPaperName, String departmentName, String subject);

    /**
     * 组合查询考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<Testpaper> findExamByCombinedQuery(Integer testPaperId, String testPaperName, String departmentName, String subject);

    /**
     * 未来考试——组合查询
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<TestpaperVo> findFutureExam(Integer testPaperId, String testPaperName, String departmentName, String subject);

}
