package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.Score;
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
     * 根据用户ID查询试卷
     * @param userId
     * @return
     */
    @Override
    public List<Score> findByUserId(Integer userId) {
        QueryWrapper<Score> scoreQueryWrapper = new QueryWrapper<>();
        scoreQueryWrapper.eq("userId", userId);
        return scoreMapper.selectList(scoreQueryWrapper);
    }

    /**
     * 查询历史试卷的平均分
     * @param testPaperId
     * @return
     */
    @Override
    public Double AvgScoreByTestPaperId(Integer testPaperId) {
        return null;
    }
}
