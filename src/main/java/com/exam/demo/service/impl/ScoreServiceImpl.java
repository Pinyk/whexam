package com.exam.demo.service.impl;

import com.exam.demo.entity.UserTestPaperScore;
import com.exam.demo.mapper.ScoreMapper;
import com.exam.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 根据用户ID查询试卷成绩
     * @param userId
     * @return
     */
    @Override
    public List<UserTestPaperScore> findByUserId(Integer userId) {
        return scoreMapper.findByUserId(userId);
    }

    /**
     * 根据试卷ID查询参加考试的用户成绩
     * @param testPaperId
     * @return
     */
    @Override
    public List<UserTestPaperScore> findByTestPaperId(Integer testPaperId) {
        return scoreMapper.findByTestPaperId(testPaperId);
    }

    /**
     * 查询历史试卷的平均分
     * @param testPaperId
     * @return
     */
    @Override
    public Double avgScoreByTestPaperId(Integer testPaperId) {
        return scoreMapper.avgScoreByTestPaperId(testPaperId);
    }
}
