package com.exam.demo.service;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.results.vo.TestpaperVo;

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
     * @param departmentId
     * @param subject
     * @return
     */
    List<TestpaperVo> findCurrentExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                      String subject, Integer currentPage, Integer pageSize);

    /**
     * 分页查询——所有正在进行的考试
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findCurrentExamByPage(Integer currentPage, Integer pageSize);

    /**
     * 查询所有历史考试——不支持分页
     * @return
     */
    List<TestpaperVo> findAllHistoricalExam();

    /**
     * 组合查询——历史考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findHistoricalExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                         String subject, Integer currentPage, Integer pageSize);

    /**
     * 分页查询——历史考试
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findHistoricalExamByPage(Integer currentPage, Integer pageSize);

    /**
     * 不支持分页——未来考试
     * @return
     */
    List<TestpaperVo> findAllFutureExam();

    /**
     * 组合查询——未来考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findFutureExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                     String subject, Integer currentPage, Integer pageSize);

    /**
     * 分页查询——未来考试
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TestpaperVo> findFutureExamByPage(Integer currentPage, Integer pageSize);
}
