package com.exam.demo.service;

import com.exam.demo.entity.Testpaper;
import com.exam.demo.rtEntity.RtTestpaper;

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
}
