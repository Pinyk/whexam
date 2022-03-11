package com.exam.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.entity.UserTestPaperScore;
import com.exam.demo.results.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ScoreService {

    /**
     * 根据用户ID查询用户的所有考试成绩信息
     * @param userId
     * @return
     */
    List<UserTestPaperScore> findByUserId(Integer userId);

    /**
     * 根据试卷ID查询所有参加本场考试的学生成绩信息
     * @param testPaperId
     * @return
     */
    PageVo<JSONObject> findByTestPaperId(Integer testPaperId, Integer currentPage, Integer pageSize);

    /**
     * 根据试卷ID 求本场考试的平均成绩
     * @param testPaperId
     * @return
     */
    Double avgScoreByTestPaperId(Integer testPaperId);
}
