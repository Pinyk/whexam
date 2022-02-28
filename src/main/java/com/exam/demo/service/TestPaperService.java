package com.exam.demo.service;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.utils.TestpaperVo;

import java.util.List;

public interface TestPaperService {

    /**
     * 查询所有试卷信息
     * @return
     */
    List<RtTestpaper> findAll();

    /**
     * 查询正在考试的试卷信息
     * @return
     */
    List<RtTestpaper> findTesting();

    /**
     * 查询历史考试的试卷信息
     * @return
     */
    List<RtTestpaper> findTested();

    /**
     * 查询尚未开始考试的试卷信息
     * @return
     */
    List<RtTestpaper> findNotStartTest();

    /**
     * 添加试卷头信息
     * @param testPaper
     * @return
     */
    Integer addTestPaper(Testpaper testPaper);

    /**
     * 查询所有正在进行的考试
     * @return
     */
    List<TestpaperVo> findAllCurrentExam();

    /**
     * 正在考试——组合查询
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    List<TestpaperVo> findCurrentExam(Integer testPaperId, String testPaperName, String departmentName,
                                      String subject, Integer currentPage, Integer pageSize);

    /**
     * 分页查询所有正在进行的考试
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findCurrentExamByPage(Integer currentPage, Integer pageSize);

    List<TestpaperVo> findAllHistoricalExam();

    List<TestpaperVo> findHistoricalExam(Integer testPaperId, String testPaperName, String departmentName, String subject);

    List<TestpaperVo> findAllFutureExam();

    List<TestpaperVo> findFutureExam(Integer testPaperId, String testPaperName, String departmentName, String subject);

}
