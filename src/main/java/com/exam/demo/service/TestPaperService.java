package com.exam.demo.service;

import com.exam.demo.entity.Testpaper;

import java.util.List;

public interface TestPaperService {

    /**
     * 查询所有试卷信息
     * @return
     */
    List<Testpaper> findAll();

    /**
     * 查询正在考试的试卷信息
     * @return
     */
    List<Testpaper> findTesting();

    /**
     * 查询历史考试的试卷信息
     * @return
     */
    List<Testpaper> findTested();

    /**
     * 查询尚未开始考试的试卷信息
     * @return
     */
    List<Testpaper> findNotStartTest();

    /**
     * 添加试卷头信息
     * @param testPaper
     * @return
     */
    Integer addTestPaper(Testpaper testPaper);
}
