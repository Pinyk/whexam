package com.exam.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Score;
import com.exam.demo.entity.UserTestPaperScore;
import com.exam.demo.mapper.ScoreMapper;
import com.exam.demo.mapper.TestPaperMapper;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private UserMapper userMapper;

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
    public PageVo<JSONObject> findByTestPaperId(Integer testPaperId, Integer currentPage, Integer pageSize) {

        Page<Score> scorePage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Score> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Score::getTestpaperId, testPaperId);
        Page<Score> page = scoreMapper.selectPage(scorePage, lambdaQueryWrapper);

        List<JSONObject> jsonObjects = new LinkedList<>();
        if (!page.getRecords().isEmpty()) {
            for (Score record : page.getRecords()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("testPaperName", testPaperMapper.selectById(record.getTestpaperId()).getName());
                jsonObject.put("userName", userMapper.selectById(record.getUserId()).getName());
                jsonObject.put("userId", record.getUserId());
                jsonObject.put("score", record.getScorenum());
                jsonObject.put("totalScore", testPaperMapper.selectById(record.getTestpaperId()).getTotalscore());
                jsonObject.put("passScore", testPaperMapper.selectById(record.getTestpaperId()).getPassscore());
                jsonObjects.add(jsonObject);
            }
        }

        return PageVo.<JSONObject>builder()
                .values(jsonObjects)
                .size(pageSize)
                .page(currentPage)
                .total(page.getTotal())
                .build();
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
