package com.exam.demo.service;

import com.exam.demo.entity.TestPaper;

import java.util.List;

public interface TestPaperService {

    /**
     * 查询所有试卷信息
     * @return
     */
    List<TestPaper> findAll();

    /**
     * 查询正在考试的试卷信息
     * @return
     */
    List<TestPaper> findTesting();

    /**
     * 查询历史考试的试卷信息
     * @return
     */
    List<TestPaper> findTested();

    /**
     * 添加试卷头信息
     * @param testPaper
     * @return
     */
    Integer addTestPaper(TestPaper testPaper);
}
