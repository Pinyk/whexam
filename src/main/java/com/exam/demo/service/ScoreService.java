package com.exam.demo.service;

import com.exam.demo.entity.UserTestPaperScore;

import java.util.List;

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
    List<UserTestPaperScore> findByTestPaperId(Integer testPaperId);

    /**
     * 根据试卷ID 求本场考试的平均成绩
     * @param testPaperId
     * @return
     */
    Double avgScoreByTestPaperId(Integer testPaperId);
}
