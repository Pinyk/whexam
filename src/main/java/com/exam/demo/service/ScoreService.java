package com.exam.demo.service;

import com.exam.demo.entity.Score;

import java.util.List;

public interface ScoreService {

    List<Score> findByUserId(Integer userId);

    Double AvgScoreByTestPaperId(Integer testPaperId);
}
